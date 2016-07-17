package com.fravokados.dangertech.core.common.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	
	
	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgradeTool),
				"WGW",
				"WGW",
				"SIS",
				Character.valueOf('I'), "ingotIron",
				Character.valueOf('G'), "ingotGold",
				Character.valueOf('W'), "plankWood",
				Character.valueOf('S'), "stone"));
	}

}
