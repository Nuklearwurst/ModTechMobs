package com.fravokados.techmobs.api.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class TDPlayerEffectPotion extends TDPlayerEffect {

	private final int value;
	private final int potionId;
	private final int duration;
	private final int multiplier;
	private String chatMessage = null;
	private EnumChatFormatting color = null;

	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier) {
		this.value = value;
		this.potionId = potionId;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier, String msg) {
		this(value, potionId, duration, multiplier);
		this.chatMessage = msg;
	}

	public TDPlayerEffectPotion setMessage(String msg) {
		this.chatMessage = msg;
		return this;
	}

	public TDPlayerEffectPotion setMessageColor(EnumChatFormatting f) {
		color = f;
		return this;
	}
	
	public TDPlayerEffectPotion setMessageAndColor(String msg, EnumChatFormatting f) {
		this.chatMessage = msg;
		this.color = f;
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
			ChatComponentTranslation chat = new ChatComponentTranslation(chatMessage);
			if(color != null) {
				chat.getChatStyle().setColor(color);
			}
			entity.addChatComponentMessage(chat);
		}
		return value;
	}

	@Override
	public String toString() {
		return "Potion, id: " + potionId + ", duration: " + duration + ", level:" + multiplier;
	}

}
