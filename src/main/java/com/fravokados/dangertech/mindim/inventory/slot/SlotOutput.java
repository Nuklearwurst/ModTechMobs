package com.fravokados.dangertech.mindim.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class SlotOutput extends Slot {

	public SlotOutput(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack p_75214_1_) {
		return false;
	}
}
