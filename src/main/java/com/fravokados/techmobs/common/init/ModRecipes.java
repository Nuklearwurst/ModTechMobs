package com.fravokados.techmobs.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipes {
	
	
	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.monsterDetector),
				"IGI",
				" G ",
				"RIR",
				Character.valueOf('I'), new ItemStack(Items.iron_ingot),
				Character.valueOf('G'), new ItemStack(Items.gold_ingot),
				Character.valueOf('R'), new ItemStack(Items.rotten_flesh));

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.item_cot),
				"WWW",
				"CCC",
				Character.valueOf('W'), new ItemStack(Blocks.wool),
				Character.valueOf('C'), new ItemStack(Blocks.carpet));


		GameRegistry.addShapedRecipe(new ItemStack(ModItems.upgradeTool),
				"WGW",
				"WGW",
				"SIS",
				Character.valueOf('I'), new ItemStack(Items.iron_ingot),
				Character.valueOf('G'), new ItemStack(Items.gold_ingot),
				Character.valueOf('W'), new ItemStack(Blocks.planks),
				Character.valueOf('S'), new ItemStack(Blocks.stone));
	}

}
