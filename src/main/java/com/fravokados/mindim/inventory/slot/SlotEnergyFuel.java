package com.fravokados.mindim.inventory.slot;

import com.fravokados.mindim.plugin.EnergyManager;
import com.fravokados.mindim.plugin.EnergyTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class SlotEnergyFuel extends Slot {

	private final EnergyTypes type;

	public SlotEnergyFuel(IInventory inv, int id, int x, int y, EnergyTypes type) {
		super(inv, id, x, y);
		this.type = type;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return EnergyManager.canItemProvideEnergy(stack, type);
	}
}
