package com.fravokados.dangertech.monsters.inventory;

import com.fravokados.dangertech.monsters.entity.EntityConservationUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class ContainerConservationUnit extends Container {

	private EntityConservationUnit conservationUnit;
	private int oldValue = 0;

	public ContainerConservationUnit(InventoryPlayer player, EntityConservationUnit conservationUnit) {
		this.conservationUnit = conservationUnit;

		//Unit inventory
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(conservationUnit, j + i * 9 + 9, 8 + j * 18, 18 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(conservationUnit, i, 8 + i * 18, 76));
		}

		//Player inventory
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 118 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 176));
		}
	}


	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack stackCopy = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackSlot = slot.getStack();
			//noinspection ConstantConditions
			stackCopy = stackSlot.copy();

			if(slotId < 27) { //machine inventory slots (main-inventory)
				if (!this.mergeItemStack(stackSlot, 36, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotId < 36) { //machine inventory slots (hotbar)
				if(!this.mergeItemStack(stackSlot, 63, this.inventorySlots.size(), false)) { //prioritize hotbar
					if (!this.mergeItemStack(stackSlot, 36, 63, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if(slotId < 63) { //from player (main)
				if(!this.mergeItemStack(stackSlot, 0, 36, false)) {
					return ItemStack.EMPTY;
				}
			} else { //from player (hotbar)
				if(!this.mergeItemStack(stackSlot, 27, 36, false)) { //prioritize hotbar
					if (!this.mergeItemStack(stackSlot, 0, 27, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (stackSlot.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (stackSlot.getCount() == stackCopy.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stackSlot);
		}

		return stackCopy;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		conservationUnit.closeInventory(playerIn);
	}
}
