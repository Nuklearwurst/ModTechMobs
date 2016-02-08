package com.fravokados.dangertech.mindim.inventory.slot;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.IEnergyTypeAware;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class SlotEnergyFuel extends Slot {

	private final IEnergyTypeAware type;

	public SlotEnergyFuel(IInventory inv, int id, int x, int y, IEnergyTypeAware type) {
		super(inv, id, x, y);
		this.type = type;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return EnergyManager.canItemProvideEnergy(stack, type.getEnergyType());
	}
}
