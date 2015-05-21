package com.fravokados.techmobs.inventory;

import com.fravokados.techmobs.upgrade.IUpgradeDefinition;
import com.fravokados.techmobs.upgrade.IUpgradeInventory;
import com.fravokados.techmobs.upgrade.UpgradeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class InventoryUpgrade implements IUpgradeInventory {

	ItemStack[] inventory;

	public InventoryUpgrade(int size) {
		inventory = new ItemStack[size];
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < inventory.length) {
			return inventory[slot];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= amount) {
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else {
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.inventory[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "Upgrades"; //TODO: localize
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return UpgradeHelper.isUpgrade(stack);
	}

	@Override
	public List<IUpgradeDefinition> getUpgrades() {
		return UpgradeHelper.getUpgradesFromItems(inventory);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(tag);
				nbttaglist.appendTag(tag);
			}
		}
		nbt.setTag("ItemsUpgrade", nbttaglist);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = nbt.getTagList("ItemsUpgrade", 10); //10 is compound type
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < this.inventory.length) {
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
}
