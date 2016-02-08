package com.fravokados.dangertech.core.plugin.energy;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.List;

/**
 * Functions to help manage energy apis
 * @author Nuklearwurst
 */
public class EnergyManager {

	/**
	 * checks whether this item can provide energy to the specified energy type
	 * @param item the itemstack to test
	 * @param type the energy type
	 * @return true if the given item can provide energy to the specified energy type
	 */
	public static boolean canItemProvideEnergy(ItemStack item, EnergyTypes type) {
		switch (type) {
			case IC2:
//				return item.getItem() instanceof IElectricItem && ((IElectricItem) item.getItem()).canProvideEnergy(item);
				return false;
			case VANILLA:
				return TileEntityFurnace.getItemBurnTime(item) > 0;
		}
		return false;
	}

	public static void createItemVariantsForEnergyTypes(List<ItemStack> list, Item item, int meta, EnergyTypes ... types) {
		for (EnergyTypes type : types) {
			list.add(createItemStackWithEnergyType(item, 1, meta, type));
		}
	}

	public static ItemStack createItemStackWithEnergyType(Block block, int amount, int damage, EnergyTypes type) {
		return createItemStackWithEnergyType(Item.getItemFromBlock(block), amount, damage, type);
	}

	public static ItemStack createItemStackWithEnergyType(Item item, int amount, int damage, EnergyTypes type) {
		ItemStack stack = new ItemStack(item, amount, damage);
		addEnergyTypeInformationToItem(stack, type);
		return stack;
	}

	public static ItemStack addEnergyTypeInformationToItem(ItemStack item, EnergyTypes type) {
		type.writeToNBT(ItemUtils.getNBTTagCompound(item));
		return item;
	}

	public static boolean writeEnergyTypeToEnergyTypeAware(IEnergyTypeAware tile, ItemStack stack) {
		EnergyTypes types = EnergyTypes.readFromNBT(stack.getTagCompound());
		if(types != EnergyTypes.INVALID) {
			tile.setEnergyType(types);
		}
		return false;
	}
}
