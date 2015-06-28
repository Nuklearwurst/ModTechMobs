package com.fravokados.techmobs.techdata.effects.player;

import com.fravokados.techmobs.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectWeather extends TDPlayerEffect {
	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= 100 && !entity.getEntityWorld().isRaining();
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		entity.getEntityWorld().setRainStrength(1);
		return 100;
	}
}
