package com.fravokados.techmobs.api.techdata.effects.chunk;

import com.fravokados.techmobs.api.util.ChunkLocation;
import net.minecraft.world.World;

public abstract class TDChunkEffect {

	public abstract boolean isUsable(int techdata, World world);

	public abstract int applyEffect(int level, ChunkLocation chunkLocation, World world);
}
