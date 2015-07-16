package com.fravokados.mindim.inventory.slot;

import com.fravokados.mindim.block.BlockPortalFrame;
import com.fravokados.mindim.item.ItemBlockPortalFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class SlotDestinationCardMinDim extends Slot {

	public SlotDestinationCardMinDim(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemBlockPortalFrame && stack.getItemDamage() == BlockPortalFrame.META_FRAME_ENTITY;
	}
}
