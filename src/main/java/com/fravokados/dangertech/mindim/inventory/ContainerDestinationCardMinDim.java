package com.fravokados.dangertech.mindim.inventory;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.configuration.Settings;
import com.fravokados.dangertech.mindim.inventory.slot.SlotDestinationCardMinDim;
import com.fravokados.dangertech.mindim.item.ItemBlockPortalFrame;
import com.fravokados.dangertech.mindim.network.IElementButtonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public class ContainerDestinationCardMinDim extends Container implements IElementButtonHandler {


	private final InventoryPlayer playerInventory;
	private final InventoryDestinationCardMinDim inventory;
	private ItemStack heldInventory;

	private int lastFrameCount = 0;

	public ContainerDestinationCardMinDim(InventoryPlayer playerInventory, ItemStack heldInventory) {
		super();
		this.playerInventory = playerInventory;
		this.heldInventory = heldInventory;
		this.inventory = new InventoryDestinationCardMinDim(null, "Destination Card");

		this.addSlotToContainer(new SlotDestinationCardMinDim(inventory, 0, 56, 46));

		//Player inventory
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}


	public static final String NETWORK_ID_ADD = "add_frame";

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (IContainerListener crafter : this.listeners) {
			int newCount = ItemUtils.getNBTTagCompound(heldInventory).getInteger("frame_blocks");
			if (lastFrameCount != newCount) {
				lastFrameCount = newCount;
				crafter.sendProgressBarUpdate(this, 0, newCount);
			}
		}
	}


// TODO: remove
//	@Override
//	public void onCraftGuiOpened(IContainerListener listener) {
//		super.onCraftGuiOpened(listener);
//		int newCount = ItemUtils.getNBTTagCompound(heldInventory).getInteger("frame_blocks");
//		listener.sendProgressBarUpdate(this, 0, newCount);
//	}

	@Override
	public void handleElementButtonClick(String elementName, int mouseButton) {
		if (elementName.equals(NETWORK_ID_ADD)) {
			//add frame blocks
			if (inventory.getStackInSlot(0) == null || inventory.getStackInSlot(0).stackSize == 0) {
				return;
			}
			NBTTagCompound tag = ItemUtils.getNBTTagCompound(heldInventory);
			int count = tag.getInteger("frame_blocks");
			int maxCount = Settings.MAX_PORTAL_SIZE * 4 - 4;
			ItemStack items = inventory.decrStackSize(0, Math.max(0, maxCount - count));
			if (items != null) {
				if (!(items.getItem() instanceof ItemBlockPortalFrame) || items.getItemDamage() != PortalFrameType.BASIC_FRAME.ordinal()) {
					this.mergeItemStack(items, 0, 1, false); //error, put items back where they came from
				} else {
					count += items.stackSize;
					tag.setInteger("frame_blocks", count);
					//update inventory
					playerInventory.setInventorySlotContents(playerInventory.currentItem, heldInventory);
					onCraftMatrixChanged(inventory);
				}
			}
		}
	}


	@Override
	public void updateProgressBar(int index, int value) {
		super.updateProgressBar(index, value);
		switch (index) {
			case 0:
				ItemUtils.getNBTTagCompound(heldInventory).setInteger("frame_blocks", value);
				break;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack stackCopy = null;
		Slot slot = this.inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackSlot = slot.getStack();
			//noinspection ConstantConditions
			stackCopy = stackSlot.copy();

			if (slotId == 0) { //Our item
				if (!this.mergeItemStack(stackSlot, 1, this.inventorySlots.size(), true)) { //(inverted merge direction)
					return null;
				}
				slot.onSlotChange(stackSlot, stackCopy);
			} else {
				if (stackSlot.getItem() instanceof ItemBlockPortalFrame && stackSlot.getItemDamage() == PortalFrameType.BASIC_FRAME.ordinal()) {
					if (!this.mergeItemStack(stackSlot, 0, 1, false)) {
						return null;
					}
				} else if (slotId >= 1 && slotId < 28) {
					if (!this.mergeItemStack(stackSlot, 28, 37, false)) {
						return null;
					}
				} else if (slotId >= 28 && slotId < 37 && !this.mergeItemStack(stackSlot, 1, 28, false)) {
					return null;
				}
			}

			if (stackSlot.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (stackSlot.stackSize == stackCopy.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stackSlot);
		}
		return stackCopy;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (inventory.getStackInSlot(0) != null) {
			player.dropItem(inventory.removeStackFromSlot(0), false);
		}
	}
}
