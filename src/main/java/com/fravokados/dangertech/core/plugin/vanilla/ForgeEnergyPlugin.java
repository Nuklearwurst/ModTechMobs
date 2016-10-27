package com.fravokados.dangertech.core.plugin.vanilla;

import com.fravokados.dangertech.core.plugin.energy.EnergyStorage;
import com.fravokados.dangertech.core.plugin.energy.IEnergyPlugin;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author Nuklearwurst
 */
public class ForgeEnergyPlugin implements IEnergyPlugin {

	@Override
	public boolean canItemProvideEnergy(ItemStack item, int sinkTier) {
		if(item.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN)) {
			IEnergyStorage capability = item.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);
			return capability.canExtract();
		}
		return false;
	}

	@Override
	public void rechargeEnergyStorageFromInventory(ItemStack item, EnergyStorage storage, IInventory inventory, int slot, int sinkTier) {
		if(item.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN)) {
			IEnergyStorage capability = item.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);
			if(capability.canExtract()) {
				storage.receiveEnergy(capability.extractEnergy(storage.getRoomForEnergy(), false), false);
			}
		}
	}
}
