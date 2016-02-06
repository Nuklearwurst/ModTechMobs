package com.fravokados.dangertech.mindim.portal;

import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.api.portal.IEntityPortalComponent;
import com.fravokados.dangertech.api.portal.IEntityPortalMandatoryComponent;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.mindim.block.BlockPortalFrame;
import com.fravokados.dangertech.mindim.block.BlockPortalMinDim;
import com.fravokados.dangertech.mindim.block.ModBlocks;
import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class PortalMetrics {

	public enum Type {
		ENTITY_PORTAL("portal.entity");

		public final String name;

		Type(String name) {
			this.name = name;
		}

		public static String getType(int i) {
			return Type.values()[i].name;
		}

		public static String getLocalizedName(int i) {
			return Strings.translate(getType(i));
		}
	}

	public EnumFacing top = EnumFacing.UP;
	public EnumFacing front = EnumFacing.EAST;

	public double originX;
	public double originY;
	public double originZ;


	public int minX;
	public int minY;
	public int minZ;

	public int maxX;
	public int maxY;
	public int maxZ;

	private boolean init = false;

	public PortalMetrics() {

	}

	public PortalMetrics(double xCoord, double yCoord, double zCoord) {
		this.originX = xCoord;
		this.originY = yCoord;
		this.originZ = zCoord;
		minX = maxX = (int) originX;
		minY = maxY = (int) originY;
		minZ = maxZ = (int) originZ;
		init = true;
	}

	public boolean isBlockInsideFrame(int x, int y, int z) {
		return x >= minX + 1 && x <= maxX - 1 && y >= minY + 1 && y <= maxY - 1 && z >= minZ + 1 && z <= maxZ - 1;
	}

	public boolean isBlockInside(int x, int y, int z) {
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public boolean isHorizontal() {
		return minY == maxY;
	}

	public void addCoord(int x, int y, int z) {
		if(init) {
			minX = Math.min(x, minX);
			minY = Math.min(y, minY);
			minZ = Math.min(z, minZ);
			maxX = Math.max(x, maxX);
			maxY = Math.max(y, maxY);
			maxZ = Math.max(z, maxZ);
		} else {
			minX = maxX = x;
			minY = maxY = y;
			minZ = maxZ = z;
			init = true;
		}
	}

	public void addCoord(TileEntity te) {
		BlockPos pos = te.getPos();
		addCoord(pos.getX(), pos.getY(), pos.getZ());
	}

	public int biggestDimension() {
		return Math.max(Math.max(maxX - minX, maxY - minY), maxZ - minZ);
	}

	public int smallestDimension() {
		int x = maxX - minX;
		int y = maxY - minY;
		int z = maxZ - minZ;
		if(x == 0) {
			return Math.min(y, z);
		}
		if(y == 0) {
			return Math.min(x, z);
		}
		if(z == 0) {
			return Math.min(x, y);
		}
		return 0;
	}

	@SuppressWarnings("ConstantConditions")
	public boolean placePortalsInsideFrame(World world, BlockPos pos) {
		for(int j = minX; j <= maxX; j++) { //X
			for(int k = minY; k <= maxY; k++) { //Y
				for(int l = minZ; l <= maxZ; l++) { //Z
					if(minX - maxX != 0 && (j == minX || j == maxX)) {
						continue;
					}
					if(minY - maxY != 0 && (k == minY || k == maxY)) {
						continue;
					}
					if(minZ - maxZ != 0 && (l == minZ || l == maxZ)) {
						continue;
					}
					BlockPortalMinDim.placePortalInWorld(world, new BlockPos(j, k, l), pos, front.getAxis());
				}
			}
		}
		return true;
	}

	/**
	 * removes Portal Blocks inside metrics
	 * @param world the world
	 */
	@SuppressWarnings("ConstantConditions")
	public void removePortalsInsideFrame(World world) {
		for(int j = minX; j <= maxX; j++) { //X
			for(int k = minY; k <= maxY; k++) { //Y
				for(int l = minZ; l <= maxZ; l++) { //Z
					if(minX - maxX != 0 && (j == minX || j == maxX)) {
						continue;
					}
					if(minY - maxY != 0 && (k == minY || k == maxY)) {
						continue;
					}
					if(minZ - maxZ != 0 && (l == minZ || l == maxZ)) {
						continue;
					}
					BlockPos pos = new BlockPos(j, k, l);
					Block block = world.getBlockState(pos).getBlock();
					if(block instanceof BlockPortalMinDim) {
						world.setBlockToAir(pos);
					}
				}
			}
		}
	}

	public void setOrigin(double x, double y, double z) {
		originX = x;
		originY = y;
		originZ = z;
	}

	public void calculateOrigin(List<IEntityPortalMandatoryComponent> list) {
		if(isHorizontal()) {
			originY = minY + 0.5; //center in horizontal portals
		} else {
			originY = minY + 1; //ground om vertical portals
		}
		originX = minX + (((double)(maxX - minX)) / 2 + 0.5);
		originZ = minZ + (((double)(maxZ - minZ)) / 2 + 0.5);
		//Orientation
		int[] orientation = {0, 0, 0, 0, 0, 0};
		for(IEntityPortalMandatoryComponent frame : list) {
			orientation[frame.getFacing()]++;
		}
		int facing = 0;
		int max = 0;
		for(int i = 0; i < 6; i++) {
			if(orientation[i] > max) {
				facing = i;
				max = orientation[i];
			}
		}
		front = EnumFacing.getFront(facing);
		if(maxX - minX == 0) {
			top = EnumFacing.UP;
			if(front != EnumFacing.EAST && front != EnumFacing.WEST) {
				front = EnumFacing.EAST;
			}
		} else if(maxY - minY == 0) {
			if(front != EnumFacing.DOWN && front != EnumFacing.UP) {
				front = EnumFacing.UP;
			}
			top = front == EnumFacing.UP ? EnumFacing.EAST : EnumFacing.WEST;
		} else if(maxZ - minZ == 0) {
			top = EnumFacing.UP;
			if(front != EnumFacing.NORTH && front != EnumFacing.SOUTH) {
				front = EnumFacing.NORTH;
			}
		}
	}

	public boolean isEntityInsidePortal(Entity e, double padding) {
		return  e.posX >= minX - padding && e.posX <= maxX + padding + 1
				&& e.posY >= minY - padding && e.posY <= maxY + padding + 1
				&& e.posZ >= minZ - padding && e.posZ <= maxZ + padding + 1;
	}

	@SuppressWarnings("ConstantConditions")
	public boolean isFrameEmpty(World world) {
		boolean flagX;
		boolean flagY;
		boolean flagZ;
		for(int x = minX; x <= maxX; x++) {
			flagX = x == minX || x == maxX;
			for(int y = minY; y <= maxY; y++) {
				flagY = y == minY || y == maxY;
				for(int z = minZ; z <= maxZ; z++) {
					flagZ = z == minZ || z == maxZ;
					if(!(flagX == flagY ? flagX : flagZ)) {
						BlockPos pos = new BlockPos(x, y, z);
						if(!world.isAirBlock(pos) && world.getBlockState(pos) != ModBlocks.blockPortalBlock) {
							//skip portal block when searching for empty frame to allow client to update metrics when a portal is already opened
							//see ClientPortalInfo for more information
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	public boolean isFrameComplete(World world) {
		//These flags are true if the coordinate is on the frame (two out of three)
		boolean flagX;
		boolean flagY;
		boolean flagZ;
		for(int x = minX; x <= maxX; x++) {
			flagX = x == minX || x == maxX;
			for(int y = minY; y <= maxY; y++) {
				flagY = y == minY || y == maxY;
				for(int z = minZ; z <= maxZ; z++) {
					flagZ = z == minZ || z == maxZ;
					if(flagX == flagY ? flagX : flagZ) {
						BlockPos pos = new BlockPos(x, y, z);
						TileEntity te = world.getTileEntity(pos);
						if(te == null || !(te instanceof IEntityPortalComponent)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}


	public boolean isPortalAreaClear(World world, int yOffset, int padding) {
		//generate padding
		int x = maxX - minX;
		int y = maxY - minY;
		int z = maxZ - minZ;
		if(x == 0) {
			x = padding;
			y = 0;
			z = 0;
		} else if(y == 0) {
			x = 0;
			y = padding;
			z = 0;
		} else if(z == 0) {
			x = 0;
			y = 0;
			z = padding;
		}
		//update coordinates
		int minX = this.minX - x;
		int minY = this.minY + yOffset - y;
		int minZ = this.minZ - z;
		int maxX = this.maxX + x;
		int maxY = this.maxY + yOffset + y;
		int maxZ = this.maxZ + z;
		for(int i = minX; i <= maxX; i++) {
			for(int j = minY; j <= maxY; j++) {
				for(int k = minZ; k <= maxZ; k++) {
					BlockPos pos = new BlockPos(i, j, k);
					if(!BlockUtils.isBlockReplaceable(world, pos)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("originX", originX);
		nbt.setDouble("originY", originY);
		nbt.setDouble("originZ", originZ);
		nbt.setInteger("minX", minX);
		nbt.setInteger("minY", minY);
		nbt.setInteger("minZ", minZ);
		nbt.setInteger("maxX", maxX);
		nbt.setInteger("maxY", maxY);
		nbt.setInteger("maxZ", maxZ);
		nbt.setInteger("top", top.ordinal());
		nbt.setInteger("front", front.ordinal());
	}

	public void readFromNBT(NBTTagCompound nbt) {
		originX = nbt.getDouble("originX");
		originY = nbt.getDouble("originY");
		originZ = nbt.getDouble("originZ");
		minX = nbt.getInteger("minX");
		minY = nbt.getInteger("minY");
		minZ = nbt.getInteger("minZ");
		maxX = nbt.getInteger("maxX");
		maxY = nbt.getInteger("maxY");
		maxZ = nbt.getInteger("maxZ");
		top = EnumFacing.getFront(nbt.getInteger("top"));
		front = EnumFacing.getFront(nbt.getInteger("front"));
	}

	public static PortalMetrics getMetricsFromNBT(NBTTagCompound nbt) {
		if(nbt == null) {
			return null;
		}
		PortalMetrics metrics = new PortalMetrics();
		metrics.readFromNBT(nbt);
		return metrics;
	}

	public double getMaxUp() {
		return getMaxForFace(top);
	}

	public double getMinUp() {
		return getMinForFace(top);
	}

	public double getMaxSide() {
		EnumFacing side = top.rotateAround(front.getAxis());
		return getMaxForFace(side);
	}

	public double getMinSide() {
		EnumFacing side = top.rotateAround(front.getAxis());
		return getMinForFace(side);
	}

	@SuppressWarnings("Duplicates")
	private double getMaxForFace(EnumFacing face) {
		if(face.getFrontOffsetX() != 0) {
			return maxX - originX;
		} else if(face.getFrontOffsetY() != 0) {
			return  maxY - originY;
		} else if(face.getFrontOffsetZ() != 0) {
			return maxZ - originZ;
		}
		return 0;
	}

	@SuppressWarnings("Duplicates")
	private double getMinForFace(EnumFacing face) {
		if(face.getFrontOffsetX() != 0) {
			return minX - originX;
		} else if(face.getFrontOffsetY() != 0) {
			return  minY - originY;
		} else if(face.getFrontOffsetZ() != 0) {
			return minZ - originZ;
		}
		return 0;
	}

	/**
	 *
	 * @return returns how many blocks are needed to form the frame of this portal
	 */
	public int getFrameBlockCount() {
		return (maxX - minX) * 2 + (maxY - minY) * 2 + (maxZ - minZ) *2;
	}

	/**
	 * creates a portal with x, y, z as its origin
	 * @param world The World to create the Portal in
	 * @param x originX
	 * @param y originY
	 * @param z originZ
	 */
	@SuppressWarnings("ConstantConditions")
	public boolean placePortalFrame(World world, int x, int y, int z, boolean clearInside) {
		int minX = this.minX - (int) (originX);
		int minY = this.minY - (int) (originY);
		int minZ = this.minZ - (int) (originZ);
		int maxX = this.maxX - (int) (originX);
		int maxY = this.maxY - (int) (originY);
		int maxZ = this.maxZ - (int) (originZ);
		boolean flagX;
		boolean flagY;
		boolean flagZ;
		for(int i = minX; i <= maxX; i++) {
			flagX = i == minX || i == maxX;
			for(int j = minY; j <= maxY; j++) {
				flagY = j == minY || j == maxY;
				for(int k = minZ; k <= maxZ; k++) {
					flagZ = k == minZ || k == maxZ;
					BlockPos pos = new BlockPos(x + i, y + j, z + k);
					if(flagX == flagY ? flagX : flagZ) {
						world.setBlockState(pos, ModBlocks.blockPortalFrame.getDefaultState().withProperty(BlockPortalFrame.TYPE_PROPERTY, PortalFrameType.BASIC_FRAME));
						TileEntity te = world.getTileEntity(pos);
						if(te != null && te instanceof IFacingSix) {
							((IFacingSix) te).setFacing((short) front.ordinal());
						}
					} else {
						world.setBlockState(pos, Blocks.air.getDefaultState());
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	public void updatePortalFrames(World worldObj) {
		boolean flagX;
		boolean flagY;
		boolean flagZ;
		for(int i = minX; i <= maxX; i++) {
			flagX = i == minX || i == maxX;
			for(int j = minY; j <= maxY; j++) {
				flagY = j == minY || j == maxY;
				for(int k = minZ; k <= maxZ; k++) {
					flagZ = k == minZ || k == maxZ;
					if(flagX == flagY ? flagX : flagZ) {
						BlockPos pos = new BlockPos(i, j, k);
						worldObj.markBlockForUpdate(pos);
					}
				}
			}
		}
	}
}

