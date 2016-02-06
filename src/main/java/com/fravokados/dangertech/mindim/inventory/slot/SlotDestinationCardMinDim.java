package com.fravokados.dangertech.mindim.inventory.slot;

import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.item.ItemBlockPortalFrame;
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
		return stack.getItem() instanceof ItemBlockPortalFrame && stack.getItemDamage() == PortalFrameType.BASIC_FRAME.ordinal();
	}
}
