package com.fravokados.techmobs.techdata.effects;

import net.minecraft.entity.EntityLivingBase;

public abstract class TDMobEffectSimple extends TDMobEffect {
	
	protected int minValue;
	
	public TDMobEffectSimple(int maxValue) {
		this.minValue = maxValue;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase entity) {
		
		return techdata >= minValue;
	}
}
