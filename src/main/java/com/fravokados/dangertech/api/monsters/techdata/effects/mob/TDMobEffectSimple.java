package com.fravokados.dangertech.api.monsters.techdata.effects.mob;

import net.minecraft.entity.EntityLivingBase;

public abstract class TDMobEffectSimple extends TDMobEffect {
	
	protected final int minValue;
	
	public TDMobEffectSimple(int maxValue) {
		this.minValue = maxValue;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase entity) {
		
		return techdata >= minValue;
	}
}
