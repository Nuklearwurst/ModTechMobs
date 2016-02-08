package com.fravokados.dangertech.mindim.block.tileentity;

import com.fravokados.dangertech.api.block.IBlockPlacedListener;
import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.api.portal.IEntityPortalController;
import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import com.fravokados.dangertech.api.upgrade.UpgradeStatCollection;
import com.fravokados.dangertech.api.upgrade.UpgradeTypes;
import com.fravokados.dangertech.core.inventory.InventoryUpgrade;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyTypes;
import com.fravokados.dangertech.core.plugin.energy.IEnergyTypeAware;
import com.fravokados.dangertech.mindim.ModMiningDimension;
import com.fravokados.dangertech.mindim.block.tileentity.energy.EnergyStorage;
import com.fravokados.dangertech.mindim.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.mindim.block.types.PortalFrameState;
import com.fravokados.dangertech.mindim.client.ClientPortalInfo;
import com.fravokados.dangertech.mindim.configuration.Settings;
import com.fravokados.dangertech.mindim.inventory.ContainerEntityPortalController;
import com.fravokados.dangertech.mindim.item.ItemDestinationCard;
import com.fravokados.dangertech.mindim.lib.NBTKeys;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import com.fravokados.dangertech.mindim.plugin.PluginLookingGlass;
import com.fravokados.dangertech.mindim.portal.BlockPositionDim;
import com.fravokados.dangertech.mindim.portal.PortalConstructor;
import com.fravokados.dangertech.mindim.portal.PortalManager;
import com.fravokados.dangertech.mindim.portal.PortalMetrics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public class TileEntityPortalControllerEntity extends TileEntity
		implements  ISidedInventory, IBlockPlacedListener, IEntityPortalController,
					IFacingSix, IUpgradable, ITickable,
					IPortalFrameWithState, IEnergyTypeAware {

	/**
	 * possible states of the controller
	 */
	public enum State {
		NO_MULTIBLOCK, READY, CONNECTING, OUTGOING_PORTAL, INCOMING_CONNECTION, INCOMING_PORTAL;

		public String getTranslationShort() {
			return Strings.translate(Strings.Gui.CONTROLLER_STATE_MSG_SHORT_BASE + Strings.Gui.CONTROLLER_STATE_MSG[this.ordinal()]);
		}

		public String getTranslationDetail() {
			return Strings.translate(Strings.Gui.CONTROLLER_STATE_MSG_DETAIL_BASE + Strings.Gui.CONTROLLER_STATE_MSG[this.ordinal()]);
		}
	}

	/**
	 * posible errors when connecting to a portal
	 */
	public enum Error {
		NO_ERROR, INVALID_DESTINATION,
		INVALID_PORTAL_STRUCTURE, CONNECTION_INTERRUPTED,
		POWER_FAILURE, DESTINATION_CHANGED, NOT_ENOUGH_MINERALS, UNKNOWN;

		public String getTranslationShort() {
			return Strings.translate(Strings.Gui.CONTROLLER_ERROR_MSG_SHORT_BASE + Strings.Gui.CONTROLLER_ERROR_MSG[this.ordinal()]);
		}

		public String getTranslationDetail() {
			return Strings.translate(Strings.Gui.CONTROLLER_ERROR_MSG_DETAIL_BASE + Strings.Gui.CONTROLLER_ERROR_MSG[this.ordinal()]);
		}
	}

	public static final int SLOT_DESTINATION = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_WRITER_IN = 2;
	public static final int SLOT_WRITER_OUT = 3;

	/**
	 * flag indicating that the controller is able to disconnect from incoming portals
	 */
	public static final int FLAG_CAN_DISCONNECT_INCOMING = 1;
	/**
	 * flag indicating that the controller can reverse the portal direction
	 */
	public static final int FLAG_CAN_REVERSE_PORTAL = 2;

	@SideOnly(Side.CLIENT)
	public ClientPortalInfo renderInfo;

	/**
	 * Portal id
	 */
	private int id = PortalManager.PORTAL_NOT_CONNECTED;

	/**
	 * Controller name
	 */
	private String name = null;

	/**
	 * Controller main inventory
	 */
	private ItemStack[] inventory = new ItemStack[getSizeInventory()];

	/**
	 * last portal metrics
	 */
	private PortalMetrics metrics;
	/**
	 * current destination (if open portal)
	 */
	private int portalDestination = PortalManager.PORTAL_NOT_CONNECTED;

	/**
	 * block facing
	 */
	private EnumFacing facing = EnumFacing.NORTH;

	/**
	 * controller state
	 */
	private State state = State.READY;
	private Error lastError = Error.NO_ERROR;

	/**
	 * current progress
	 */
	private int tick = 0;

	/**
	 * gets set to true on first world tick, used for energy initialization
	 */
	private boolean init = false;

	/**
	 * EnergyType of this block
	 */
	private EnergyTypes energyType = EnergyTypes.INVALID; //TODO support of different energy mods
	/**
	 * Energy Storage
	 */
	private final EnergyStorage energy = new EnergyStorage(Settings.ENERGY_STORAGE);

	/**
	 * Upgrades
	 * TODO: support different upgrade inventory sizes
	 */
	private InventoryUpgrade upgrades = new InventoryUpgrade(9);

	/**
	 * used to determine wich upgrades are installed
	 */
	private int upgradeTrackerFlags = 0;

	/**
	 * Currently used ticket for chunkloading
	 */
	private ForgeChunkManager.Ticket chunkLoaderTicket;

	/**
	 * Time the portal needs to open a connection
	 */
	private int connectionTime = Settings.PORTAL_CONNECTION_TIME;

	/**
	 * Length of time the portal can be hold open
	 */
	private int connectionLength = Settings.MAX_PORTAL_CONNECTION_LENGTH;

	/**
	 * base energy usage after applying upgrades
	 */
	private double baseEnergyUse = Settings.ENERGY_USAGE;

	/**
	 * base energy usage (init) after applying upgrades, note: MinDim - portal creation is currently unaffected by upgrades
	 */
	private double baseEnergyUseInit = Settings.ENERGY_USAGE_INIT;

	/**
	 * opens a portal to the current destination<br>
	 * does only the placement of portal blocks
	 *
	 * @return success
	 */
	public boolean placePortalBlocks() {
		return metrics != null && metrics.placePortalsInsideFrame(worldObj, getPos());
	}

	/**
	 * Used to update portal strucutre metrics
	 */
	public boolean updateMetrics() {
		return PortalConstructor.createPortalMultiBlock(worldObj, getPos()) == PortalConstructor.Result.SUCCESS;
	}


	@Override
	public IUpgradeInventory getUpgradeInventory() {
		return upgrades;
	}

	/**
	 * updates local information on upgrades
	 */
	@Override
	public void updateUpgradeInformation() {
		UpgradeStatCollection col = UpgradeStatCollection.getUpgradeStatsFromDefinitions(upgrades.getUpgrades());
		energy.setCapacity(Settings.ENERGY_STORAGE + col.getInt(UpgradeTypes.ENERGY_STORAGE.id, 0));
		upgradeTrackerFlags = 0;
		if (col.hasKey(UpgradeTypes.DISCONNECT_INCOMING)) {
			upgradeTrackerFlags += FLAG_CAN_DISCONNECT_INCOMING;
		}
		if (col.hasKey(UpgradeTypes.REVERSE_DIRECTION)) {
			upgradeTrackerFlags += FLAG_CAN_REVERSE_PORTAL;
		}
	}

	public int getUpgradeTrackerFlags() {
		return upgradeTrackerFlags;
	}

	public void setUpgradeTrackerFlags(int upgradeTrackerFlags) {
		this.upgradeTrackerFlags = upgradeTrackerFlags;
	}

	public PortalMetrics getMetrics() {
		return metrics;
	}

	public State getState() {
		return state;
	}

	@Override
	public PortalFrameState getPortalFrameState() {
		switch (state) {
			case NO_MULTIBLOCK:
			case READY:
				return PortalFrameState.DISABLED;
			case INCOMING_PORTAL:
			case OUTGOING_PORTAL:
				return PortalFrameState.ACTIVE;
			default:
				return PortalFrameState.CONNECTING;
		}
	}

	public Error getLastError() {
		return lastError;
	}

	public void setState(State state) {
		if(worldObj.isRemote && PluginLookingGlass.isAvailable()) {
			if(state == State.OUTGOING_PORTAL || state == State.INCOMING_PORTAL) {
				if(state != this.state) {
					if(renderInfo != null) {
						if(metrics == null) {
							updateMetrics();
						}
						if(metrics != null) {
							renderInfo.createLookingGlass(metrics, this.worldObj);
						}
					}
				}
			} else if(this.state == State.OUTGOING_PORTAL || this.state == State.INCOMING_PORTAL) {
				if(renderInfo != null) {
					renderInfo.destroyLookingGlass();
				}
			}
		}
		this.state = state;
		this.worldObj.markBlockForUpdate(getPos());
		if (metrics != null) {
			metrics.updatePortalFrames(worldObj);
		}
	}

	public void setLastError(Error lastError) {
		this.lastError = lastError;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public EnergyTypes getEnergyType() {
		return energyType;
	}

	@Override
	public void setEnergyType(EnergyTypes energyType) {
		this.energyType = energyType;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * reads the destination card
	 *
	 * @return the destination id of the current destination card
	 */
	public int getDestination() {
		if (inventory[0] == null) {
			return PortalManager.PORTAL_NOT_CONNECTED;
		}
		if (inventory[0].getItem() instanceof ItemDestinationCard) {
			if (inventory[0].getItemDamage() == ItemDestinationCard.META_MIN_DIM) {
				return PortalManager.PORTAL_MINING_DIMENSION;
			} else if (inventory[0].getTagCompound() != null && inventory[0].getTagCompound().hasKey(NBTKeys.DESTINATION_CARD_PORTAL_TYPE) && inventory[0].getTagCompound().hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID)) {
				if (inventory[0].getTagCompound().getInteger(NBTKeys.DESTINATION_CARD_PORTAL_TYPE) == PortalMetrics.Type.ENTITY_PORTAL.ordinal()) {
					return inventory[0].getTagCompound().getInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID);
				} else {
					return PortalManager.PORTAL_WRONG_TYPE;
				}
			}
		}
		return PortalManager.PORTAL_INVALID_ITEM;
	}

	/**
	 * teleports an entity to the current destination
	 *
	 * @param entity the enity to teleport
	 */
	public void teleportEntity(Entity entity) {
		if (state == State.OUTGOING_PORTAL && metrics != null) {
			if (metrics.isEntityInsidePortal(entity, 1)) {
				if (!ModMiningDimension.instance.portalManager.teleportEntityToEntityPortal(entity, getDestination(), id, metrics)) {
					closePortal(true);
					resetOnError(Error.CONNECTION_INTERRUPTED);
				}
			}
		} else if (state != State.INCOMING_PORTAL) {
			//invalid state, close portal and continue as usual
			closePortal(true);
			resetOnError(Error.CONNECTION_INTERRUPTED);
			LogHelperMD.warn("Invalid portal found, destroying... (" + id + ", dest.:" + portalDestination + ")");
		}
	}

	/**
	 * called on Block#onBlockPostPlaced()
	 * registers portal
	 */
	@Override
	public void onBlockPostPlaced(World world, BlockPos pos, IBlockState state) {
		if(id >= 0) {
			PortalManager.getInstance().registerEntityPortal(id, new BlockPositionDim(this));
		} else {
			id = ModMiningDimension.instance.portalManager.registerNewEntityPortal(new BlockPositionDim(this));
		}
		PortalConstructor.createPortalMultiBlock(world, pos);
		//TODO proper EnergyTypes
		setEnergyType(EnergyTypes.VANILLA);
	}

	/**
	 * @return true if portal is open
	 */
	public boolean isActive() {
		return state == State.INCOMING_PORTAL || state == State.OUTGOING_PORTAL;
	}

	public void setMetrics(PortalMetrics metrics) {
		this.metrics = metrics;
	}

	@Override
	public void update() {
		if (worldObj.isRemote) {
			return;
		}
		//Do first tick initialization
		if (!init) {
			init = true;
			if (state != State.INCOMING_PORTAL && state != State.OUTGOING_PORTAL) {
				//reset portals
				closePortal(true);
			}
			if (energyType == EnergyTypes.IC2) {
				//init ic2 energy net
				//MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				//FIXME ic2 integration
			}
		}
		//Write Destination Cards (Side GUI)
		if (id > -1 && inventory[2] != null && inventory[3] == null) {
			if (inventory[2].getItem() instanceof ItemDestinationCard && inventory[2].getItemDamage() != ItemDestinationCard.META_MIN_DIM) {
				inventory[3] = inventory[2];
				inventory[2] = null;
				ItemDestinationCard.writeDestination(inventory[3], id, getDisplayName().getFormattedText());
				markDirty();
			}
		}
		//Connect Portal
		if (state == State.CONNECTING) {
			if (tick % 40 == 0 && metrics != null) {
				//play connecting sound effect
				worldObj.playSoundEffect(metrics.originX, metrics.originY, metrics.originZ, Strings.Sounds.PORTAL_CONNECT, 0.5F, worldObj.rand.nextFloat() * 0.1F + 1.9F);
			}
			if (tick == 0) {
				//update destination portal
				portalDestination = getDestination();
				if (portalDestination >= 0) {
					BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
					if (pos == null || portalDestination == id) {
						//invalid portal
						resetOnError(Error.INVALID_DESTINATION);
					} else {
						MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
						WorldServer world = server.worldServerForDimension(pos.dimension);
						TileEntity te = world.getTileEntity(pos.getPosition());
						if (te != null && te instanceof TileEntityPortalControllerEntity) {
							//inform target of our connection
							((TileEntityPortalControllerEntity) te).setState(State.INCOMING_CONNECTION);
						} else { //invalid controller
							LogHelperMD.warn("Could not find registered controller with id: " + portalDestination);
							resetOnError(Error.CONNECTION_INTERRUPTED);
						}
					}
				}
				tick++;
			} else if (tick >= connectionTime) {
				//Do connection
				tick = 0;
				int oldDestination = portalDestination;
				portalDestination = getDestination();
				if (oldDestination != portalDestination) {
					//check destination
					resetOnError(Error.DESTINATION_CHANGED);
				} else if (updateMetrics()) {
					//Check whether we created a successful multiblock
					//create portal if necessary
					if (portalDestination == PortalManager.PORTAL_MINING_DIMENSION) {
						//create mining dimension portal and update destination
						int count = ItemUtils.getNBTTagCompound(inventory[0]).getInteger("frame_blocks");
						if (count >= metrics.getFrameBlockCount()) {
							if (energy.useEnergy(Settings.ENERGY_USAGE_CREATE_PORTAL)) {
								portalDestination = PortalManager.getInstance().createPortal(id, metrics, this);
								if (portalDestination >= 0) {
									//create destination card, TODO: localization
									inventory[0] = ItemDestinationCard.fromDestination(portalDestination, "Unknown");
								} else {
									resetOnError(Error.INVALID_DESTINATION);
								}
							} else {
								resetOnError(Error.POWER_FAILURE);
							}
						} else {
							resetOnError(Error.NOT_ENOUGH_MINERALS);
						}
					}
					if (portalDestination >= 0) { //if destination is valid
						BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
						if (pos == null) { //invalid destination (Not found)
							resetOnError(Error.INVALID_DESTINATION);
						} else {
							//open portal
							if (!placePortalBlocks()) { //invalid structure
								resetOnError(Error.INVALID_PORTAL_STRUCTURE);
							} else {
								//update controllers
								TileEntityPortalControllerEntity te = pos.getControllerEntity();
								if (te != null) {
									if(te.isActive()) {
										//Portal already has an open connection
										closePortal(false);
										resetOnError(Error.CONNECTION_INTERRUPTED);
									} else if (te.updateMetrics() && te.placePortalBlocks()) {
										if (energy.useEnergy(baseEnergyUseInit)) { //use initial energy
											loadChunk(pos);
											//update controller states
											te.setState(State.INCOMING_PORTAL);
											te.portalDestination = id;
											setState(State.OUTGOING_PORTAL);
											lastError = Error.NO_ERROR;
											worldObj.playSoundEffect(metrics.originX, metrics.originY, metrics.originZ, Strings.Sounds.PORTAL_OPEN, 0.5F, worldObj.rand.nextFloat() * 0.1F + 1.9F);
										} else {
											//reset destination if power fails
											te.setState(State.READY);
											closePortal(true);
											resetOnError(Error.POWER_FAILURE);
										}
									} else { //invalid portal (Destination has invalid structure [portal creation failed])
										closePortal(true);
										resetOnError(Error.CONNECTION_INTERRUPTED);
									}
								} else { //invalid portal (Invalid TE)
									LogHelperMD.warn("Error opening portal to: " + portalDestination);
									closePortal(true);
									resetOnError(Error.CONNECTION_INTERRUPTED);
								}
							}
						}
					} else {
						//connection failed (invalid destination card or failed creating portal)
						if (state != State.READY) {
							resetOnError(Error.UNKNOWN);
						}
					}
				} else {
					resetOnError(Error.INVALID_PORTAL_STRUCTURE);
				}
			} else {
				//update progress
				tick++;
			}
		} else if (state == State.OUTGOING_PORTAL) {
			//Outgoing portal opened --> update max length
			if (connectionLength > 0 && tick >= connectionLength) {
				closePortal(true);
				tick = 0;
			} else {
				tick++;
			}
		}
		//Use Energy
		if (state == State.CONNECTING || state == State.OUTGOING_PORTAL) {
			if (!energy.useEnergy(baseEnergyUse)) {
				closePortal(true);
				resetOnError(Error.POWER_FAILURE);
			}
		}
		//recharge energy
		if (!energy.isFull() && inventory[1] != null) {
			switch (energyType) {
				case IC2:
					/*
					if (EnergyManager.canItemProvideEnergy(inventory[1], EnergyTypes.IC2)) {
						energy.receiveEnergy(ElectricItem.manager.discharge(inventory[1], getDemandedEnergy(), getSinkTier(), false, true, false), false);
					}
					*/
					//FIXME ic2 integration
					break;
				case VANILLA:
					double fuelValue = TileEntityFurnace.getItemBurnTime(inventory[SLOT_FUEL]);
					if(energy.hasRoomForEnergy(fuelValue)) {
						energy.receiveEnergy(fuelValue, false);
						decrStackSize(SLOT_FUEL, 1);
					}
					break;
			}
		}
	}

	private void loadChunk(BlockPositionDim pos) {
		ForgeChunkManager.releaseTicket(chunkLoaderTicket);
		chunkLoaderTicket = ForgeChunkManager.requestTicket(ModMiningDimension.instance, pos.getWorldServer(), ForgeChunkManager.Type.NORMAL);
		if(chunkLoaderTicket == null) {
			LogHelperMD.warn("Chunkloading Ticket limit reached!");
		} else {
			ChunkCoordIntPair chunkToLoad = WorldUtils.convertToChunkCoord(pos.getPosition());
			ForgeChunkManager.forceChunk(chunkLoaderTicket, chunkToLoad);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		onChunkUnload();
	}

	/**
	 * Unloads the IC2 Energy Sink and closes any open connections
	 */
	@Override
	public void onChunkUnload() {
		if (worldObj != null && worldObj.isRemote) {
			return;
		}
		if (init && energyType == EnergyTypes.IC2) {
//			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
//			init = false;
			//FIXME ic2 integration
		}
		closePortal(true);
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < inventory.length) {
			return inventory[slot];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return ItemUtils.decrStackSize(this, slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

/*
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	*/

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.inventory[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return BlockUtils.isTileEntityUsableByPlayer(this, player);
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		switch (slot) {
			case SLOT_DESTINATION:
			case SLOT_WRITER_IN:
				return stack.getItem() instanceof ItemDestinationCard;
			case SLOT_FUEL:
				return EnergyManager.canItemProvideEnergy(stack, getEnergyType());
			case SLOT_WRITER_OUT:
				return false;
		}
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList nbttaglist = nbt.getTagList("Items", 10); //10 is compound type
		this.inventory = new ItemStack[this.getSizeInventory()];

		ItemUtils.readInventoryContentsFromNBT(this, nbttaglist);
		if (nbt.hasKey("name")) {
			name = nbt.getString("name");
		}
		id = nbt.getInteger("PortalID");
		facing = EnumFacing.getFront(nbt.getByte("facing"));
		if (nbt.hasKey("metrics")) {
			metrics = PortalMetrics.getMetricsFromNBT(nbt.getCompoundTag("metrics"));
		}
		upgrades.readFromNBT(nbt.getCompoundTag("Upgrades"));
		energyType = EnergyTypes.readFromNBT(nbt);
		energy.readFromNBT(nbt);
		updateUpgradeInformation();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList nbttaglist = new NBTTagList();

		ItemUtils.writeInventoryContentsToNBT(this, nbttaglist);
		nbt.setTag("Items", nbttaglist);
		if (hasCustomName()) {
			nbt.setString("name", name);
		}
		nbt.setInteger("PortalID", id);
		nbt.setByte("facing", (byte) facing.getIndex());
		if (metrics != null) {
			NBTTagCompound metricsTag = new NBTTagCompound();
			metrics.writeToNBT(metricsTag);
			nbt.setTag("metrics", metricsTag);
		}
		nbt.setTag("Upgrades", upgrades.writeToNBT(new NBTTagCompound()));
		energyType.writeToNBT(nbt);
		energy.writeToNBT(nbt);
	}

	/**
	 * connects portal with destination
	 */
	public void initializeConnection() {
		//update state and tick
		setState(State.CONNECTING);
		lastError = Error.NO_ERROR;
		tick = 0;
		//register portal and log warning
		if (id == PortalManager.PORTAL_NOT_CONNECTED) {
			LogHelperMD.warn("Invalid Controller found!");
			LogHelperMD.warn((hasCustomName() ? "Unnamed Controller" : ("Controller " + name)) + " @dim: " + worldObj.provider.getDimensionId() + ", pos: " + getPos().getX() + "; " + getPos().getY() + "; " + getPos().getZ() + " has no valid id. Registering...");
			id = ModMiningDimension.instance.portalManager.registerNewEntityPortal(new BlockPositionDim(this));
		}
	}

	@SuppressWarnings(value = "unchecked")
	public void handleStartButton(ContainerEntityPortalController containerEntityPortalController) {
		switch (state) {
			case READY:
				initializeConnection();
				break;
			case INCOMING_PORTAL: {
				BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
				if (pos != null) {
					TileEntityPortalControllerEntity te = pos.getControllerEntity();
					if (te != null) {
						te.setState(State.INCOMING_PORTAL);
						this.setState(State.OUTGOING_PORTAL);
					} else {
						closePortal(true);
					}
				} else {
					closePortal(true);
				}
				break;
			}
		}
	}

	public void handleStopButton(ContainerEntityPortalController containerEntityPortalController) {
		switch (state) {
			case CONNECTING:
			case OUTGOING_PORTAL:
				closePortal(true);
				break;
			case INCOMING_PORTAL:
				if((upgradeTrackerFlags & FLAG_CAN_DISCONNECT_INCOMING) != 0) {
					closePortal(true);
				}
				break;
		}
	}

	/**
	 * closes the portal
	 *
	 * @param closeRemote should remote also be closed?
	 */
	public void closePortal(boolean closeRemote) {
		//release loaded chunks
		ForgeChunkManager.releaseTicket(chunkLoaderTicket);
		//close remote portal if needed
		if (closeRemote) {
			BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
			if (pos != null) {
				TileEntityPortalControllerEntity te = pos.getControllerEntity();
				if (te != null) {
					te.closePortal(false);
				}
			}
		}
		//remove portal
		if (metrics != null) {
			metrics.removePortalsInsideFrame(worldObj);
			if (state != State.READY) {
				//Play closing sound effect
				worldObj.playSoundEffect(metrics.originX, metrics.originY, metrics.originZ, Strings.Sounds.PORTAL_CLOSE, 1.0F, worldObj.rand.nextFloat() * 0.1F + 2.9F);
			}
		}
		//reset state
		setState(State.READY);
	}

	@Override
	public void setFacing(EnumFacing f) {
		this.facing = f;
		this.worldObj.markBlockForUpdate(getPos());
	}

//	@Override
//	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
//		return facing != side;
//	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	//	@Override
//	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
//		return true;
//	}
//
//	@Override
//	public float getWrenchDropRate() {
//		return 1;
//	}
//
//	@Override
//	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
//		ItemStack out = new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal());
//		ItemUtils.writeUpgradesToItemStack(getUpgradeInventory(), out);
//		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(out);
//		nbt.setInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID, id);
//		if(hasCustomName()) {
//			nbt.setString(NBTKeys.DESTINATION_CARD_PORTAL_NAME, getDisplayName().getFormattedText());
//		}
//		upgrades = null; //Hack to prevent droping of upgrades when removing using a wrench
//		return out;
//	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("facing", (byte) facing.getIndex());
		nbt.setInteger("state", state.ordinal());
		if(metrics != null) {
			nbt.setByte("portalFacing", (byte) metrics.front.ordinal());
			if(PluginLookingGlass.isAvailable() && isActive()) {
				PortalMetrics target = PortalManager.getInstance().getPortalMetricsForId(portalDestination);
				if(target != null) {
					nbt.setInteger("targetDimension", PortalManager.getInstance().getEntityPortalForId(portalDestination).dimension);
					nbt.setByte("targetFacing", (byte) target.front.ordinal());
					nbt.setDouble("targetX", target.originX);
					nbt.setDouble("targetY", target.originY);
					nbt.setDouble("targetZ", target.originZ);
				}
			}
		}
		return new S35PacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		if (nbt != null) {
			if(nbt.hasKey("portalFacing")) {
				renderInfo = new ClientPortalInfo();
				renderInfo.originDirection = EnumFacing.getFront(nbt.getByte("portalFacing"));
				if(nbt.hasKey("targetFacing")) {
					renderInfo.targetDimension = nbt.getInteger("targetDimension");
					renderInfo.targetDirection = EnumFacing.getFront(nbt.getByte("targetFacing"));
					renderInfo.targetX = nbt.getInteger("targetX");
					renderInfo.targetY = nbt.getInteger("targetY");
					renderInfo.targetZ = nbt.getInteger("targetZ");
				}
			} else {
				renderInfo = null;
			}

			if (nbt.hasKey("facing")) {
				EnumFacing oldFacing = facing;
				facing = EnumFacing.getFront(nbt.getByte("facing"));
				State oldState = state;
				setState(State.values()[nbt.getInteger("state")]);
				if (oldFacing != facing || oldState != state) {
					this.worldObj.markBlockForUpdate(getPos());
				}
			}
		}
	}


	/*
	@Override
	public double getDemandedEnergy() {
		return Math.max(0, energy.getMaxEnergyStored() - energy.getEnergyStored());
	}

	@Override
	public int getSinkTier() {
		return 3; //TODO: proper ic2 tiers
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		return amount - energy.receiveEnergy(amount, false);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return energyType == EnergyTypes.IC2;
	}
	*/

	public double getEnergyStored() {
		return energy.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return energy.getMaxEnergyStored();
	}

	public EnergyStorage getEnergyStorage() {
		return energy;
	}

	/**
	 * sets an error message and resets state to default
	 */
	public void resetOnError(Error error) {
		setState(State.READY);
		lastError = error;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction) {
		return slot == 0 && stack.getItem() instanceof ItemDestinationCard;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
		return slot == 0 && stack.getItem() instanceof ItemDestinationCard;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasCustomName() {
		return name != null;
	}

	@Override
	public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(name) : new ChatComponentTranslation(Strings.Gui.CONTROLLER_NAME_UNNAMED);
	}
}
