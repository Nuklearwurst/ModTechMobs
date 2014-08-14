package com.fravokados.techmobs.techdata.effects;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.entity.ai.EntityAIScanArea;
import com.fravokados.techmobs.lib.util.world.WorldHelper;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffect;
import com.fravokados.techmobs.techdata.effects.player.TDPlayerEffect;


/**
 * applies the effects
 * @author Nuklearwurst
 *
 */
public class TDEffectHandler {
	
	public static void onLivingSpawn(LivingSpawnEvent.CheckSpawn evt) {
		if(!(evt.entityLiving instanceof IMob)) {
			return;
		}
		
		/////////////////////////
		
		if(Settings.TechScanning.INJECT_SCANNING_AI) {
			//WIP
			if(evt.entityLiving instanceof EntityZombie || evt.entityLiving instanceof EntitySkeleton) {
				EntityLiving e = ((EntityLiving)evt.entityLiving);
				e.tasks.addTask(3, new EntityAIScanArea(e));
			}
		}
		
		////////////////////////
		
		
		//localize and read chunk
		ChunkCoordIntPair coord = WorldHelper.convertToChunkCoord(evt.x, evt.z);
		int level = TDManager.getScoutedTechLevel(evt.world.provider.dimensionId, coord);
		if(level <= 0) {
			return;
		}
		
		if(evt.world.rand.nextInt(Settings.TechData.TD_EFFECT_CHANCE) < level) {
			int min = (int) (Settings.TechData.TD_EFFECT_MIN + Math.floor(level * Settings.TechData.TD_EFFECT_MIN_FACTOR));
			if(level > min) {
				level = min + evt.world.rand.nextInt(level - min);				
			}
			int i = 0;
			List<TDMobEffect> effects = TDEffects.getUsableMobEffects(level, evt.entityLiving);
			while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_MOB) {
				//randomize effect order
				level -= effects.get(evt.world.rand.nextInt(effects.size())).applyEffect(level, evt.entityLiving);
				i++;
				//update effectlist
				effects = TDEffects.getUsableMobEffects(level, evt.entityLiving);
			}
		}
	}
	
	public static void applyRandomEffectOnPlayer(EntityPlayer entity, String username, Random rand) {
		int level = TDManager.getPlayerScoutedTechLevel(entity);
		int i = 0;
		List<TDPlayerEffect> effects = TDEffects.getUsablePlayerEffects(level, username, entity);
		while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_PLAYER) {
			level -= effects.get(rand.nextInt(effects.size())).applyEffect(level, username, MinecraftServer.getServer().getConfigurationManager().func_152612_a(username));
			i++;
			effects = TDEffects.getUsablePlayerEffects(level, username,  entity);
		}
		TDManager.setPlayerScoutedTechLevel(entity, level);
	}
	
	
	
	

}
