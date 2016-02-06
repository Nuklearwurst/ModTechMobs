package com.fravokados.dangertech.core.lib.util;

import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldUtils {

	
	public static ChunkCoordIntPair convertToChunkCoord(int x, int z) {
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

	public static ChunkCoordIntPair convertToChunkCoord(float x, float z) {
		return convertToChunkCoord((int)x, (int)z);
	}
	
	public static ChunkCoordIntPair convertToChunkCoord(BlockPos coords) {
		return convertToChunkCoord(coords.getX(), coords.getZ());
	}
}
