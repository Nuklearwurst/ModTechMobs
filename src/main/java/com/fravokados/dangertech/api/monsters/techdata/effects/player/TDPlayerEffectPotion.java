package com.fravokados.dangertech.api.monsters.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public class TDPlayerEffectPotion extends TDPlayerEffect {

	private final int value;
	private final Potion potion;
	private final int duration;
	private final int multiplier;
	private ITextComponent chatMessage = null;

	public TDPlayerEffectPotion(int value, Potion potion, int duration, int multiplier) {
		this.value = value;
		this.potion = potion;
		this.duration = duration;
		this.multiplier = multiplier;
	}

	public TDPlayerEffectPotion(int value, int potionId, int duration, int multiplier, ITextComponent msg) {
		this(value, Potion.getPotionById(potionId), duration, multiplier, msg);
	}

	public TDPlayerEffectPotion(int value, Potion potion, int duration, int multiplier, ITextComponent msg) {
		this(value, potion, duration, multiplier);
		this.chatMessage = msg;
	}

	public TDPlayerEffectPotion setMessage(ITextComponent msg) {
		this.chatMessage = msg;
		return this;
	}
	
	public TDPlayerEffectPotion setMessageAndColor(ITextComponent msg) {
		this.chatMessage = msg;
		return this;
	}


	@Override
	public boolean isUsable(int techvalue, UUID uuid, EntityPlayer entity) {
		return techvalue >= value && !entity.isPotionActive(potion);
	}

	@Override
	public int applyEffect(int techvalue, UUID uuid, EntityPlayer entity) {
		entity.addPotionEffect(new PotionEffect(potion, duration, multiplier));
		if(chatMessage != null) {
			chatMessage.getStyle().setItalic(true);
			entity.sendMessage(chatMessage);
		}
		return value;
	}

	@Override
	public String toString() {
		return "Potion, id: " + potion + ", duration: " + duration + ", level:" + multiplier;
	}

}
