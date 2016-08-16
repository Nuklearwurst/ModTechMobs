package com.fravokados.dangertech.core.plugin.ic2;

import com.fravokados.dangertech.core.common.init.ModItems;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author Nuklearwurst
 */
@SuppressWarnings("ConstantConditions")
public class IC2Recipes {

	/**
	 * inits recipes using ic2 items
	 */
	public static void init() {
		//Tools
		final ItemStack wrench = IC2Items.getItem("wrench");

		//Crafting
		final ItemStack circuit = IC2Items.getItem("crafting", "circuit");

		//Cable
		final ItemStack cableCopper = IC2Items.getItem("cable", "type:copper,insulation:1");

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgradeTool),
				" W ",
				"CEC",
				"SIS",
				'W', wrench,
				'C', cableCopper,
				'E', circuit,
				'S', "stone",
				'I', "ingotIron"));
	}
}
