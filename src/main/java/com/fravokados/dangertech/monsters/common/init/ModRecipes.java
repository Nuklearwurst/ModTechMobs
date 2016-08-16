package com.fravokados.dangertech.monsters.common.init;

import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.monsters.plugin.ic2.IC2Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	
	
	public static void init() {
		//init recipes that are always the same
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.item_cot),
				"WWW",
				"CCC",
				'W', new ItemStack(Blocks.WOOL),
				'C', new ItemStack(Blocks.CARPET)));

		if(PluginManager.ic2Activated()) {
			IC2Recipes.init();
		} else {
			initVanillaFallback();
		}
	}

	/**
	 * registers fallback recipes (vanilla)
	 */
	private static void initVanillaFallback() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.monsterDetector),
				"IGI",
				" G ",
				"RIR",
				'I', "ingotIron",
				'G', "ingotGold",
				'R', new ItemStack(Items.ROTTEN_FLESH)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.quantumSword),
				"SIS",
				" D ",
				" T ",
				'S', new ItemStack(Items.SHEARS),
				'I', "ingotIron",
				'D', "gemDiamond",
				'T', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tdAnalyzer),
				"GEG",
				"GDG",
				" S ",
				'S', new ItemStack(Items.SIGN),
				'E', Items.ENDER_PEARL,
				'D', "gemDiamond",
				'G', "dustGlowstone"));


	}

}
