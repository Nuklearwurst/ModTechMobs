package com.fravokados.dangertech.core.plugin.vanilla;

import com.fravokados.dangertech.core.plugin.energy.EnergyStorage;
import com.fravokados.dangertech.core.plugin.energy.IEnergyPlugin;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 *
 */
public class VanillaEnergyPlugin implements IEnergyPlugin {

	@Override
	public boolean canItemProvideEnergy(ItemStack item, int sinkTier) {
		return TileEntityFurnace.getItemBurnTime(item) > 0;
	}

	@Override
	public void rechargeEnergyStorageFromInventory(ItemStack stack, EnergyStorage storage, IInventory inventory, int slot, int sinkTier) {
		int fuelValue = TileEntityFurnace.getItemBurnTime(stack);
		if(storage.hasRoomForEnergy(fuelValue)) {
			storage.receiveEnergy(fuelValue, false);
			inventory.decrStackSize(slot, 1);
		}
	}
}
