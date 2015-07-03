package com.fravokados.techmobs.techdata.effects.chunk;

import com.fravokados.techmobs.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.techmobs.api.util.ChunkLocation;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class TDChunkEffectRain extends TDChunkEffect {

	private static final int value = 5000;

	@Override
	public boolean isUsable(int techvalue, World world) {
		return techvalue >= value && !world.isRaining();
	}

	@Override
	public int applyEffect(int level, ChunkLocation chunkLocation, World world) {
		world.setRainStrength(1);
		return value;
	}
}
