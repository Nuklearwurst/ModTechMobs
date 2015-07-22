package com.fravokados.dangertech.api.util;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkLocation {
	
	public final int x;
	public final int z;
	public final int dimension;

	public ChunkLocation(int dimension, int x, int z) {
		this.x = x;
		this.z = z;
		this.dimension = dimension;
	}
	
	public ChunkLocation(int dimension, ChunkCoordIntPair coords) {
		this(dimension, coords.chunkXPos, coords.chunkZPos);
	}
	
	public ChunkLocation(World world, ChunkCoordIntPair coords) {
		this(world.provider.dimensionId, coords);
	}
	
	public ChunkLocation(Chunk chunk) {
		this(chunk.worldObj, chunk.getChunkCoordIntPair());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ChunkLocation && ((ChunkLocation) obj).x == x && ((ChunkLocation) obj).z == z && ((ChunkLocation) obj).dimension == dimension;
	}

	public ChunkCoordIntPair getChunkCoordIntPair() {
		return new ChunkCoordIntPair(x, z);
	}
}
