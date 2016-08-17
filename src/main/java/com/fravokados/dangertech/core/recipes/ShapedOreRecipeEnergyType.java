package com.fravokados.dangertech.core.recipes;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * ShapedOreRecipe that keeps EnergyType of input to output
 *
 * note: output should be something that can have an EnergyType
 */
public class ShapedOreRecipeEnergyType extends ShapedOreRecipe {

	public static final String NAME = "shapedore_energytype";

	public ShapedOreRecipeEnergyType(Block result, Object... recipe) {
		super(result, recipe);
	}

	public ShapedOreRecipeEnergyType(Item result, Object... recipe) {
		super(result, recipe);
	}

	public ShapedOreRecipeEnergyType(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return super.matches(inv, world) && EnergyManager.getEnergyTypeOfInventory(inv) != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stackOut = super.getCraftingResult(inv);
		if(stackOut != null) {
			EnergyType type = EnergyManager.getFirstEnergyTypeOfInventory(inv);
			if(type != null) {
				type.writeToNBT(ItemUtils.getNBTTagCompound(stackOut));
			}
		}
		return stackOut;
	}
}
