package com.fravokados.dangertech.core.common.init;

import com.fravokados.dangertech.core.common.init.ModItems;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2Recipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	
	
	public static void init() {
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgradeTool),
				"WGW",
				"WGW",
				"SIS",
				'I', "ingotIron",
				'G', "ingotGold",
				'W', "plankWood",
				'S', "stone"));
	}

}
