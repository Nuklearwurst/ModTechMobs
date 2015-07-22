package com.fravokados.dangertech.core.inventory;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class ContainerUpgradeTool extends Container {

	private IUpgradable te;

	public ContainerUpgradeTool(InventoryPlayer player, IUpgradable te) {
		super();
		this.te = te;

		//Upgrades
		int row;
		int column;
		for(row = 0; row < 3; ++row) {
			for(column = 0; column < 3; ++column) {
				this.addSlotToContainer(new Slot(te.getUpgradeInventory(), column + row * 3, 62 + column * 18, 17 + row * 18));
			}
		}

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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		te.updateUpgradeInformation();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return null;
	}
}
