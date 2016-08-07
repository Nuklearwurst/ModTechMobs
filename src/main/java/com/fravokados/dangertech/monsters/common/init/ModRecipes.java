package com.fravokados.dangertech.monsters.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	
	
	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.monsterDetector),
				"IGI",
				" G ",
				"RIR",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('R'), new ItemStack(Items.ROTTEN_FLESH)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.item_cot),
				"WWW",
				"CCC",
				Character.valueOf('W'), new ItemStack(Blocks.WOOL),
				Character.valueOf('C'), new ItemStack(Blocks.CARPET)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.quantumSword),
				"SIS",
				" D ",
				" T ",
				Character.valueOf('S'), new ItemStack(Items.SHEARS),
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('D'), "gemDiamond",
				Character.valueOf('T'), "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tdAnalyzer),
				"GEG",
				"GDG",
				" S ",
				Character.valueOf('S'), new ItemStack(Items.SIGN),
				Character.valueOf('E'), Items.ENDER_PEARL,
				Character.valueOf('D'), "gemDiamond",
				Character.valueOf('G'), "dustGlowstone"));


	}

}
