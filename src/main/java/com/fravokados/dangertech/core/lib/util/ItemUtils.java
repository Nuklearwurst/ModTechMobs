package com.fravokados.dangertech.core.lib.util;

import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class ItemUtils {

	/**
	 * gets the {@link NBTTagCompound} of an {@link ItemStack}, generates one if not available
	 *
	 * @param stack the ItemStack which contains the NBTTagCompound
	 * @return the NBTTagCompound of the ItemStack
	 */
	public static NBTTagCompound getNBTTagCompound(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		//noinspection ConstantConditions
		return stack.getTagCompound();
	}

	/**
	 * writes the content of an {@link IUpgradeInventory} to the nbtData of an ItemStack
	 *
	 * @param inventory the inventory that should be saved
	 * @param stack     the stack that should hold the information
	 */
	public static void writeUpgradesToItemStack(@Nullable IUpgradeInventory inventory, ItemStack stack) {
		if (inventory == null) {
			return;
		}
		NBTTagCompound nbt = getNBTTagCompound(stack);

		NBTTagList nbttaglist = new NBTTagList();
		writeInventoryContentsToNBT(inventory, nbttaglist);
		nbt.setTag("Upgrades", nbttaglist);
	}

	/**
	 * reads upgrade inventory from stack<br><br>
	 * <p/>
	 * <b>DO NOT</b> use this if you don't know whether the UpgradeInventory is big enough.<br>
	 * <u>Doing so will result in a loss of items!</u>
	 *
	 * @param inventory the inventory that is to be populated
	 * @param stack     the stack containing the infomation
	 */
	public static void readUpgradesFromItemStack(IUpgradeInventory inventory, ItemStack stack) {
		NBTTagCompound nbt = getNBTTagCompound(stack);
		if (nbt.hasKey("Upgrades")) {
			NBTTagList nbttaglist = nbt.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
			readInventoryContentsFromNBT(inventory, nbttaglist);
		}
	}

	/**
	 * reads the content of an inventory from the given NBTTagList
	 * @param inventory the inventory the items should get added to
	 * @param nbtTagList the Taglist containing the saved itemstacks
	 */
	public static void readInventoryContentsFromNBT(IInventory inventory, NBTTagList nbtTagList) {
		for (int i = 0; i < nbtTagList.tagCount(); ++i) {
			NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.getSizeInventory()) {
				inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
			}
		}
	}

	/**
	 * saves the content of an inventory to the given NBTTagList
	 * @param inventory the inventory to be saved
	 * @param nbtTagList the taglist the itemstacks get saved to
	 */
	public static void writeInventoryContentsToNBT(IInventory inventory, NBTTagList nbtTagList) {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				//noinspection ConstantConditions
				inventory.getStackInSlot(i).writeToNBT(tag);
				nbtTagList.appendTag(tag);
			}
		}
	}

	/**
	 * a default implementation for decrStackSize of IInventory
	 */
	public static ItemStack decrStackSize(IInventory inventory, int slot, int amount) {
		ItemStack stackInSlot = inventory.getStackInSlot(slot);
		if (stackInSlot != null) {
			ItemStack itemstack;
			if (stackInSlot.stackSize <= amount) {
				itemstack = stackInSlot;
				inventory.setInventorySlotContents(slot, null);
				return itemstack;
			} else {
				itemstack = stackInSlot.splitStack(amount);
				if (stackInSlot.stackSize == 0) {
					inventory.setInventorySlotContents(slot, null);
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}
}
