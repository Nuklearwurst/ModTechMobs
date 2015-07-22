package com.fravokados.dangertech.api.techdata.effects.chunk;

import com.fravokados.dangertech.api.util.ChunkLocation;
import net.minecraft.world.World;

public abstract class TDChunkEffect {

	public abstract boolean isUsable(int techdata, World world);

	public abstract int applyEffect(int level, ChunkLocation chunkLocation, World world);
}
