package com.fravokados.dangertech.portals.inventory.slot;

import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.item.ItemBlockPortalFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class SlotDestinationCardMinDim extends Slot {

	public SlotDestinationCardMinDim(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemBlockPortalFrame && stack.getItemDamage() == PortalFrameType.BASIC_FRAME.ordinal();
	}
}
