package com.fravokados.dangertech.api.monsters.techdata.effects;

import com.fravokados.dangertech.api.monsters.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.dangertech.api.monsters.techdata.effects.mob.TDMobEffect;
import com.fravokados.dangertech.api.monsters.techdata.effects.player.TDPlayerEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * @author Nuklearwurst
 */
public interface IEffectRegistry {
	void addMobEffect(TDMobEffect effect);

	void addPlayerEffect(TDPlayerEffect effect);

	void addChunkEffect(TDChunkEffect effect);

	List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving);

	List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, UUID uuid, EntityPlayer entity);

	List<TDChunkEffect> getUsableWorldEffects(int level, World world);
}
