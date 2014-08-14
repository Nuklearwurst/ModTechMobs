package com.fravokados.techmobs.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;

public abstract class TDPlayerEffect {
	
	public abstract boolean isUsable(int techvalue, String username, EntityPlayer entity);
	
	public abstract int applyEffect(int techvalue, String username, EntityPlayer entity);

}
