package com.fravokados.dangertech.monsters.techdata.effects;

import com.fravokados.dangertech.api.techdata.effects.mob.TDMobEffect;
import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.List;
import java.util.Random;


/**
 * applies the effects
 * @author Nuklearwurst
 *
 */
public class TDEffectHandler {
	
	public static void onLivingSpawn(LivingSpawnEvent evt) {
		if(evt.getEntity().worldObj.isRemote || !(evt.getEntityLiving() instanceof IMob)) {
			return;
		}
		
		//localize and read chunk
		ChunkPos coord = WorldUtils.convertToChunkCoord(evt.getX(), evt.getZ());
		int level = TDManager.getScoutedTechLevel(evt.getWorld().provider.getDimension(), coord);
		if(level <= 0) {
			return;
		}
		
		if(evt.getWorld().rand.nextInt(Settings.TechData.TD_EFFECT_CHANCE) < level) {
			int min = (int) (Settings.TechData.TD_EFFECT_MIN + Math.floor(level * Settings.TechData.TD_EFFECT_MIN_FACTOR));
			if(level > min) {
				level = min + evt.getWorld().rand.nextInt(level - min);
			}
			int i = 0;
			List<TDMobEffect> effects = TDEffects.getInstance().getUsableMobEffects(level, evt.getEntityLiving());
			while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_MOB) {
				//randomize effect order
				level -= effects.get(evt.getWorld().rand.nextInt(effects.size())).applyEffect(level, evt.getEntityLiving());
				i++;
				//update effectlist
				effects = TDEffects.getInstance().getUsableMobEffects(level, evt.getEntityLiving());
			}
		}
	}
	
	public static void applyRandomEffectOnPlayer(EntityPlayer entity, String username, Random rand) {
		int level = TDManager.getPlayerScoutedTechLevel(entity);
		int i = 0;
		List<TDPlayerEffect> effects = TDEffects.getInstance().getUsablePlayerEffects(level, username, entity);
		while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_PLAYER) {
			level -= effects.get(rand.nextInt(effects.size())).applyEffect(level, username, entity);
			effects = TDEffects.getInstance().getUsablePlayerEffects(level, username, entity);
			i++;
		}
		TDManager.setPlayerScoutedTechLevel(entity, level);
	}
	
	
	
	

}
