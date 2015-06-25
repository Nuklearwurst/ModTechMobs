package com.fravokados.techmobs.techdata.effects.player;

import com.fravokados.techmobs.configuration.Settings;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class TDPlayerEffectFakeExplosion extends TDPlayerEffect {
	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= Settings.TDPlayerEffects.FAKE_EXPLOSION_VALUE;
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		//TODO move this to the client
//		entity.worldObj.spawnParticle("largeexplode", entity.posX, entity.posY, entity.posZ, 0, 0, 0);
		entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "random.explode", 4.0F, (1.0F + (entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		return Settings.TDPlayerEffects.FAKE_EXPLOSION_VALUE;
	}
}
