package com.fravokados.dangertech.api.techdata.effects.mob;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Nuklearwurst
 */
public class TDMobEffectPotion extends TDMobEffect {
	private final int value;
	private final Potion potion;
	private final int duration;
	private final int multiplier;
	private TextFormatting color = null;

	public TDMobEffectPotion(int value, int potionId, int duration, int multiplier) {
		this(value, Potion.getPotionById(potionId), duration, multiplier);
	}

	public TDMobEffectPotion(int value, Potion potion, int duration, int multiplier) {
		this.value = value;
		this.potion = potion;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase entity) {
		return techdata >= value && !entity.isPotionActive(potion);
	}

	@Override
	public int applyEffect(int techdata, EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(potion, duration, multiplier));
		return value;
	}

	@Override
	public String toString() {
		return "Potion, id: " + potion + ", duration: " + duration + ", level:" + multiplier;
	}
}
