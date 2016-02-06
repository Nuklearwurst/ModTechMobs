package com.fravokados.dangertech.techmobs.plugin.ic2;

import com.fravokados.dangertech.techmobs.plugin.PluginManager;

/**
 * @author Nuklearwurst
 */
public class IC2RecipeIntegration {

	@SuppressWarnings("UnnecessaryBoxing")
	public static void init() {
		if(PluginManager.ic2Activated()) {
			/*
			ItemStack motor = IC2Items.getItem("elemotor");
			ItemStack cesu = IC2Items.getItem("cesuUnit");

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.conservationUnit),
					"MEM",
					"GDG",
					"MCM",
					Character.valueOf('M'), motor,
					Character.valueOf('C'), cesu,
					Character.valueOf('E'), Items.ender_eye,
					Character.valueOf('D'), "blockDiamond",
					Character.valueOf('G'), "glowstone"));
			*/
		}
	}
}
