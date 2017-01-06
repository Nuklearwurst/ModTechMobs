package com.fravokados.dangertech.api.core.util;

import net.minecraft.util.math.ChunkPos;
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
	
	public ChunkLocation(int dimension, ChunkPos coords) {
		this(dimension, coords.chunkXPos, coords.chunkZPos);
	}
	
	public ChunkLocation(World world, ChunkPos coords) {
		this(world.provider.getDimension(), coords);
	}
	
	public ChunkLocation(Chunk chunk) {
		this(chunk.getWorld(), chunk.getChunkCoordIntPair());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ChunkLocation && ((ChunkLocation) obj).x == x && ((ChunkLocation) obj).z == z && ((ChunkLocation) obj).dimension == dimension;
	}

	public ChunkPos getChunkCoordIntPair() {
		return new ChunkPos(x, z);
	}
}
