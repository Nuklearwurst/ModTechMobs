package com.fravokados.dangertech.techmobs.techdata.effects;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.api.techdata.effects.TDEffectRegistry;
import com.fravokados.dangertech.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.dangertech.api.techdata.effects.mob.TDMobEffect;
import com.fravokados.dangertech.api.techdata.effects.mob.TDMobEffectEquipment;
import com.fravokados.dangertech.api.techdata.effects.mob.TDMobEffectPotion;
import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffectPotion;
import com.fravokados.dangertech.core.lib.util.ChatUtils;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.techdata.effects.chunk.TDChunkEffectRain;
import com.fravokados.dangertech.techmobs.techdata.effects.chunk.TDChunkEffectThunder;
import com.fravokados.dangertech.techmobs.techdata.effects.mob.TDMobEffectChargedCreeper;
import com.fravokados.dangertech.techmobs.techdata.effects.player.TDPlayerEffectFakeExplosion;
import com.fravokados.dangertech.techmobs.techdata.effects.player.TDPlayerEffectLightning;
import com.fravokados.dangertech.techmobs.techdata.effects.player.TDPlayerEffectWeather;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * contains  information of the techdata effects
 * @author Nuklearwurst
 *
 */
public class TDEffects implements TDEffectRegistry {

	/**
	 * effects that are applied to mods on spawn
	 */
	private final List<TDMobEffect> mobEffects = new ArrayList<TDMobEffect>();
	
	/**
	 * effects that are applied randomly to players with high techvalue
	 */
	private final List<TDPlayerEffect> playerEffects = new ArrayList<TDPlayerEffect>();
	
	/**
	 * effects that are applied randomly to chunks with high techvalues
	 */
	private final List<TDChunkEffect> worldEffects = new ArrayList<TDChunkEffect>();


	/**
	 * registers a new Mob effect
	 */
	@Override
	public void addMobEffect(TDMobEffect effect) {
		if(mobEffects.contains(effect)) {
			LogHelperTM.error("MobEffect " + effect + "already registered!");
			return;
		}
		mobEffects.add(effect);
		LogHelperTM.info("Registered MobEffect: " + effect);
	}
	
	/**
	 * registers a new player effect
	 */
	@Override
	public void addPlayerEffect(TDPlayerEffect effect) {
		if(playerEffects.contains(effect)) {
			LogHelperTM.error("PlayerEffect " + effect + "already registered!");
			return;
		}
		playerEffects.add(effect);
		LogHelperTM.info("Registered PlayerEffect: " + effect);
	}

	/**
	 * registers a new world effect
	 */
	@Override
	public void addChunkEffect(TDChunkEffect effect) {
		if(worldEffects.contains(effect)) {
			LogHelperTM.error("WorldEffect " + effect + "already registered!");
			return;
		}
		worldEffects.add(effect);
		LogHelperTM.info("Registered WorldEffect: " + effect);
	}
	/**
	 * used to get a List containing all MobEffects that are applicable for the given Entity
	 */
	public List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving) {
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
	 */
	@Override
	public List<TDPlayerEffect> getUsablePlayerEffects(int techvalue, String username, EntityPlayer entity) {
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
	 */
	@Override
	public List<TDChunkEffect> getUsableWorldEffects(int level, World world) {
		List<TDChunkEffect> out = new ArrayList<TDChunkEffect>();
		for(TDChunkEffect eff : worldEffects) {
			if(eff.isUsable(level, world)) {
				out.add(eff);
			}
		}
		return out;
	}

	/**
	 * registers mod effects
	 */
	public static void init() {
		getInstance().addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				new ItemStack(Items.diamond_sword),
				new ItemStack(Items.diamond_helmet),
				new ItemStack(Items.diamond_chestplate),
				new ItemStack(Items.diamond_leggings),
				new ItemStack(Items.diamond_boots)
		}, false, false, new int[]{400, 100, 400, 200, 200}).setDoesArmorDrop(false));
		getInstance().addMobEffect(new TDMobEffectChargedCreeper());
		getInstance().addMobEffect(new TDMobEffectPotion(300, 5, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(100, 8, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(300, 1, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(600, 14, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(300, 11, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(300, 10, 1500, 1));
		getInstance().addMobEffect(new TDMobEffectPotion(500, 21, 1500, 3));

		getInstance().addPlayerEffect(new TDPlayerEffectFakeExplosion());
		getInstance().addPlayerEffect(new TDPlayerEffectWeather());
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(400, 9, 400, 3, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectNausea, EnumChatFormatting.DARK_PURPLE))); //Nausea IV
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(800, 15, 200, 0, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectBlindness, EnumChatFormatting.RED))); //Blindness
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(400, 18, 200, 2, new ChatComponentTranslation(Strings.Chat.effectWeakness))); //Weakness II
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(400, 17, 100, 1, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectHunger, EnumChatFormatting.GOLD))); //Hunger II
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(300, 4, 100, 1, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectMiningFatigue, EnumChatFormatting.DARK_RED))); //Mining Fatigue II
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(300, 2, 100, 1, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectSlowness, EnumChatFormatting.BLUE))); //Slowness II
		getInstance().addPlayerEffect(new TDPlayerEffectPotion(3000, 2, 100, 3, ChatUtils.getTranslatedChatComponentWithColor(Strings.Chat.effectSlowness_2, EnumChatFormatting.DARK_PURPLE))); //Slowness IV
		getInstance().addPlayerEffect(new TDPlayerEffectLightning());

		getInstance().addChunkEffect(new TDChunkEffectRain());
		getInstance().addChunkEffect(new TDChunkEffectThunder());
	}

	public static TDEffectRegistry getInstance() {
		return DangerousTechnologyAPI.effectRegistry;
	}
}
