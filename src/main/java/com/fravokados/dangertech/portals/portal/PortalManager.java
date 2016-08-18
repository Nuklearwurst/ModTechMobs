package com.fravokados.dangertech.portals.portal;

import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.block.BlockPortalFrame;
import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import com.fravokados.dangertech.portals.lib.util.RotationUtils;
import com.fravokados.dangertech.portals.lib.util.TeleportUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nuklearwurst
 */
public class PortalManager extends WorldSavedData {

	//Destination numbers
	public static final int PORTAL_NOT_CONNECTED = -1;
	public static final int PORTAL_GENERATING = -2;
	public static final int PORTAL_INVALID_ITEM = -3;
	public static final int PORTAL_WRONG_TYPE = -4;
	public static final int PORTAL_INVALID_DIMENSION = -5;
	public static final int PORTAL_NO_SPAWN_FOUND = -6;


	private final Map<Integer, BlockPositionDim> entityPortals = new HashMap<Integer, BlockPositionDim>();
	private int entityPortalCounter = 0;

	public PortalManager(String s) {
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		entityPortals.clear();

		entityPortalCounter = nbt.getInteger("entityPortalCounter");
		int[] keys = nbt.getIntArray("entityPortalKeys");
		for (int portalId : keys) {
			if (nbt.hasKey("entityPortal_" + portalId)) {
				BlockPositionDim pos = new BlockPositionDim();
				pos.readFromNBT(nbt.getCompoundTag("entityPortal_" + portalId));
				entityPortals.put(portalId, pos);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("entityPortalCounter", entityPortalCounter);
		int[] entityPortalKeys = new int[entityPortals.size()];
		int i = 0;
		for (int portalId : entityPortals.keySet()) {
			entityPortalKeys[i] = portalId;
			NBTTagCompound tag = new NBTTagCompound();
			entityPortals.get(portalId).writeToNBT(tag);
			nbt.setTag("entityPortal_" + portalId, tag);
			i++;
		}
		nbt.setIntArray("entityPortalKeys", entityPortalKeys);
		return nbt;
	}

	/**
	 * registers a new entity portal
	 *
	 * @param pos position of the controller
	 * @return new id
	 */
	public int registerNewEntityPortal(BlockPositionDim pos) {
		registerEntityPortal(++entityPortalCounter, pos);
		return entityPortalCounter;
	}

	public boolean entityPortalExists(int portal) {
		return entityPortals.containsKey(portal);
	}

	/**
	 * tries to find a controller registered under the given id
	 *
	 * note: this might load unloaded worlds
	 * @param portal id that will be checked
	 * @return true when no controller could be found
	 */
	public boolean entityPortalIdValidForUse(int portal) {
		if(entityPortalExists(portal)) {
			BlockPositionDim pos = getEntityPortalForId(portal);
			if(pos != null) {
				if(pos.getControllerEntity() != null) {
					return false;
				}
			}
		}
		return true;
	}

	public void registerEntityPortal(int portal, BlockPositionDim pos) {
		entityPortals.put(portal, pos);
		this.markDirty();
	}


	/**
	 * @param entity        entity to teleport
	 * @param portalId      destination portal id
	 * @param originMetrics used to calculate Entity Position in the destination portal (originMetrics of the origin portal)
	 */
	public boolean teleportEntityToEntityPortal(Entity entity, int portalId, int parent, PortalMetrics originMetrics) {
		if (!entityPortalExists(portalId)) {
			return false;
		}
		if (portalId == -1) {
			return false;
		}
		BlockPositionDim pos = getEntityPortalForId(portalId);
		if (pos == null) {
			return false;
		}

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer worldServer = server.worldServerForDimension(pos.dimension);
		TileEntity te = worldServer.getTileEntity(pos.getPosition());
		if (te != null && te instanceof TileEntityPortalControllerEntity) {
			PortalMetrics targetMetrics = ((TileEntityPortalControllerEntity) te).getMetrics();
			if (targetMetrics != null) {
				EnumFacing sideAxisOrigin = originMetrics.top.rotateAround(originMetrics.front.getAxis());
				EnumFacing sideAxisTarget = targetMetrics.top.rotateAround(targetMetrics.front.getAxis());

				double posX = entity.posX - originMetrics.originX;
				double posY = entity.posY - originMetrics.originY;
				double posZ = entity.posZ - originMetrics.originZ;

				////////////////
				//  position  //
				////////////////
				//relative coordinate system
				double posTop = posX * originMetrics.top.getFrontOffsetX() + posY * originMetrics.top.getFrontOffsetY() + posZ * originMetrics.top.getFrontOffsetZ();
				//invert side in order to make player come out of the front of the portal
				double posSide = (posX * sideAxisOrigin.getFrontOffsetX() + posY * sideAxisOrigin.getFrontOffsetY() + posZ * sideAxisOrigin.getFrontOffsetZ()) * -1;

				//make sure player spawns inside portal
				double maxUp = targetMetrics.getMaxUp();
				double minUp = targetMetrics.getMinUp();
				double maxSide = targetMetrics.getMaxSide() - 0.5;
				double minSide = targetMetrics.getMinSide() + 1.5;


				posTop = posTop / (originMetrics.getMaxUp() - originMetrics.getMinUp()) * (maxUp - minUp);
				posSide = posSide / (originMetrics.getMaxSide() - originMetrics.getMinSide()) * (maxSide - minSide);

				if (!targetMetrics.isHorizontal()) {
					//prevent player spawning with head inside portal frame
					posTop = MathHelper.clamp_double(posTop, minUp, maxUp - entity.height); //"Top" Axis
				} else {
					//compensate problems with max/minUp on horizontal portals
					posTop = MathHelper.clamp_double(posTop, minUp + 1.5, maxUp - 0.5); //"Top" Axis
				}
				posSide = MathHelper.clamp_double(posSide, minSide, maxSide); //"Side" Axis

				//target coordinate system
				double x = targetMetrics.originX + targetMetrics.top.getFrontOffsetX() * posTop + sideAxisTarget.getFrontOffsetX() * posSide;
				double y = targetMetrics.originY + targetMetrics.top.getFrontOffsetY() * posTop + sideAxisTarget.getFrontOffsetY() * posSide;
				double z = targetMetrics.originZ + targetMetrics.top.getFrontOffsetZ() * posTop + sideAxisTarget.getFrontOffsetZ() * posSide;

				if (targetMetrics.front == EnumFacing.DOWN) {
					//spawn slighly lower to compensate entity/player height on down facing portals
					y -= entity.height;
				}

				////////////////
				//  momentum  //
				////////////////

				//relative coordinate system
				double speed_rel_x = entity.motionX * originMetrics.front.getFrontOffsetX() + entity.motionY * originMetrics.front.getFrontOffsetY() + entity.motionZ * originMetrics.front.getFrontOffsetZ();
				double speed_rel_y = entity.motionX * originMetrics.top.getFrontOffsetX() + entity.motionY * originMetrics.top.getFrontOffsetY() + entity.motionZ * originMetrics.top.getFrontOffsetZ();
				double speed_rel_z = entity.motionX * sideAxisOrigin.getFrontOffsetX() + entity.motionY * sideAxisOrigin.getFrontOffsetY() + entity.motionZ * sideAxisOrigin.getFrontOffsetZ();

				//target coordinate system
				double speed_x = targetMetrics.front.getFrontOffsetX() * (-1) * speed_rel_x + targetMetrics.top.getFrontOffsetX() * speed_rel_y + sideAxisTarget.getFrontOffsetX() * speed_rel_z;
				double speed_y = targetMetrics.front.getFrontOffsetY() * (-1) * speed_rel_x + targetMetrics.top.getFrontOffsetY() * speed_rel_y + sideAxisTarget.getFrontOffsetY() * speed_rel_z;
				double speed_z = targetMetrics.front.getFrontOffsetZ() * (-1) * speed_rel_x + targetMetrics.top.getFrontOffsetZ() * speed_rel_y + sideAxisTarget.getFrontOffsetZ() * speed_rel_z;

				//update entity
				entity.motionX = speed_x;
				entity.motionY = speed_y;
				entity.motionZ = speed_z;
				entity.velocityChanged = true;
				//update player
				//needed?
//				if(entity instanceof EntityPlayerMP) {
//					ModNetworkManager.INSTANCE.sendTo(new MessagePlayerMotionUpdate((EntityPlayerMP) entity), (EntityPlayerMP) entity);
//				}
				////////////////
				//  rotation  //
				////////////////
				if (originMetrics.isHorizontal()) {
					if (targetMetrics.isHorizontal()) {
						entity.rotationYaw = (entity.rotationYaw + RotationUtils.get2DRotation(originMetrics.top, targetMetrics.top)) % 360;
					} else {
						entity.rotationPitch = (entity.rotationPitch + originMetrics.front.getFrontOffsetY() * (-90)) % 180;
						entity.rotationYaw = (entity.rotationYaw + RotationUtils.get2DRotation(originMetrics.top, targetMetrics.front.getOpposite())) % 360;
					}
				} else {
					if (targetMetrics.isHorizontal()) {
						entity.rotationPitch = (entity.rotationPitch + targetMetrics.front.getOpposite().getFrontOffsetY() * 90) % 180;
						entity.rotationYaw = (entity.rotationYaw + RotationUtils.get2DRotation(originMetrics.front, targetMetrics.top)) % 360;
					} else {
						entity.rotationYaw = (entity.rotationYaw + RotationUtils.get2DRotation(originMetrics.front, targetMetrics.front.getOpposite())) % 360;
					}
				}


				////////////////
				//  transfer  //
				////////////////
				if (entity instanceof EntityPlayerMP) {
					if (pos.dimension == entity.dimension) {
						entity.dismountRidingEntity();
						entity.setPositionAndUpdate(x, y, z);
					} else {
						entity.dismountRidingEntity();
						TeleportUtils.transferPlayerToDimension((EntityPlayerMP) entity, pos.dimension, x, y, z, entity.rotationYaw, entity.rotationPitch);
					}
				} else {
					if (pos.dimension == entity.dimension) {
						entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
					} else {
						TeleportUtils.transferEntityToDimension(entity, pos.dimension, x, y, z, entity.rotationYaw);
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Generates an entity portal in the target dimension
	 * <br/>
	 * note: different behaviour (config) when targeting the same dimension the origin is in
	 * <br/>
	 * also portals, when configured, must origin from the overworld
	 * @param targetDimension target dimension id
	 * @param parent origin portal id
	 * @param metrics metrics of the portal that should get generated (will get modified, to make sure portals don't spawn mid air or underground)
	 * @param parentTile gets used to create the controller
	 * @return the new target portal id
	 */
	public int createPortal(int targetDimension, int parent, PortalMetrics metrics, TileEntity parentTile) {
		BlockPositionDim pos = entityPortals.get(parent);
		if (pos == null) {
			return PORTAL_NOT_CONNECTED;
		}
		//Generate in overworld if needed
		if(pos.dimension == targetDimension) {
			if(Settings.CAN_CREATE_PORTAL_BACK_TO_OVERWORLD) {
				targetDimension = 0;
			} else {
				return PORTAL_INVALID_DIMENSION;
			}
		} else if(pos.dimension != 0 && Settings.CAN_ONLY_ENTER_MINING_DIMENSION_FROM_OVERWORLD) {
			return PORTAL_INVALID_DIMENSION;
		}

		//get the destination world server
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer worldServer = server.worldServerForDimension(targetDimension);

		//create the portal frame
		int yOffset = 0;
		if(Settings.START_SPAWN_SEARCH_FROM_BOTTOM) {
			yOffset = 64 - metrics.minY;
		}
		while (!metrics.isPortalAreaClear(worldServer, yOffset, 1)) {
			if (metrics.maxY + yOffset >= worldServer.getHeight()) {
				//Cancel if we are reaching height limit
				return PORTAL_NO_SPAWN_FOUND;
			}
			yOffset++;
		}
		PortalConstructor.createPortalFromMetrics(worldServer, metrics, true, yOffset);
		//create the controller
		BlockPos controllerPos = pos.getPosition().up(yOffset);
		//noinspection ConstantConditions
		worldServer.setBlockState(controllerPos, ModBlocks.blockPortalFrame.getDefaultState().withProperty(BlockPortalFrame.TYPE_PROPERTY, PortalFrameType.BASIC_CONTROLLER));
		TileEntity te = worldServer.getTileEntity(controllerPos);
		if (te != null && te instanceof TileEntityPortalControllerEntity) {
			if (Settings.PORTAL_SPAWN_WITH_CARD) {
				((TileEntityPortalControllerEntity) te).setInventorySlotContents(0, ItemDestinationCard.fromDestination(parent, ((TileEntityPortalControllerEntity) te).getDisplayName().getUnformattedText()));
			}
			((TileEntityPortalControllerEntity) te).onBlockPostPlaced(te.getWorld(), controllerPos, worldServer.getBlockState(controllerPos));
			if (parentTile instanceof IFacingSix) {
				((TileEntityPortalControllerEntity) te).setFacing(((IFacingSix) parentTile).getFacing());
			}
			return ((TileEntityPortalControllerEntity) te).getId();
		}
		LogHelperMD.warn("Error creating Portal Controller!");
		return PORTAL_NOT_CONNECTED;
	}

	@Nullable
	public BlockPositionDim getEntityPortalForId(int id) {
		return entityPortals.get(id);
	}

	@Nullable
	public PortalMetrics getPortalMetricsForId(int id) {
		BlockPositionDim pos = getEntityPortalForId(id);
		if(pos != null) {
			TileEntityPortalControllerEntity te = pos.getControllerEntity();
			if(te != null) {
				return te.getMetrics();
			}
		}
		return null;
	}

	public boolean removeEntityPortal(int id) {
		if (entityPortals.remove(id) != null) {
			markDirty();
			return true;
		}
		return false;
	}

	public static PortalManager getInstance() {
		return ModMiningDimension.instance.portalManager;
	}
}
