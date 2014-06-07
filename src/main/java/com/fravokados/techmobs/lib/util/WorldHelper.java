package com.fravokados.techmobs.lib.util;

import net.minecraft.world.ChunkCoordIntPair;

public class WorldHelper {

	
	public static ChunkCoordIntPair convertToChunkCoord(int x, int z) {
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

	public static ChunkCoordIntPair convertToChunkCoord(float x, float z) {
		return convertToChunkCoord((int)x, (int)z);
	}
}
