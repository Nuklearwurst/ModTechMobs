package com.fravokados.techmobs.techdata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.techdata.effects.TDMobEffect;
import com.fravokados.techmobs.techdata.effects.TDMobEffectArmor;

/**
 * contains  information of the techdata effects
 * @author Nuklearwurst
 *
 */
public class TDEffects {

	private static List<TDMobEffect> mobEffects = new ArrayList<TDMobEffect>();


	public static void addMobEffect(TDMobEffect effect) {
		if(mobEffects.contains(effect))
			return;
		mobEffects.add(effect);
		LogHelper.info("Registered MobEffect: " + effect);
	}

	public static List<TDMobEffect> getUsableMobEffects(int techData, EntityLivingBase entityLiving) {
		List<TDMobEffect> out = new ArrayList<TDMobEffect>();
		for(TDMobEffect eff : mobEffects) {
			if(eff.isUsable(techData, entityLiving)) {
				out.add(eff);
			}
		}
		return out;
	}

	public static void init() {
		addMobEffect(new TDMobEffectArmor(new ItemStack[] {
				new ItemStack((Item)Item.itemRegistry.getObject("diamond_helmet")),
				new ItemStack((Item)Item.itemRegistry.getObject("diamond_chestplate")),
				new ItemStack((Item)Item.itemRegistry.getObject("diamond_leggings")),
				new ItemStack((Item)Item.itemRegistry.getObject("diamond_boots"))
		}, false, false, new int[] {10, 30, 20, 10}));
	}
}
