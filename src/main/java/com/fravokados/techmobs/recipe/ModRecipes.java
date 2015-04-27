package com.fravokados.techmobs.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.fravokados.techmobs.common.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	
	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.monsterDetector), "IGI",
				" G ",
				"RIR",
				Character.valueOf('I'), new ItemStack(Items.iron_ingot),
				Character.valueOf('G'), new ItemStack(Items.gold_ingot),
				Character.valueOf('R'), new ItemStack(Items.rotten_flesh));
	}

}
