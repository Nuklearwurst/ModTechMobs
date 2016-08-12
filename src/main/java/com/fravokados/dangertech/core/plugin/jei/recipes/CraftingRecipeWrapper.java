package com.fravokados.dangertech.core.plugin.jei.recipes;

import com.fravokados.dangertech.core.recipes.ICraftingRecipe;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 */
public class CraftingRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

	private ICraftingRecipe recipe;


	public CraftingRecipeWrapper(ICraftingRecipe recipe) {
		this.recipe = recipe;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		return recipe.getInputs();
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return recipe.getOutputs();
	}

	@Override
	public int getWidth() {
		return recipe.getWidth();
	}

	@Override
	public int getHeight() {
		return recipe.getHeight();
	}
}
