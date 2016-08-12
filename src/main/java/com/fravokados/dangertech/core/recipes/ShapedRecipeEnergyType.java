package com.fravokados.dangertech.core.recipes;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ShapedRecipeEnergyType implements ICraftingRecipe {

	private ItemStack[] inputs;
	private ItemStack output;

	public ShapedRecipeEnergyType(ItemStack output, Object... inputs) {
		this(output, ItemUtils.createInputs(3, 3, inputs));
	}

	private ShapedRecipeEnergyType(ItemStack output, ItemStack[] inputs) {
		assert inputs.length == 9 : "Recipe Input array must be of length 9!";
		this.output = output;
		this.inputs = inputs;
	}


	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		EnergyType typeFound = null;
		for (int i = 0; i < 9; i++) {
			ItemStack expectedStack = inputs[i];
			ItemStack craftingStack = inv.getStackInSlot(i);
			if (expectedStack != null || craftingStack != null) {
				if (expectedStack == null || craftingStack == null) {
					return false;
				}

				if (craftingStack.getItem() != expectedStack.getItem()) {
					return false;
				}

				if (expectedStack.getMetadata() != 32767 && craftingStack.getMetadata() != expectedStack.getMetadata()) {
					return false;
				}

				//make sure energy types line up
				if (craftingStack.hasTagCompound()) {
					//noinspection ConstantConditions
					EnergyType type = EnergyType.readFromNBT(craftingStack.getTagCompound());
					if (type != EnergyType.INVALID) {
						if (typeFound == null) {
							typeFound = type;
						} else if (typeFound != type) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Nullable
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stackOut = output.copy();
		for (int i = 0; i < 9; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.hasTagCompound()) {
				//noinspection ConstantConditions
				EnergyType type = EnergyType.readFromNBT(stack.getTagCompound());
				if (type != EnergyType.INVALID) {
					type.writeToNBT(stack.getTagCompound());
					break;
				}
			}

		}
		return stackOut;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Nullable
	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] itemStacks = new ItemStack[9];

		for (int i = 0; i < 9; ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			itemStacks[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return itemStacks;
	}

	@Override
	public List<ItemStack> getInputs() {
		return Arrays.asList(inputs);
	}
}
