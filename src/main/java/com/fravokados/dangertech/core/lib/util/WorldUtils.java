package com.fravokados.dangertech.core.lib.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class WorldUtils {

	
	public static ChunkPos convertToChunkCoord(int x, int z) {
		return new ChunkPos(x >> 4, z >> 4);
	}

	public static ChunkPos convertToChunkCoord(float x, float z) {
		return convertToChunkCoord((int)x, (int)z);
	}
	
	public static ChunkPos convertToChunkCoord(BlockPos coords) {
		return convertToChunkCoord(coords.getX(), coords.getZ());
	}

	public static void notifyBlockUpdateAtTile(TileEntity te) {
		final IBlockState state = te.getWorld().getBlockState(te.getPos());
		te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 3);
	}
}
