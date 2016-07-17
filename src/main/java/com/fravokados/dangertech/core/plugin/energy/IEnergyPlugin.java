package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 *
 */
public interface IEnergyPlugin {

	/**
	 * checks whether this item can provide energy to the specified energy type
	 * @param item the itemstack to test
	 * @param type the energy type
	 * @return true if the given item can provide energy to the specified energy type
	 */
	boolean canItemProvideEnergy(ItemStack item, EnergyType type);

	void rechargeEnergyStorageFromInventory(ItemStack stack, EnergyStorage storage, EnergyType type, IInventory inventory, int slot, int sinkTier);

}
