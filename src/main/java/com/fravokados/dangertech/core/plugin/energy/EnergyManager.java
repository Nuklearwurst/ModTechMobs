package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * Functions to help manage energy apis
 * @author Nuklearwurst
 */
public class EnergyManager {

	/**
	 * checks whether this item can provide energy to the specified energy type
	 * @param item the itemstack to test
	 * @param type the energy type
	 * @return true if the given item can provide energy to the specified energy type
	 */
	public static boolean canItemProvideEnergy(ItemStack item, EnergyTypes type) {
		switch (type) {
			case IC2:
//				return item.getItem() instanceof IElectricItem && ((IElectricItem) item.getItem()).canProvideEnergy(item);
				return false;
			case VANILLA:
				return TileEntityFurnace.getItemBurnTime(item) > 0;
		}
		return false;
	}
}
