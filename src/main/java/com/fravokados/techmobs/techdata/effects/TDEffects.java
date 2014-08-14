package com.fravokados.techmobs.techdata.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffect;
import com.fravokados.techmobs.techdata.effects.mob.TDMobEffectEquipment;
import com.fravokados.techmobs.techdata.effects.player.TDPlayerEffect;
import com.fravokados.techmobs.techdata.effects.player.TDPlayerEffectPotion;
import com.fravokados.techmobs.techdata.effects.world.TDWorldEffect;

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
		if(mobEffects.contains(effect)) {
			LogHelper.error("MobEffect " + effect + "already registered!");
			return;
		}
		mobEffects.add(effect);
		LogHelper.info("Registered MobEffect: " + effect);
	}
	
	/**
	 * registers a new player effect
	 * @param effect
	 */
	public static void addPlayerEffect(TDPlayerEffect effect) {
		if(playerEffects.contains(effect)) {
			LogHelper.error("PlayerEffect " + effect + "already registered!");
			return;
		}
		playerEffects.add(effect);
		LogHelper.info("Registered PlayerEffect: " + effect);
	}

	/**
	 * registers a new world effect
	 * @param effect
	 */
	public static void addWorldEffect(TDWorldEffect effect) {
		if(worldEffects.contains(effect)) {
			LogHelper.error("WorldEffect " + effect + "already registered!");
			return;
		}
		worldEffects.add(effect);
		LogHelper.info("Registered WorldEffect: " + effect);
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
	 * @param techLevel
	 * @param entityLiving
	 * @return
	 */
	public static List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, String username, EntityPlayer entity) {
		List<TDPlayerEffect> out = new ArrayList<TDPlayerEffect>();
		for(TDPlayerEffect eff : playerEffects) {
			if(eff.isUsable(techvalue, username, entity)) {
				out.add(eff);
			}
		}
		return out;
	}
	
	/**
	 * used to get a List containing all WorldEffects that are applicable for the given Chunk TODO:params
	 * @param level 
	 * @return
	 */
	public static List<TDWorldEffect> getUsableWorldEffects(int level) {
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
		//mob effects
		addMobEffect(new TDMobEffectEquipment(new ItemStack[] {
				new ItemStack(Items.diamond_sword),
				new ItemStack(Items.diamond_helmet),
				new ItemStack(Items.diamond_chestplate),
				new ItemStack(Items.diamond_leggings),
				new ItemStack(Items.diamond_boots)
		}, false, false, new int[] {30, 10, 30, 20, 10}).setDoNotDrop(false));
		//player effects
		addPlayerEffect(new TDPlayerEffectPotion(100, 18, 200, 1, "chat.effect.potion").setMessageColor(EnumChatFormatting.DARK_AQUA));
		//world effects
	}
}
