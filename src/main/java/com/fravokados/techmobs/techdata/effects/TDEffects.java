package com.fravokados.techmobs.techdata.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffect;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffectEquipment;
import com.fravokados.techmobs.techdata.effects.player.TDPlayerEffect;
import com.fravokados.techmobs.techdata.effects.world.TDWorldEffect;
import com.fravokados.techmobs.world.techdata.TDPlayer;

/**
 * contains  information of the techdata effects
 * @author Nuklearwurst
 *
 */
public class TDEffects {

	/**
	 * effects that are applied to mods on spawn
	 */
	private static List<TDMobEffect> mobEffects = new ArrayList<TDMobEffect>();
	
	/**
	 * effects that are applied randomly to players with high techvalue
	 */
	private static List<TDPlayerEffect> playerEffects = new ArrayList<TDPlayerEffect>();
	
	/**
	 * effects that are applied randomly to chunks with high techvalues
	 */
	private static List<TDWorldEffect> worldEffects = new ArrayList<TDWorldEffect>();


	/**
	 * registers a new Mob effect
	 * @param effect
	 */
	public static void addMobEffect(TDMobEffect effect) {
		if(mobEffects.contains(effect))
			return;
		mobEffects.add(effect);
		LogHelper.info("Registered MobEffect: " + effect);
	}

	/**
	 * used to get a List containing all MobEffects that are applicable for the given Entity
	 * @param techData
	 * @param entityLiving
	 * @return
	 */
	public static List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving) {
		List<TDMobEffect> out = new ArrayList<TDMobEffect>();
		for(TDMobEffect eff : mobEffects) {
			if(eff.isUsable(techData, entityLiving)) {
				out.add(eff);
			}
		}
		return out;
	}
	
	/**
	 * used to get a List containing all PlayerEffects that are applicable for the given Player
	 * @param player 
	 * @param username 
	 * @param techData
	 * @param entityLiving
	 * @return
	 */
	public static List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, String username, TDPlayer player) {
		List<TDPlayerEffect> out = new ArrayList<TDPlayerEffect>();
		for(TDPlayerEffect eff : playerEffects) {
			if(eff.isUsable(techvalue, username, player)) {
				out.add(eff);
			}
		}
		return out;
	}
	
	/**
	 * used to get a List containing all WorldEffects that are applicable for the given Chunk
	 * @param techData
	 * @param entityLiving
	 * @return
	 */
	public static List<TDWorldEffect> getUsableWorldEffects() {
		List<TDWorldEffect> out = new ArrayList<TDWorldEffect>();
		for(TDWorldEffect eff : worldEffects) {
			if(eff.isUsable()) {
				out.add(eff);
			}
		}
		return out;
	}

	/**
	 * registers mod effects
	 */
	public static void init() {
		addMobEffect(new TDMobEffectEquipment(new ItemStack[] {
				new ItemStack(Items.diamond_sword),
				new ItemStack(Items.diamond_helmet),
				new ItemStack(Items.diamond_chestplate),
				new ItemStack(Items.diamond_leggings),
				new ItemStack(Items.diamond_boots)
		}, false, false, new int[] {30, 10, 30, 20, 10}).setDoNotDrop(false));
	}
}
