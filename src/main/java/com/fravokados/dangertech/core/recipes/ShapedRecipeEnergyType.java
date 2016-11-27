package com.fravokados.dangertech.core.recipes;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

/**
 *
 */
public class ShapedRecipeEnergyType extends ShapedRecipes {

	public static final String NAME = "shaped_energytype";

	public ShapedRecipeEnergyType(ItemStack output, Object... inputs) {
		this(3, 3, output, inputs);
	}

	public ShapedRecipeEnergyType(int width, int height, ItemStack output, Object... inputs) {
		this(width, height, output, ItemUtils.createInputs(width, height, inputs));
	}

	private ShapedRecipeEnergyType(int width, int height, ItemStack output, ItemStack[] inputs) {
		super(width, height, inputs, output);
	}


	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return super.matches(inv, world) && EnergyManager.getEnergyTypeOfInventory(inv) != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stackOut = super.getCraftingResult(inv);
		if(!stackOut.isEmpty()) {
			EnergyType type = EnergyManager.getFirstEnergyTypeOfInventory(inv);
			if(type != null) {
				type.writeToNBT(ItemUtils.getNBTTagCompound(stackOut));
			}
		}
		return stackOut;
	}
}
