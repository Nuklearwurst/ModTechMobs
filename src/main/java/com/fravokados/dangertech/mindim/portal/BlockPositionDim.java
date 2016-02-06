package com.fravokados.dangertech.mindim.portal;

import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class BlockPositionDim {

	public int dimension;
	public BlockPos position;

	public BlockPositionDim() {
	}

	public BlockPositionDim(BlockPos pos, int dim) {
		this.position = pos;
		this.dimension = dim;
	}

	public BlockPositionDim(int x, int y, int z, int dim) {
		this.position = new BlockPos(x, y, z);
		this.dimension = dim;
	}

	public BlockPositionDim(TileEntity tileEntity) {
		this(tileEntity.getPos(), tileEntity.getWorld().provider.getDimensionId());
	}

	public int getDimension() {
		return dimension;
	}

	public BlockPos getPosition() {
		return position;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		this.position = BlockUtils.readBlockPosFromNBT(nbt);
		this.dimension = nbt.getInteger("dim");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		BlockUtils.writeBlockPosToNBT(position, nbt);
		nbt.setInteger("dim", dimension);
	}

	public WorldServer getWorldServer() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		return server.worldServerForDimension(dimension);
	}

	public TileEntityPortalControllerEntity getControllerEntity() {
		World world = getWorldServer();
		TileEntity te = world.getTileEntity(position);
		if (te != null && te instanceof TileEntityPortalControllerEntity) {
			return (TileEntityPortalControllerEntity) te;
		}
		return null;
	}
}
