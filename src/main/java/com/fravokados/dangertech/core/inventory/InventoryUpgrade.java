package com.fravokados.dangertech.core.inventory;

import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import com.fravokados.dangertech.core.lib.Strings;
import com.fravokados.dangertech.core.upgrade.UpgradeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class InventoryUpgrade implements IUpgradeInventory {

	private NonNullList<ItemStack> inventory;

	public InventoryUpgrade(int size) {
		inventory = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < inventory.size()) {
			return inventory.get(slot);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return ItemStackHelper.getAndSplit(this.inventory, slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		return ItemStackHelper.getAndRemove(this.inventory, slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.inventory.set(slot, itemStack);

		if (itemStack.getCount() > this.getInventoryStackLimit()) {
			itemStack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public String getName() {
		return Strings.Gui.INVENTORY_UPGRADES;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
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
		inventory.replaceAll((x) -> ItemStack.EMPTY);
	}

	@Override
	public List<IUpgradeDefinition> getUpgrades() {
		return UpgradeHelper.getUpgradesFromItems(inventory);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtWrapper = new NBTTagCompound();

		ItemStackHelper.saveAllItems(nbtWrapper, inventory);

		nbt.setTag("ItemsUpgrade", nbtWrapper);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		ItemStackHelper.loadAllItems(nbt.getCompoundTag("ItemsUpgrade"), inventory);
	}

	@Override
	public boolean isEmpty() {
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}
}
