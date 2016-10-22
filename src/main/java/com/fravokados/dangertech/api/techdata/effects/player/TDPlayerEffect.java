package com.fravokados.dangertech.api.techdata.effects.player;

import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public abstract class TDPlayerEffect {

	/**
	 * This method gets called to gather a list of potential effects<br>
	 * Its result should usually only be dependent on the techvalue, it should be consistent<br>
	 * Note: This method may be called without calling applyEffect afterwards
	 * @param techvalue the potential techvalue that is involved in the effects
	 * @param uuid the uuid of the player
	 * @param entity the Player entity
	 * @return whether this effect is applicable to the given Player
	 */
	public abstract boolean isUsable(int techvalue, UUID uuid, EntityPlayer entity);

	/**
	 * This method is responsible for applying the effect to the player
	 * @param techvalue the available techdata, you can use this parameter to adjust the effects strength
	 * @param uuid the uuid of the player
	 * @param entity the player entity
	 * @return the used techvalue, make sure to not simply return techvalue
	 */
	public abstract int applyEffect(int techvalue, UUID uuid, EntityPlayer entity);

}
