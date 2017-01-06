package com.fravokados.dangertech.monsters.techdata.effects.mob;

import com.fravokados.dangertech.api.monsters.techdata.effects.mob.TDMobEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;

/**
 * @author Nuklearwurst
 */
public class TDMobEffectChargedCreeper extends TDMobEffect {

	private static final int value = 200;

	@Override
	public boolean isUsable(int techdata, EntityLivingBase entityLiving) {
		return techdata >= 200 && entityLiving instanceof EntityCreeper;
	}

	@Override
	public int applyEffect(int techdata, EntityLivingBase entityLiving) {
		entityLiving.onStruckByLightning(null);
		return value;
	}
}
