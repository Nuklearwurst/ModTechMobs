package com.fravokados.techmobs.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;

public abstract class TDPlayerEffect {

	/**
	 * This method gets called to gather a list of potential effects<br>
	 * Its result should usually only be dependent on the techvalue, it should be consistent<br>
	 * Note: This method may be called without calling applyEffect afterwards
	 * @param techvalue the potential techvalue that is involved in the effects
	 * @param username the username of the player
	 * @param entity the Player entity
	 * @return whether this effect is applicable to the given Player
	 */
	public abstract boolean isUsable(int techvalue, String username, EntityPlayer entity);

	/**
	 * This method is responsible for applying the effect to the player
	 * @param techvalue the available techdata, you can use this parameter to adjust the effects strength
	 * @param username the username of the player
	 * @param entity the player entity
	 * @return the used techvalue, make sure to not simply return techvalue
	 */
	public abstract int applyEffect(int techvalue, String username, EntityPlayer entity);

}
