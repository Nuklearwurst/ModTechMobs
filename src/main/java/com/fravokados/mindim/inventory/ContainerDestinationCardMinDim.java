package com.fravokados.mindim.inventory;

import com.fravokados.mindim.block.BlockPortalFrame;
import com.fravokados.mindim.configuration.Settings;
import com.fravokados.mindim.inventory.slot.SlotDestinationCardMinDim;
import com.fravokados.mindim.item.ItemBlockPortalFrame;
import com.fravokados.mindim.network.IElementButtonHandler;
import com.fravokados.mindim.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public class ContainerDestinationCardMinDim extends Container implements IElementButtonHandler {

	private final ItemStack heldInventory;

	private final InventoryPlayer playerInventory;
	private final InventoryDestinationCardMinDim inventory;

	private int lastFrameCount = 0;

	public ContainerDestinationCardMinDim(InventoryPlayer player, ItemStack heldInventory) {
		super();
		this.playerInventory = player;
		this.heldInventory = heldInventory;
		this.inventory = new InventoryDestinationCardMinDim(null, "Destination Card");

		this.addSlotToContainer(new SlotDestinationCardMinDim(inventory, 0, 56, 46));

		//Player inventory
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
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
		for (Object crafter : this.crafters) {
			ICrafting icrafting = (ICrafting) crafter;
			int newCount = ItemUtils.getNBTTagCompound(heldInventory).getInteger("frame_blocks");
			if (lastFrameCount != newCount) {
				lastFrameCount = newCount;
				icrafting.sendProgressBarUpdate(this, 0, newCount);
			}
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting icrafting) {
		super.addCraftingToCrafters(icrafting);
		int newCount = ItemUtils.getNBTTagCompound(heldInventory).getInteger("frame_blocks");
		icrafting.sendProgressBarUpdate(this, 0, newCount);
	}

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
				if (!(items.getItem() instanceof ItemBlockPortalFrame) || items.getItemDamage() != BlockPortalFrame.META_FRAME_ENTITY) {
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
		Slot slot = (Slot) this.inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackSlot = slot.getStack();
			stackCopy = stackSlot.copy();

			if (slotId == 0) { //Our item
				if (!this.mergeItemStack(stackSlot, 1, this.inventorySlots.size(), true)) { //(inverted merge direction)
					return null;
				}
				slot.onSlotChange(stackSlot, stackCopy);
			} else {
				if (stackSlot.getItem() instanceof ItemBlockPortalFrame && stackSlot.getItemDamage() == BlockPortalFrame.META_FRAME_ENTITY) {
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
			player.dropPlayerItemWithRandomChoice(inventory.getStackInSlotOnClosing(0), false);
		}
	}
}
