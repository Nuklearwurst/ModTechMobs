package com.fravokados.techmobs.api.techdata.effects;

import com.fravokados.techmobs.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.techmobs.api.techdata.effects.mob.TDMobEffect;
import com.fravokados.techmobs.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public interface TDEffectRegistry {
	void addMobEffect(TDMobEffect effect);

	void addPlayerEffect(TDPlayerEffect effect);

	void addWorldEffect(TDChunkEffect effect);

	List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving);

	List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, String username, EntityPlayer entity);

	List<TDChunkEffect> getUsableWorldEffects(int level, World world);
}
