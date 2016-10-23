package com.fravokados.dangertech.portals.block.tileentity;

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
import com.fravokados.dangertech.core.plugin.energy.TileEntityEnergyReceiver;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.portals.block.types.PortalFrameState;
import com.fravokados.dangertech.portals.client.ClientPortalInfo;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.inventory.ContainerEntityPortalController;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.lib.NBTKeys;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import com.fravokados.dangertech.portals.plugin.lookingglass.PluginLookingGlass;
import com.fravokados.dangertech.portals.portal.BlockPositionDim;
import com.fravokados.dangertech.portals.portal.PortalConstructor;
import com.fravokados.dangertech.portals.portal.PortalManager;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class TileEntityPortalControllerEntity extends TileEntityEnergyReceiver
		implements ISidedInventory, IBlockPlacedListener, IEntityPortalController,
		IFacingSix, IUpgradable,
		IPortalFrameWithState {

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
	private EnumFacing facing = EnumFacing.DOWN;

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
	 * Upgrades
	 * TODO: support different upgrade inventory sizes
	 */
	private InventoryUpgrade upgrades = new InventoryUpgrade(9);

	/**
	 * used to determine which upgrades are installed
	 */
	private int upgradeTrackerFlags = 0;

	/**
	 * Currently used ticket for chunkloading
	 */
	private ForgeChunkManager.Ticket chunkLoaderTicketDestination;
	private ForgeChunkManager.Ticket chunkLoaderTicketOrigin;

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

	private int energyTier = 2;

	public TileEntityPortalControllerEntity() {
		super(Settings.ENERGY_STORAGE);
	}

	/**
	 * opens a portal to the current destination<br>
	 * does only the placement of portal blocks
	 *
	 * @return success
	 */
	private boolean placePortalBlocks() {
		return metrics != null && metrics.placePortalsInsideFrame(worldObj, getPos());
	}

	/**
	 * Recreate portal metrics, this will reset Controller state
	 */
	public boolean updateMetrics() {
		metrics = null;
		setState(State.NO_MULTIBLOCK);
		return PortalConstructor.createPortalMultiBlock(worldObj, getPos()) == PortalConstructor.Result.SUCCESS;
	}

	/**
	 * Checks whether current portal metrics are valid
	 */
	public boolean checkMetrics() {
		return metrics != null && metrics.isFrameComplete(worldObj) && metrics.isFrameEmpty(worldObj);
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
		energyStorage.setCapacity(Settings.ENERGY_STORAGE + col.getInt(UpgradeTypes.ENERGY_STORAGE.id, 0));
		energyTier = 2 + col.getInt(UpgradeTypes.ENERGY_TIER.id, 0);
		upgradeTrackerFlags = 0;
		if (col.hasKey(UpgradeTypes.DISCONNECT_INCOMING)) {
			upgradeTrackerFlags |= FLAG_CAN_DISCONNECT_INCOMING;
		}
		if (col.hasKey(UpgradeTypes.REVERSE_DIRECTION)) {
			upgradeTrackerFlags |= FLAG_CAN_REVERSE_PORTAL;
		}
	}

	public int getUpgradeTrackerFlags() {
		return upgradeTrackerFlags;
	}

	public void setUpgradeTrackerFlags(int upgradeTrackerFlags) {
		this.upgradeTrackerFlags = upgradeTrackerFlags;
	}

	public boolean hasUpgrade(int upgrade) {
		return (upgradeTrackerFlags & upgrade) == upgrade;
	}

	@Nullable
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
				return PortalFrameState.DISCONNECTED;
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

	public void onPortalFrameModified() {
		if(!isActive() && state != State.NO_MULTIBLOCK) {
			closePortal(true);
			setState(State.NO_MULTIBLOCK);
		}
	}

	public void setState(State state) {
		if (worldObj.isRemote && PluginLookingGlass.isAvailable()) {
			if (state == State.OUTGOING_PORTAL || state == State.INCOMING_PORTAL) {
				if (state != this.state) {
					if (renderInfo != null) {
						if(metrics != null) {
							renderInfo.createLookingGlass(metrics, this.worldObj);
						} else {
							LogHelperMD.error("Invalid portal state! Portal bounds are not available!");
						}
					}
				}
			} else if (this.state == State.OUTGOING_PORTAL || this.state == State.INCOMING_PORTAL) {
				if (renderInfo != null) {
					renderInfo.destroyLookingGlass();
				}
			}
		}
		this.state = state;
		WorldUtils.notifyBlockUpdateAtTile(this);
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

	public void setName(@Nullable String name) {
		this.name = name;
	}

	/**
	 * reads the destination card
	 *
	 * @return the destination id of the current destination card
	 */
	public int readDestinationCard() {
		final ItemStack stack = inventory[0];
		if (stack == null) {
			return PortalManager.PORTAL_NOT_CONNECTED;
		}
		if (stack.getItem() instanceof ItemDestinationCard) {
			if (ItemDestinationCard.hasDestination(stack)) {
				if (stack.getItemDamage() != ItemDestinationCard.META_GENERATING
						&& (ItemDestinationCard.getTypeFromStack(stack) != PortalMetrics.Type.ENTITY_PORTAL.ordinal())) {
					return PortalManager.PORTAL_WRONG_TYPE;
				}
				return ItemDestinationCard.getPortalIDFromStack(inventory[0]);
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
				if (!ModMiningDimension.instance.portalManager.teleportEntityToEntityPortal(entity, readDestinationCard(), id, metrics)) {
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
		if (id >= 0 && PortalManager.getInstance().entityPortalIdValidForUse(id)) {
			PortalManager.getInstance().registerEntityPortal(id, new BlockPositionDim(this));
		} else {
			id = ModMiningDimension.instance.portalManager.registerNewEntityPortal(new BlockPositionDim(this));
		}
		PortalConstructor.createPortalMultiBlock(world, pos);
	}

	/**
	 * @return true if portal is open
	 */
	public boolean isActive() {
		return state == State.INCOMING_PORTAL || state == State.OUTGOING_PORTAL;
	}

	public void setMetrics(PortalMetrics metrics) {
		this.metrics = metrics;
		setState(State.READY);
	}

	@Override
	protected void loadMachine() {
		super.loadMachine();
		if (state != State.INCOMING_PORTAL && state != State.OUTGOING_PORTAL) {
			//reset portals
			closePortal(true);
		}
	}

	@Override
	public void updateMachineServer() {
		//Write Destination Cards (Side GUI)
		if (id > -1 && inventory[2] != null && inventory[3] == null) {
			if (inventory[2].getItem() instanceof ItemDestinationCard && inventory[2].getItemDamage() != ItemDestinationCard.META_GENERATING) {
				inventory[3] = inventory[2];
				inventory[2] = null;
				ItemDestinationCard.writeDestinationToStack(inventory[3], id, getDisplayName().getUnformattedText());
				markDirty();
			}
		}
		//Connect Portal
		switch (state) {
			case CONNECTING:
				if (tick % 40 == 0 && metrics != null) {
					//play connecting sound effect
					worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 1.9F);
				}
				if (tick == 0) {
					//update destination portal
					connectToDestinationPortal();
					tick++;
				} else if (tick >= connectionTime) {
					//Do connection
					tick = 0;

					//check destination
					int oldDestination = portalDestination;
					portalDestination = readDestinationCard();
					if (oldDestination != portalDestination) {
						//destination changed --> abort
						resetOnError(Error.DESTINATION_CHANGED);
					} else if (updateMetrics()) {
						//Check whether we created a successful multiblock
						//create portal if necessary
						if (portalDestination == PortalManager.PORTAL_GENERATING) {
							//create mining dimension portal and update destination
							generatePortal();
						}
						if (portalDestination >= 0) { //if destination is valid
							openPortalToDestination();
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
				break;
			case INCOMING_CONNECTION:
				tick++;
				if (tick % 40 == 0 && metrics != null) {
					worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 1.9F);
				}
				break;
			case OUTGOING_PORTAL:
				//Outgoing portal opened --> update max length
				if (connectionLength > 0 && tick >= connectionLength) {
					closePortal(true);
					tick = 0;
				} else {
					tick++;
					if (tick % 60 == 0 && metrics != null) {
						worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F - 1F);
					}
				}
				break;
			case INCOMING_PORTAL:
				tick++;
				if (tick % 60 == 0 && metrics != null) {
					worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F - 1F);
				}
				break;
		}
		//Use Energy
		if (state == State.CONNECTING || state == State.OUTGOING_PORTAL) {
			if (!useEnergy(baseEnergyUse)) {
				closePortal(true);
				resetOnError(Error.POWER_FAILURE);
			}
		}
		//recharge energy
		EnergyManager.rechargeEnergyStorageFromInventory(energyStorage, getEnergyType(), this, SLOT_FUEL, energyTier);
	}

	private void openPortalToDestination() {
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
					if (te.isActive()) {
						//Portal already has an open connection
						closePortal(false);
						resetOnError(Error.CONNECTION_INTERRUPTED);
					} else if (te.checkMetrics() && te.placePortalBlocks()) {
						if (useEnergy(baseEnergyUseInit)) { //use initial energy
							loadNeededChunks(pos);
							//update controller states
							te.setState(State.INCOMING_PORTAL);
							te.portalDestination = id;
							setState(State.OUTGOING_PORTAL);
							lastError = Error.NO_ERROR;
							createFXForPortalOpen();
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
	}

	private void createFXForPortalOpen() {
		worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 1.9F);
//		if (worldObj instanceof WorldServer) {
//			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.DRAGON_BREATH, metrics.getCenterX(), metrics.getCenterY(), metrics.getCenterZ(),
//					60 + worldObj.rand.nextInt(10), 0, 0, 0, 0.1F);
//		}
	}

	private void createFXForPortalClose() {
		worldObj.playSound(null, metrics.originX, metrics.originY, metrics.originZ, SoundEvents.BLOCK_PORTAL_TRIGGER, SoundCategory.BLOCKS, 1.0F, worldObj.rand.nextFloat() * 0.1F + 2.9F);
//		if (worldObj instanceof WorldServer) {
//			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.DRAGON_BREATH, metrics.getCenterX(), metrics.getCenterY(), metrics.getCenterZ(),
//					60 + worldObj.rand.nextInt(10), 0, 0, 0, 0.1F);
//		}
	}

	/**
	 * Creates the Portal (frame+contoller) for a miningdimension card
	 * <br/>
	 * ItemDestinationCard stored in slot 0 has to be {@link ItemDestinationCard#META_GENERATING}
	 */
	private void generatePortal() {
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(inventory[0]);
		final int dim = ItemDestinationCard.getDimensionFromStack(inventory[0]);
		final int count = nbt.getInteger(NBTKeys.DESTINATION_CARD_FRAME_BLOCKS);
		if (count >= metrics.getFrameBlockCount()) {
			if (useEnergy(Settings.ENERGY_USAGE_CREATE_PORTAL)) {
				portalDestination = PortalManager.getInstance().createPortal(dim, id, metrics, this);
				if (portalDestination >= 0) {
					//create destination card
					inventory[0] = ItemDestinationCard.fromDestination(portalDestination, Strings.translate(Strings.Tooltip.UNKNOWN_DESTINATION));
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

	private void connectToDestinationPortal() {
		portalDestination = readDestinationCard();
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
					State remoteState = ((TileEntityPortalControllerEntity) te).getState();
					if(((TileEntityPortalControllerEntity) te).isActive() || remoteState == State.INCOMING_CONNECTION || remoteState == State.CONNECTING) {
						resetOnError(Error.CONNECTION_INTERRUPTED);
					} else {
						//inform target of our connection
						((TileEntityPortalControllerEntity) te).setState(State.INCOMING_CONNECTION);
					}
				} else { //invalid controller
					LogHelperMD.warn("Could not find registered controller with id: " + portalDestination);
					resetOnError(Error.CONNECTION_INTERRUPTED);
				}
			}
		} else if (portalDestination != PortalManager.PORTAL_GENERATING) {
			//Invalid destination
			resetOnError(Error.INVALID_DESTINATION);
		}
	}

	/**
	 * Loads the needed chunk for the given destination
	 * <br/>
	 * will load the chunk of the destinations portal-controller as well as {@code this} controllers chunk
	 *
	 * @param pos Position of the destination portal
	 */
	private void loadNeededChunks(BlockPositionDim pos) {
		ForgeChunkManager.releaseTicket(chunkLoaderTicketDestination);
		chunkLoaderTicketDestination = ForgeChunkManager.requestTicket(ModMiningDimension.instance, pos.getWorldServer(), ForgeChunkManager.Type.NORMAL);
		if (chunkLoaderTicketDestination == null) {
			LogHelperMD.warn("Chunkloading Ticket limit reached!");
		} else {
			ChunkPos chunkToLoad = WorldUtils.convertToChunkCoord(pos.getPosition());
			ForgeChunkManager.forceChunk(chunkLoaderTicketDestination, chunkToLoad);
		}
		ForgeChunkManager.releaseTicket(chunkLoaderTicketOrigin);
		chunkLoaderTicketOrigin = ForgeChunkManager.requestTicket(ModMiningDimension.instance, worldObj, ForgeChunkManager.Type.NORMAL);
		if (chunkLoaderTicketOrigin == null) {
			LogHelperMD.warn("Chunkloading Ticket limit reached!");
		} else {
			ChunkPos chunkToLoad = WorldUtils.convertToChunkCoord(getPos());
			ForgeChunkManager.forceChunk(chunkLoaderTicketOrigin, chunkToLoad);
		}


	}

	private void unloadChunks() {
		if (chunkLoaderTicketDestination != null) {
			ForgeChunkManager.releaseTicket(chunkLoaderTicketDestination);
			chunkLoaderTicketDestination = null;
		}
		if (chunkLoaderTicketOrigin != null) {
			ForgeChunkManager.releaseTicket(chunkLoaderTicketOrigin);
			chunkLoaderTicketOrigin = null;
		}
	}

	@Override
	protected void unloadMachine() {
		super.unloadMachine();
		closePortal(true);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int getSinkTier() {
		//FIXME: IC2: if energy tier is > 62 machine explodes! (happens for ic2 machines too)
		return energyTier;
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
	public ItemStack removeStackFromSlot(int slot) {
		ItemStack stack = this.inventory[slot];
		this.inventory[slot] = null;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, @Nullable ItemStack itemStack) {
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
	public void openInventory(@Nullable EntityPlayer player) {

	}

	@Override
	public void closeInventory(@Nullable EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		switch (slot) {
			case SLOT_DESTINATION:
			case SLOT_WRITER_IN:
				return stack.getItem() instanceof ItemDestinationCard;
			case SLOT_FUEL:
				return EnergyManager.canItemProvideEnergy(stack, getEnergyType(), getSinkTier());
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

		NBTTagList nbttaglist = nbt.getTagList(NBTKeys.TILE_MAIN_INVENTORY, Constants.NBT.TAG_COMPOUND);
		this.inventory = new ItemStack[this.getSizeInventory()];

		ItemUtils.readInventoryContentsFromNBT(this, nbttaglist);

		if (nbt.hasKey(NBTKeys.TILE_NAME)) {
			name = nbt.getString(NBTKeys.TILE_NAME);
		}
		id = nbt.getInteger("PortalID");
		facing = EnumFacing.getFront(nbt.getByte(NBTKeys.TILE_FACING));
		if (nbt.hasKey("metrics")) {
			metrics = PortalMetrics.getMetricsFromNBT(nbt.getCompoundTag("metrics"));
		}
		upgrades.readFromNBT(nbt.getCompoundTag(NBTKeys.TILE_UPGRADE_INVENTORY));
		updateUpgradeInformation();

		if (nbt.hasKey(NBTKeys.CONTROLLER_STATE)) {
			state = State.values()[nbt.getInteger(NBTKeys.CONTROLLER_STATE)];
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList nbttaglist = new NBTTagList();

		ItemUtils.writeInventoryContentsToNBT(this, nbttaglist);
		nbt.setTag(NBTKeys.TILE_MAIN_INVENTORY, nbttaglist);

		if (hasCustomName()) {
			nbt.setString(NBTKeys.TILE_NAME, name);
		}
		nbt.setInteger("PortalID", id);
		nbt.setByte(NBTKeys.TILE_FACING, (byte) facing.getIndex());
		if (metrics != null) {
			NBTTagCompound metricsTag = new NBTTagCompound();
			metrics.writeToNBT(metricsTag);
			nbt.setTag("metrics", metricsTag);
		}
		nbt.setTag(NBTKeys.TILE_UPGRADE_INVENTORY, upgrades.writeToNBT(new NBTTagCompound()));
		return nbt;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = writeToNBT(new NBTTagCompound());
		nbt.setInteger(NBTKeys.CONTROLLER_STATE, state.ordinal());
		return nbt;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte(NBTKeys.TILE_FACING, (byte) facing.getIndex());
		nbt.setInteger(NBTKeys.CONTROLLER_STATE, state.ordinal());
		if (PluginLookingGlass.isAvailable() && metrics != null) {
			nbt.setByte("portalFacing", (byte) metrics.front.ordinal());
			if (isActive()) {
				PortalMetrics target = PortalManager.getInstance().getPortalMetricsForId(portalDestination);
				if (target != null) {
					BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
					if (pos != null) {
						nbt.setInteger("targetDimension", pos.dimension);
						nbt.setByte("targetFacing", (byte) target.front.ordinal());
						nbt.setDouble("targetX", target.originX);
						nbt.setDouble("targetY", target.originY);
						nbt.setDouble("targetZ", target.originZ);
					}
				}
			}
		}
		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		if (nbt.hasKey("portalFacing")) {
			renderInfo = new ClientPortalInfo();
			renderInfo.originDirection = EnumFacing.getFront(nbt.getByte("portalFacing"));
			if (nbt.hasKey("targetFacing")) {
				renderInfo.targetDimension = nbt.getInteger("targetDimension");
				renderInfo.targetDirection = EnumFacing.getFront(nbt.getByte("targetFacing"));
				renderInfo.targetX = nbt.getInteger("targetX");
				renderInfo.targetY = nbt.getInteger("targetY");
				renderInfo.targetZ = nbt.getInteger("targetZ");
			}
		} else {
			renderInfo = null;
		}

		if (nbt.hasKey(NBTKeys.TILE_FACING)) {
			EnumFacing oldFacing = facing;
			facing = EnumFacing.getFront(nbt.getByte(NBTKeys.TILE_FACING));
			State oldState = state;
			setState(State.values()[nbt.getInteger(NBTKeys.CONTROLLER_STATE)]);
			if (oldFacing != facing || oldState != state) {
				final IBlockState blockState = worldObj.getBlockState(getPos());
				this.worldObj.notifyBlockUpdate(getPos(), blockState, blockState, 3);
			}
		}
	}

	/**
	 * connects portal with destination
	 */
	private void initializeConnection() {
		//update state and tick
		setState(State.CONNECTING);
		lastError = Error.NO_ERROR;
		tick = 0;
		//register portal and log warning
		if (id == PortalManager.PORTAL_NOT_CONNECTED) {
			LogHelperMD.warn("Invalid Controller found!");
			LogHelperMD.warn((hasCustomName() ? "Unnamed Controller" : ("Controller " + name)) + " @dim: " + worldObj.provider.getDimension() + ", pos: " + getPos().getX() + "; " + getPos().getY() + "; " + getPos().getZ() + " has no valid id. Registering...");
			id = ModMiningDimension.instance.portalManager.registerNewEntityPortal(new BlockPositionDim(this));
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void handleStartButton(ContainerEntityPortalController containerEntityPortalController) {
		switch (state) {
			case READY:
				initializeConnection();
				break;
			case INCOMING_PORTAL: {
				if (hasUpgrade(FLAG_CAN_REVERSE_PORTAL)) {
					BlockPositionDim pos = PortalManager.getInstance().getEntityPortalForId(portalDestination);
					if (pos != null) {
						TileEntityPortalControllerEntity te = pos.getControllerEntity();
						if (te != null) {
							te.setState(State.INCOMING_PORTAL);
							this.setState(State.OUTGOING_PORTAL);
							this.tick = connectionTime;
							createFXForPortalOpen();
						} else {
							closePortal(true);
						}
					} else {
						closePortal(true);
					}
				}
				break;
			}
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void handleStopButton(ContainerEntityPortalController containerEntityPortalController) {
		switch (state) {
			case CONNECTING:
			case OUTGOING_PORTAL:
				closePortal(true);
				break;
			case INCOMING_PORTAL:
				if (hasUpgrade(FLAG_CAN_DISCONNECT_INCOMING)) {
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
			if (state != State.READY && state != State.NO_MULTIBLOCK) {
				//Play closing sound effect
				createFXForPortalClose();
			}
		}
		//reset state
		if(state != State.NO_MULTIBLOCK) {
			setState(State.READY);
		}
		//release loaded chunks
		unloadChunks();
	}

	@Override
	public void setFacing(EnumFacing f) {
		this.facing = f;
		WorldUtils.notifyBlockUpdateAtTile(this);
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}


	/**
	 * sets an error message and resets state to default
	 */
	private void resetOnError(Error error) {
		setState(State.READY);
		lastError = error;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
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
	@Nonnull
	public ITextComponent getDisplayName() {
		return hasCustomName() ? new TextComponentString(name) : new TextComponentTranslation(Strings.Gui.CONTROLLER_NAME_UNNAMED);
	}

	public void writeDataToStack(ItemStack stack) {
		//noinspection ConstantConditions
		ItemUtils.writeUpgradesToItemStack(getUpgradeInventory(), stack);
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
		getEnergyType().writeToNBT(nbt);
		nbt.setInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID, id);
		if (hasCustomName()) {
			nbt.setString(NBTKeys.DESTINATION_CARD_PORTAL_NAME, getDisplayName().getUnformattedText());
		}
		upgrades = null; //Hack to prevent droping of upgrades when removing using a wrench
	}


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
	 * possible errors when connecting to a portal
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
}
