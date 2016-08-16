package com.fravokados.dangertech.portals.common.init;

import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.portals.plugin.ic2.IC2Recipes;

/**
 * Recipes
 *
 * @author Nuklearwurst
 */
@SuppressWarnings("ConstantConditions")
public class ModRecipes {

	public static void init() {
		if (PluginManager.ic2Activated()) {
			IC2Recipes.init();
		} else {
			initVanillaFallback();
		}
	}

	/**
	 * registers fallback recipes (vanilla)
	 */
	private static void initVanillaFallback() {

	}

}
