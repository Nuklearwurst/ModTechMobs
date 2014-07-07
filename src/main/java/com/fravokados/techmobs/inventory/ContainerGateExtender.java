package com.fravokados.techmobs.inventory;

import com.fravokados.techmobs.tileentity.TileEntityGateExtender;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerGateExtender extends Container {

	public ContainerGateExtender(InventoryPlayer invPlayer,
			TileEntityGateExtender tile) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return false;
	}

}
