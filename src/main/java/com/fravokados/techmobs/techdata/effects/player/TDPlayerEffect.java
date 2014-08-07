package com.fravokados.techmobs.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;

import com.fravokados.techmobs.world.techdata.TDPlayer;

public abstract class TDPlayerEffect {
	
	public abstract boolean isUsable(int techvalue, String username, TDPlayer player);
	
	public abstract int applyEffect(int techvalue, String username, TDPlayer player, EntityPlayer entity);

}
