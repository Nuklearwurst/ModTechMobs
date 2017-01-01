package com.fravokados.dangertech.portals.inventory;

import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.item.ItemBlockPortalFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class InventoryDestinationCardMinDim implements IInventory {

	private ItemStack item;
	private final String name;

	public InventoryDestinationCardMinDim(@Nullable ItemStack item, String name) {
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
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = item;
		item = null;
		return stack;
	}

	/*
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
	*/

	@Override
	public void setInventorySlotContents(int slot, @Nullable ItemStack stack) {
		item = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return item.getItem() instanceof ItemBlockPortalFrame && item.getItemDamage() == PortalFrameType.BASIC_FRAME.ordinal();
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		item = null;
	}
}
