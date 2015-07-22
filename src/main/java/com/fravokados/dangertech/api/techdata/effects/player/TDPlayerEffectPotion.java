package com.fravokados.dangertech.api.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IChatComponent;

public class TDPlayerEffectPotion extends TDPlayerEffect {

	private final int value;
	private final int potionId;
	private final int duration;
	private final int multiplier;
	private IChatComponent chatMessage = null;

	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier) {
		this.value = value;
		this.potionId = potionId;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier, IChatComponent msg) {
		this(value, potionId, duration, multiplier);
		this.chatMessage = msg;
	}

	public TDPlayerEffectPotion setMessage(IChatComponent msg) {
		this.chatMessage = msg;
		return this;
	}
	
	public TDPlayerEffectPotion setMessageAndColor(IChatComponent msg) {
		this.chatMessage = msg;
		return this;
	}


	@Override
	public boolean isUsable(int techvalue, String username, EntityPlayer entity) {
		return techvalue >= value && !entity.isPotionActive(potionId);
	}

	@Override
	public int applyEffect(int techvalue, String username, EntityPlayer entity) {
		entity.addPotionEffect(new PotionEffect(potionId, duration, multiplier));
		if(chatMessage != null) {
			chatMessage.getChatStyle().setItalic(true);
			entity.addChatComponentMessage(chatMessage);
		}
		return value;
	}

	@Override
	public String toString() {
		return "Potion, id: " + potionId + ", duration: " + duration + ", level:" + multiplier;
	}

}
