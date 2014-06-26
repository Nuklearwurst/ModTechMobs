package com.fravokados.techmobs.techdata.effects.mob;

import net.minecraft.entity.EntityLivingBase;

/**
 * effect that can be triggered on mobspawns
 * @author Nuklearwurst
 *
 */
public abstract class TDMobEffect {
	
	public abstract boolean isUsable(int techdata, EntityLivingBase entityLiving);
	
	/**
	 * applys the effect
	 * @param techdata the techdata that is available
	 * @param entityLiving the entity to apply the effect to
	 * @return the amount of techdata used
	 */
	public abstract int applyEffect(int techdata, EntityLivingBase entityLiving);

}
