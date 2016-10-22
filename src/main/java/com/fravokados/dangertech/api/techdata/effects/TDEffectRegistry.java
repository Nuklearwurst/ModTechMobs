package com.fravokados.dangertech.api.techdata.effects;

import com.fravokados.dangertech.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.dangertech.api.techdata.effects.mob.TDMobEffect;
import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * @author Nuklearwurst
 */
public interface TDEffectRegistry {
	void addMobEffect(TDMobEffect effect);

	void addPlayerEffect(TDPlayerEffect effect);

	void addChunkEffect(TDChunkEffect effect);

	List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving);

	List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, UUID uuid, EntityPlayer entity);

	List<TDChunkEffect> getUsableWorldEffects(int level, World world);
}
