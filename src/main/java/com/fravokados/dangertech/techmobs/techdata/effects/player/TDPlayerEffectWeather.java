package com.fravokados.dangertech.techmobs.techdata.effects.player;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectWeather extends TDPlayerEffect {
	public static final int value = 1000;

	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= value && !entity.getEntityWorld().isRaining();
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		entity.getEntityWorld().setRainStrength(1);
		return value;
	}
}
