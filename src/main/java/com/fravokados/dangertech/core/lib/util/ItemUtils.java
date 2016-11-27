package com.fravokados.dangertech.core.lib.util;

import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Nuklearwurst
 */
public class ItemUtils {

	/**
	 * Creates a {@link NonNullList} containing the given items.
	 * <br/>
	 * These must not be {@code null}
	 * @param stacks Items to be inserted
	 * @return new {@link NonNullList}
	 */
	public static NonNullList<ItemStack> createNonNullList(ItemStack ... stacks)
	{
		NonNullList<ItemStack> list = NonNullList.create();
		Collections.addAll(list, stacks);
		return list;
	}

	/**
	 * Checks whether the given inventory is empty
	 * @param inventory itemstack list
	 * @return true if the given inventory contains no items
	 */
	public static boolean isEmpty(NonNullList<ItemStack> inventory) {
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}

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
	 *
	 * @deprecated use {@link net.minecraft.inventory.ItemStackHelper#loadAllItems(NBTTagCompound, NonNullList)} instead
	 */
	@Deprecated
	public static void readInventoryContentsFromNBT(IInventory inventory, NBTTagList nbtTagList) {
		for (int i = 0; i < nbtTagList.tagCount(); ++i) {
			NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.getSizeInventory()) {
				inventory.setInventorySlotContents(slot, new ItemStack(tag));
			}
		}
	}

	/**
	 * saves the content of an inventory to the given NBTTagList
	 * @param inventory the inventory to be saved
	 * @param nbtTagList the taglist the itemstacks get saved to
	 *
	 * @deprecated use {@link net.minecraft.inventory.ItemStackHelper#saveAllItems(NBTTagCompound, NonNullList)} instead
	 */
	@Deprecated
	public static void writeInventoryContentsToNBT(IInventory inventory, NBTTagList nbtTagList) {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (!inventory.getStackInSlot(i).isEmpty()) {
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
	 *
	 * @deprecated use {@link net.minecraft.inventory.ItemStackHelper#getAndSplit(List, int, int)} instead
	 */
	@Deprecated
	public static ItemStack decrStackSize(IInventory inventory, int slot, int amount) {
		ItemStack stackInSlot = inventory.getStackInSlot(slot);
		if (!stackInSlot.isEmpty()) {
			ItemStack itemstack;
			if (stackInSlot.getCount() <= amount) {
				itemstack = stackInSlot;
				inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
				return itemstack;
			} else {
				itemstack = stackInSlot.splitStack(amount);
				if (stackInSlot.getCount() == 0) {
					inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
				}
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	public static ItemStack[] createInputs(int width, int height, Object... recipeComponents) {
		String inputList = "";
		int index = 0;

		if (recipeComponents[index] instanceof String[]) {
			String[] recipeLayout = (String[]) recipeComponents[index];
			index++;

			assert recipeLayout.length == height;

			for (String line : recipeLayout) {
				assert width == line.length();
				inputList = inputList + line;
			}
		} else {
			for(int i = 0; i < 3; i++) {
				String line = (String) recipeComponents[i];
				inputList = inputList + line;
			}
			index = 3;
		}

		Map<Character, ItemStack> map = Maps.newHashMap();

		while (index < recipeComponents.length) {
			Character character = (Character) recipeComponents[index];
			ItemStack itemstack = null;

			if (recipeComponents[index + 1] instanceof Item) {
				itemstack = new ItemStack((Item) recipeComponents[index + 1]);
			} else if (recipeComponents[index + 1] instanceof Block) {
				itemstack = new ItemStack((Block) recipeComponents[index + 1], 1, 32767);
			} else if (recipeComponents[index + 1] instanceof ItemStack) {
				itemstack = (ItemStack) recipeComponents[index + 1];
			}

			map.put(character, itemstack);
			index += 2;
		}

		ItemStack[] outputStacks = new ItemStack[width * height];

		for (int i = 0; i < width * height; ++i) {
			char character = inputList.charAt(i);

			if (map.containsKey(character)) {
				outputStacks[i] = map.get(character).copy();
			} else {
				outputStacks[i] = null;
			}
		}

		return outputStacks;
	}
}
