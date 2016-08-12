package com.fravokados.dangertech.core.plugin.jei;

import com.fravokados.dangertech.core.plugin.jei.recipes.CraftingRecipeHandler;
import com.fravokados.dangertech.core.recipes.ShapedRecipeEnergyType;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

import javax.annotation.Nonnull;

/**
 *
 */
@JEIPlugin
public class DangerTechJEIPlugin extends BlankModPlugin {

	@Override
	public void register(@Nonnull IModRegistry registry) {
		registry.addRecipeHandlers(new CraftingRecipeHandler<>(ShapedRecipeEnergyType.class));
	}
}
