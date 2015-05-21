package com.fravokados.techmobs.inventory.slot;

import com.fravokados.techmobs.upgrade.IUpgradable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class SlotUpgrade extends Slot {

	public SlotUpgrade(IUpgradable inv, int id, int x, int y) {
		super(inv.getUpgradeInventory(), id, x, y);
	}

	public SlotUpgrade(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return this.inventory.isItemValidForSlot(this.slotNumber, stack);
	}
}
