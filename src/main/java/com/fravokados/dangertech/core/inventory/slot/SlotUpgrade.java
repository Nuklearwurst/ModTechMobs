package com.fravokados.dangertech.core.inventory.slot;

import com.fravokados.dangertech.api.core.upgrade.IUpgradable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

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
	public boolean isItemValid(@Nullable ItemStack stack) {
		return stack != null && this.inventory.isItemValidForSlot(this.slotNumber, stack);
	}
}
