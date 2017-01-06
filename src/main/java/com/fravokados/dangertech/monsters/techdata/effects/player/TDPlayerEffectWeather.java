package com.fravokados.dangertech.monsters.techdata.effects.player;

import com.fravokados.dangertech.api.monsters.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectWeather extends TDPlayerEffect {
	public static final int value = 1000;

	@Override
	public boolean isUsable(int techvalue, UUID uuid, EntityPlayer entity) {
		return techvalue >= value && !entity.getEntityWorld().isRaining();
	}

	@Override
	public int applyEffect(int techvalue, UUID uuid, EntityPlayer entity) {
		entity.getEntityWorld().setRainStrength(1);
		return value;
	}
}
