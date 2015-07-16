package com.fravokados.mindim.inventory;

import com.fravokados.mindim.block.BlockPortalFrame;
import com.fravokados.mindim.item.ItemBlockPortalFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class InventoryDestinationCardMinDim implements IInventory {

	private ItemStack item;
	private final String name;

	public InventoryDestinationCardMinDim(ItemStack item, String name) {
		this.item = item;
		this.name = name;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return item;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.item != null) {
			ItemStack itemstack;

			if (item.stackSize <= amount) {
				itemstack = item;
				item = null;
				return itemstack;
			} else {
				itemstack = item.splitStack(amount);
				if (item.stackSize == 0) {
					item = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(item != null) {
			ItemStack stack = item;
			item = null;
			return stack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		item = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return item.getItem() instanceof ItemBlockPortalFrame && item.getItemDamage() == BlockPortalFrame.META_FRAME_ENTITY;
	}
}
