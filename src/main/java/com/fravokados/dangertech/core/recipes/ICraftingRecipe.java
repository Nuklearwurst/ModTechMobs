package com.fravokados.dangertech.core.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.Collections;
import java.util.List;

/**
 * used for jei-integration
 *
 * @see com.fravokados.dangertech.core.plugin.jei.recipes.CraftingRecipeHandler
 */
public interface ICraftingRecipe extends IRecipe {

	List<ItemStack> getInputs();

	default List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipeOutput());
	}

	default int getWidth() {
		return 3;
	}

	default int getHeight() {
		return 3;
	}
}
