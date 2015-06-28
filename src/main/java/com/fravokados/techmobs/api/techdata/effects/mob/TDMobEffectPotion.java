package com.fravokados.techmobs.api.techdata.effects.mob;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Nuklearwurst
 */
public class TDMobEffectPotion extends TDMobEffect {
	private final int value;
	private final int potionId;
	private final int duration;
	private final int multiplier;
	private EnumChatFormatting color = null;

	public TDMobEffectPotion(int value, int potionId, int duration, int multiplier) {
		this.value = value;
		this.potionId = potionId;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase entity) {
		return techdata >= value && !entity.isPotionActive(potionId);
	}

	@Override
	public int applyEffect(int techdata, EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(potionId, duration, multiplier));
		return value;
	}

	@Override
	public String toString() {
		return "Potion, id: " + potionId + ", duration: " + duration + ", level:" + multiplier;
	}
}
