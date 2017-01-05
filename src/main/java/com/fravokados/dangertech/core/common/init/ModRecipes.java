package com.fravokados.dangertech.core.common.init;

import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2Recipes;
import com.fravokados.dangertech.core.recipes.ShapedOreRecipeEnergyType;
import com.fravokados.dangertech.core.recipes.ShapedRecipeEnergyType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	
	
	public static void init() {
		//Register Recipe classes
		registerRecipeType(ShapedRecipeEnergyType.NAME, ShapedRecipeEnergyType.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
		registerRecipeType(ShapedOreRecipeEnergyType.NAME, ShapedOreRecipeEnergyType.class, RecipeSorter.Category.SHAPED, "after:forge:shapedore");

		if(PluginManager.isIc2Available()) {
			IC2Recipes.init();
		} else {
			initVanillaFallback();
		}
	}

	/**
	 * registers fallback recipes (vanilla)
	 */
	private static void initVanillaFallback() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgradeTool),
				"WGW",
				"WGW",
				"SIS",
				'I', "ingotIron",
				'G', "ingotGold",
				'W', "plankWood",
				'S', "stone"));
	}

	public static void registerRecipeType(String name, Class<?> clazz, RecipeSorter.Category category, String dependencies) {
		RecipeSorter.register(Reference.MOD_ID + ":" + name, clazz, category, dependencies);
	}

}
