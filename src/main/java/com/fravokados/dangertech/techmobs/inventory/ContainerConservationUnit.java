package com.fravokados.dangertech.techmobs.inventory;

import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

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
	public void addCraftingToCrafters(ICrafting crafter) {
		super.addCraftingToCrafters(crafter);

	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : this.crafters) {

		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
