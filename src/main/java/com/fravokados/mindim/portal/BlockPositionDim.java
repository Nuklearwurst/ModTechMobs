package com.fravokados.mindim.portal;

import com.fravokados.mindim.block.tileentity.TileEntityPortalControllerEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockPositionDim {
	public int dimension;
	public int x;
	public int y;
	public int z;

	public BlockPositionDim() {
	}

	public BlockPositionDim(int x, int y, int z, int dim) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dim;
	}

	public BlockPositionDim(TileEntity tileEntity) {
		this(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.getWorldObj().provider.dimensionId);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		this.x = nbt.getInteger("x");
		this.y = nbt.getInteger("y");
		this.z = nbt.getInteger("z");
		this.dimension = nbt.getInteger("dim");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		nbt.setInteger("dim", dimension);
	}

	public WorldServer getWorldServer() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		return server.worldServerForDimension(dimension);
	}

	public TileEntityPortalControllerEntity getControllerEntity() {
		World world = getWorldServer();
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityPortalControllerEntity) {
			return (TileEntityPortalControllerEntity) te;
		}
		return null;
	}
}
