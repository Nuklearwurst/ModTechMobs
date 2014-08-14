package com.fravokados.techmobs.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;

public class TDPlayerEffectPotion extends TDPlayerEffect {
	
	private int value;
	private int potionId;
	private int duration;
	private int multiplier;
	
	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier) {
		this.value = value;
		this.potionId = potionId;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= value && !entity.isPotionActive(potionId);
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		entity.addPotionEffect(new PotionEffect(potionId, duration, multiplier));
		//DEBUG
		entity.addChatMessage(new ChatComponentText("Potion"));
		return value;
	}
	
	@Override
	public String toString() {
		return "Potion, id: " + potionId + ", duration: " + duration + ", level:" + multiplier;
	}

}
