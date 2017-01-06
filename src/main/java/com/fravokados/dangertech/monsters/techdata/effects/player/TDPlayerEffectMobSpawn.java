package com.fravokados.dangertech.monsters.techdata.effects.player;

import com.fravokados.dangertech.api.monsters.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectMobSpawn extends TDPlayerEffect {

	@Override
	public boolean isUsable(int techvalue, UUID uuid, EntityPlayer entity) {
		return false;
	}

	@Override
	public int applyEffect(int techvalue, UUID uuid, EntityPlayer player) {

		return 0;
	}
}
