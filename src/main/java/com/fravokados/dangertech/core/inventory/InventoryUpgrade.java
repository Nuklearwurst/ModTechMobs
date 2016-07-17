package com.fravokados.dangertech.core.inventory;

import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import com.fravokados.dangertech.core.upgrade.UpgradeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
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
	public ItemStack removeStackFromSlot(int slot) {
		ItemStack itemstack = this.inventory[slot];
		this.inventory[slot] = null;
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int slot, @Nullable ItemStack itemStack) {
		this.inventory[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getName() {
		return "Upgrades"; //TODO: localize
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return UpgradeHelper.isUpgrade(stack);
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
		for(int i = 0; i < inventory.length; i++) {
			inventory[i] = null;
		}
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
		NBTTagList nbttaglist = nbt.getTagList("ItemsUpgrade", Constants.NBT.TAG_COMPOUND);
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
