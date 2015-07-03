package com.fravokados.techmobs.techdata.effects.chunk;

import com.fravokados.techmobs.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.techmobs.api.util.ChunkLocation;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class TDChunkEffectThunder extends TDChunkEffect {

	public static final int value = 10000;

	@Override
	public boolean isUsable(int techdata, World world) {
		return techdata >= value && !world.isThundering() && world.isRaining();
	}

	@Override
	public int applyEffect(int level, ChunkLocation chunkLocation, World world) {
		world.setThunderStrength(1);
		return value;
	}
}
