package com.fravokados.dangertech.core.plugin.ic2;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyStorage;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.core.plugin.energy.IEnergyPlugin;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 *
 */
public class IC2EnergyPlugin implements IEnergyPlugin {

	@Override
	public boolean canItemProvideEnergy(ItemStack item, EnergyType type) {
		return item.getItem() instanceof IElectricItem && ((IElectricItem) item.getItem()).canProvideEnergy(item);
	}

	@Override
	public void rechargeEnergyStorageFromInventory(ItemStack stack, EnergyStorage storage, EnergyType type, IInventory inventory, int slot, int sinkTier) {
		if (EnergyManager.canItemProvideEnergy(stack, EnergyType.IC2)) {
			storage.receiveEnergy(ElectricItem.manager.discharge(stack, storage.getRoomForEnergy(), sinkTier, false, true, false), false);
		}
	}

	public static void postIC2TileLoadEvent(IEnergyTile te) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(te));
	}

	public static void postIC2TileUnloadEvent(IEnergyTile te) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(te));
	}
}
