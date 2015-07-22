package com.fravokados.dangertech.techmobs.lib.util;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldUtils {

	
	public static ChunkCoordIntPair convertToChunkCoord(int x, int z) {
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

	public static ChunkCoordIntPair convertToChunkCoord(float x, float z) {
		return convertToChunkCoord((int)x, (int)z);
	}
	
	public static ChunkCoordIntPair convertToChunkCoord(ChunkCoordinates coords) {
		return convertToChunkCoord(coords.posX, coords.posZ);
	}
}
