package com.fravokados.techmobs.techdata;

import java.util.List;

import net.minecraft.entity.monster.IMob;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.lib.util.WorldHelper;
import com.fravokados.techmobs.techdata.effects.TDMobEffect;


/**
 * applies the effects
 * @author Nuklearwurst
 *
 */
public class TDEffectManager {
	
	public static void onLivingSpawn(LivingSpawnEvent.CheckSpawn evt) {
		if(!(evt.entityLiving instanceof IMob)) {
			return;
		}
		ChunkCoordIntPair coord = WorldHelper.convertToChunkCoord(evt.x, evt.z);
//		LogHelper.info(coord);
		int level = TDManager.getScoutedTechLevel(evt.world.provider.dimensionId, coord);
		if(level <= 0) {
			return;
		}
//		LogHelper.info("Mobfound--techlevel high");
		if(evt.world.rand.nextInt(Settings.TechData.TD_EFFECT_CHANCE) < level) {
//			LogHelper.info("Starting effects...");
			int min = (int) (Settings.TechData.TD_EFFECT_MIN + Math.floor(level * Settings.TechData.TD_EFFECT_MIN_FACTOR));
			if(level > min) {
				level = min + evt.world.rand.nextInt(level - min);				
			}
			int i = 0;
			List<TDMobEffect> effects = TDEffects.getUsableMobEffects(level, evt.entityLiving);
			while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS) {
				level -= effects.get(evt.world.rand.nextInt(effects.size())).applyEffect(level, evt.entityLiving);
				i++;
				effects = TDEffects.getUsableMobEffects(level, evt.entityLiving);
//				LogHelper.info("Effect !!!");
			}
		}
	}
	
	
	

}
