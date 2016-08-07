package com.fravokados.dangertech.portals.inventory;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.inventory.slot.SlotControllerDestinationCard;
import com.fravokados.dangertech.portals.inventory.slot.SlotEnergyFuel;
import com.fravokados.dangertech.portals.inventory.slot.SlotOutput;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.network.IContainerIntegerListener;
import com.fravokados.dangertech.portals.network.IElementButtonHandler;
import com.fravokados.dangertech.portals.network.IGuiTextUpdateHandler;
import com.fravokados.dangertech.portals.network.ModMDNetworkManager;
import com.fravokados.dangertech.portals.network.message.MessageContainerIntegerUpdate;
import com.fravokados.dangertech.portals.network.message.MessageContainerStringUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class ContainerEntityPortalController extends Container implements IElementButtonHandler, IContainerIntegerListener, IGuiTextUpdateHandler {

	public static final String NETWORK_ID_START = "epc_bstart";
	public static final String NETWORK_ID_STOP = "epc_bstop";

	private final TileEntityPortalControllerEntity te;

	private TileEntityPortalControllerEntity.Error lastError = TileEntityPortalControllerEntity.Error.NO_ERROR;
	private TileEntityPortalControllerEntity.State lastState = TileEntityPortalControllerEntity.State.READY;

	private int lastEnergyStored = 0;
	private int lastMaxEnergyStored = 100000;
	private int lastFlags = 0;
	private EnergyType lastType = null;

	private boolean nameChanged = true;


	public ContainerEntityPortalController(InventoryPlayer player, TileEntityPortalControllerEntity te) {
		super();
		this.te = te;

		this.addSlotToContainer(new SlotControllerDestinationCard(te, 0, 55, 80)); //destination card
		this.addSlotToContainer(new SlotEnergyFuel(te, 1, 199, 80, te)); //battery
		this.addSlotToContainer(new SlotControllerDestinationCard(te, 2, 14, 10)); //input destination card
		this.addSlotToContainer(new SlotOutput(te, 3, 14, 68)); //output destination card

		//Player inventory
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + 47 + j * 18, 109 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + 47 + i * 18, 167));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (IContainerListener crafter : this.listeners) {
			if (this.lastState != te.getState()) {
				crafter.sendProgressBarUpdate(this, 1, te.getState().ordinal());
			}
			if (this.lastError != te.getLastError()) {
				crafter.sendProgressBarUpdate(this, 2, te.getLastError().ordinal());
			}
			if (this.lastEnergyStored != (int) te.getEnergyStored()) {
				if (crafter instanceof EntityPlayerMP) {
					ModMDNetworkManager.INSTANCE.sendTo(new MessageContainerIntegerUpdate((byte) 0, (int) te.getEnergyStored()), (EntityPlayerMP) crafter);
				}
			}
			if (this.lastMaxEnergyStored != te.getMaxEnergyStored()) {
				if (crafter instanceof EntityPlayerMP) {
					ModMDNetworkManager.INSTANCE.sendTo(new MessageContainerIntegerUpdate((byte) 1, te.getMaxEnergyStored()), (EntityPlayerMP) crafter);
				}
			}
			if (this.lastFlags != te.getUpgradeTrackerFlags()) {
				crafter.sendProgressBarUpdate(this, 3, te.getUpgradeTrackerFlags());
			}
//			if(this.lastType != te.getEnergyType()) {
//				icrafting.sendProgressBarUpdate(this, 4, te.getEnergyType().getId());
//			}

			if (nameChanged && crafter instanceof EntityPlayerMP) {
				ModMDNetworkManager.INSTANCE.sendTo(new MessageContainerStringUpdate("controllerName", te.hasCustomName() ? te.getDisplayName().getUnformattedText() : ""), (EntityPlayerMP) crafter);
			}
		}
		this.lastState = te.getState();
		this.lastError = te.getLastError();
		this.lastEnergyStored = (int) te.getEnergyStored();
		this.lastMaxEnergyStored = te.getMaxEnergyStored();
		this.lastFlags = te.getUpgradeTrackerFlags();
//		this.lastType = te.getEnergyType();
		nameChanged = false;
	}


	@Override
	public void updateProgressBar(int index, int value) {
		super.updateProgressBar(index, value);
		switch (index) {
			case 0:
				this.te.setId(value);
				break;
			case 1:
				this.te.setState(TileEntityPortalControllerEntity.State.values()[value]);
				break;
			case 2:
				this.te.setLastError(TileEntityPortalControllerEntity.Error.values()[value]);
				break;
			case 3:
				this.te.setUpgradeTrackerFlags(value);
				break;
			case 4:
				this.te.setEnergyType(EnergyType.getEnergyType(value));
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

			if (slotId == 3) { //Destination Card Output
				if (!this.mergeItemStack(stackSlot, 4, this.inventorySlots.size(), true)) { //(inverted merge direction)
					return null;
				}
				slot.onSlotChange(stackSlot, stackCopy);
			} else if (slotId < 4) { //all other machine inventory slots
				if (!this.mergeItemStack(stackSlot, 4, this.inventorySlots.size(), false)) {
					return null;
				}
			} else { //from player:
				if (EnergyManager.canItemProvideEnergy(stackSlot, te.getEnergyType())) {
					//Fuel Item
					if (!this.mergeItemStack(stackSlot, 1, 2, false)) {
						return null;
					}
				} else if (stackSlot.getItem() instanceof ItemDestinationCard) {
					//Destination Card
					if (!this.mergeItemStack(stackSlot, 0, 1, false)) {
						return null;
					}
				} else if (slotId >= 4 && slotId < 31) {
					//To Hotbar
					if (!this.mergeItemStack(stackSlot, 31, 40, false)) {
						return null;
					}
				} else if (slotId >= 31 && slotId < 40 && !this.mergeItemStack(stackSlot, 4, 31, false)) {
					//From hotbar to player main inventory
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
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	@Override
	public void handleElementButtonClick(@Nullable String elementName, int mouseButton) {
		if (elementName != null) {
			if (elementName.equals(NETWORK_ID_START)) {
				te.handleStartButton(this);
			} else if (elementName.equals(NETWORK_ID_STOP)) {
				te.handleStopButton(this);
			}

		}
	}

	@Override
	public void onIntegerUpdate(byte index, int value) {
		switch (index) {
			case 0:
				this.te.getEnergyStorage().setEnergyStored(value);
				break;
			case 1:
				this.te.getEnergyStorage().setCapacity(value);
				break;
		}
	}

	@Override
	public void handleGuiTextUpdate(String elementName, String elementText) {
		if(elementName.equals("controllerName")) {
			if(elementText.isEmpty()) {
				elementText = null;
			}
			te.setName(elementText);
			nameChanged = true;
		}
	}
}
