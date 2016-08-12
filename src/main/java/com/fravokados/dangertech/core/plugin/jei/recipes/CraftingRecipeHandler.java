package com.fravokados.dangertech.core.plugin.jei.recipes;

import com.fravokados.dangertech.core.recipes.ICraftingRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import javax.annotation.Nonnull;

/**
 *
 */
public class CraftingRecipeHandler<T extends ICraftingRecipe>  implements IRecipeHandler<T> {

	private final Class<T> recipeClass;

	public CraftingRecipeHandler(Class<T> recipeClass) {
		this.recipeClass = recipeClass;
	}

	@Nonnull
	@Override
	public Class<T> getRecipeClass() {
		return recipeClass;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull T recipe) {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull T recipe) {
		return new CraftingRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(@Nonnull T recipe) {
		return true;
	}

}
