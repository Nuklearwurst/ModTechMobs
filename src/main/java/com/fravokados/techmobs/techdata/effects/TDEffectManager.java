package com.fravokados.techmobs.techdata.effects;

import java.util.List;

import net.minecraft.entity.monster.IMob;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.world.WorldHelper;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffect;


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
			while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS) {
				//randomize effect order
				level -= effects.get(evt.world.rand.nextInt(effects.size())).applyEffect(level, evt.entityLiving);
				i++;
				//update effectlist
				effects = TDEffects.getUsableMobEffects(level, evt.entityLiving);
			}
		}
	}
	
	
	

}
