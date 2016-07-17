package com.fravokados.dangertech.mindim.inventory.slot;

import com.fravokados.dangertech.mindim.item.ItemDestinationCard;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class SlotControllerDestinationCard extends Slot {

	public SlotControllerDestinationCard(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return stack.getItem() instanceof ItemDestinationCard;
	}
}
