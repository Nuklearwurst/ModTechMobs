package com.fravokados.dangertech.monsters.techdata.effects.player;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectMobSpawn extends TDPlayerEffect {

	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return false;
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer player) {

		return 0;
	}
}
