package com.fravokados.dangertech.techmobs.techdata.effects.player;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectLightning extends TDPlayerEffect {

	public static final int value = 1000;

	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= 1000 && entity.getEntityWorld().isRaining() && entity.getEntityWorld().canLightningStrikeAt(entity.serverPosX, entity.serverPosY, entity.serverPosZ);
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		entity.getEntityWorld().spawnEntityInWorld(new EntityLightningBolt(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ));
		return value;
	}
}
