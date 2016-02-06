package com.fravokados.dangertech.techmobs.plugin.ic2;

import com.fravokados.dangertech.techmobs.plugin.PluginManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class IC2EMPIntegration {

	public static boolean handleTileEntityEMP(World world, int tileX, int tileY, int tileZ, double x, double y, double z, float strength, int radius, float factor) {
		if(PluginManager.ic2Activated()) {
			/*TileEntity te = EnergyNet.instance.getTileEntity(world, tileX, tileY, tileZ);
			factor *= strength;
			float energyDraw = factor * 1000;
			if(te instanceof IEnergySource) {
				((IEnergySource) te).drawEnergy(energyDraw);
				return true;
			} else if(te instanceof IEnergyStorage) {
				((IEnergyStorage) te).setStored((int) Math.max(0, ((IEnergyStorage) te).getStored() - energyDraw));
				return true;
			} else if(te instanceof IEnergySink) {
//				((IEnergySink) te).injectEnergy(ForgeDirection.UNKNOWN, energyDraw, strength);
				return true;
			}*/
			//FIXME ic2 integration
		}
		return false;
	}

	/**
	 * @param factor 1 / distance to entity
	 */
	public static boolean handleItemEMP(ItemStack stack, Entity entity, double x, double y, double z, float strength, int radius, float factor) {
		if(PluginManager.ic2Activated() && stack != null) {
			/*
			if(stack.getItem() instanceof IElectricItem) {
				ElectricItem.manager.discharge(stack, (factor * strength / 10) * ElectricItem.manager.getCharge(stack) + factor * strength * 100, 4, true, false, false);
				return true;
			}
			*/
		}
		return false;
	}
}
