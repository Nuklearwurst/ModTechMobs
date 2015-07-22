package com.fravokados.dangertech.techmobs.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {
	
	
	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.monsterDetector),
				"IGI",
				" G ",
				"RIR",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('R'), new ItemStack(Items.rotten_flesh)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.item_cot),
				"WWW",
				"CCC",
				Character.valueOf('W'), new ItemStack(Blocks.wool),
				Character.valueOf('C'), new ItemStack(Blocks.carpet)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.quantumSword),
				"SIS",
				" D ",
				" T ",
				Character.valueOf('S'), new ItemStack(Items.shears),
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('D'), "gemDiamond",
				Character.valueOf('T'), "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tdAnalyzer),
				"GEG",
				"GDG",
				" S ",
				Character.valueOf('S'), new ItemStack(Items.sign),
				Character.valueOf('E'), Items.ender_pearl,
				Character.valueOf('D'), "gemDiamond",
				Character.valueOf('G'), "dustGlowstone"));


	}

}
