package com.fravokados.dangertech.portals.inventory.slot;

import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.lib.Textures;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		return stack == null || stack.getItem() instanceof ItemDestinationCard;
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	@Override
	public String getSlotTexture() {
		return Textures.GUI_SLOT_DESTINATION_CARD.toString();
	}
}
