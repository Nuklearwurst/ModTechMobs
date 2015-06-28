package com.fravokados.techmobs.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipes {
	
	
	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.monsterDetector), "IGI",
				" G ",
				"RIR",
				Character.valueOf('I'), new ItemStack(Items.iron_ingot),
				Character.valueOf('G'), new ItemStack(Items.gold_ingot),
				Character.valueOf('R'), new ItemStack(Items.rotten_flesh));
	}

}
