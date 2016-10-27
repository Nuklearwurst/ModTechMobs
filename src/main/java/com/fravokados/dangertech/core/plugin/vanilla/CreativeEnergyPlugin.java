package com.fravokados.dangertech.core.plugin.vanilla;

import com.fravokados.dangertech.core.plugin.energy.EnergyStorage;
import com.fravokados.dangertech.core.plugin.energy.IEnergyPlugin;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class CreativeEnergyPlugin implements IEnergyPlugin {
	@Override
	public boolean canItemProvideEnergy(ItemStack item, int sinkTier) {
		return true;
	}

	@Override
	public void rechargeEnergyStorageFromInventory(ItemStack stack, EnergyStorage storage, IInventory inventory, int slot, int sinkTier) {
		storage.receiveEnergy(storage.getRoomForEnergy(), false);
	}
}
