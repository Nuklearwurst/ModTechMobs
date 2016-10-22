package com.fravokados.dangertech.monsters.techdata.effects.player;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectLightning extends TDPlayerEffect {

	public static final int value = 1000;

	@Override
	public boolean isUsable(int techvalue, UUID uuid, EntityPlayer entity) {
		BlockPos pos = new BlockPos(entity);
		return techvalue >= 1000 && entity.getEntityWorld().isRaining() && entity.getEntityWorld().isRainingAt(pos);
	}

	@Override
	public int applyEffect(int techvalue, UUID uuid, EntityPlayer entity) {
		entity.getEntityWorld().addWeatherEffect(new EntityLightningBolt(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, false));
		return value;
	}
}
