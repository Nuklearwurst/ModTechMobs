package com.fravokados.dangertech.techmobs.inventory;

import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.network.IContainerIntegerListener;
import com.fravokados.dangertech.techmobs.network.ModTDNetworkManager;
import com.fravokados.dangertech.techmobs.network.message.MessageContainerIntegerUpdateClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class ContainerCreativeTechnology extends Container implements IContainerIntegerListener {

	private TileEntityCreativeTechnology te;
	private int oldValue = 0;

	public ContainerCreativeTechnology(TileEntityCreativeTechnology te) {
		this.te = te;
	}

// TODO: remove
//	@Override
//	public void onCraftGuiOpened(IContainerListener crafter) {
//		super.onCraftGuiOpened(crafter);
//		if (crafter instanceof EntityPlayerMP) {
//			ModTDNetworkManager.INSTANCE.sendTo(new MessageContainerIntegerUpdateClient((byte) 0, te.getTechData()), (EntityPlayerMP) crafter);
//		}
//	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if(oldValue != te.getTechData()) {
			for (Object crafter : this.listeners) {
				if (crafter instanceof EntityPlayerMP) {
					ModTDNetworkManager.INSTANCE.sendTo(new MessageContainerIntegerUpdateClient((byte) 0, te.getTechData()), (EntityPlayerMP) crafter);
				}
			}
		}
		oldValue =  te.getTechData();
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
	public void onIntegerUpdate(byte index, int value) {
		if(index == 0) {
			te.setTechData(value);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
