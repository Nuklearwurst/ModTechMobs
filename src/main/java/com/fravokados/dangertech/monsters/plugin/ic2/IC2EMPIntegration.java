package com.fravokados.dangertech.monsters.plugin.ic2;

import com.fravokados.dangertech.monsters.plugin.PluginManager;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class IC2EMPIntegration {

	public static boolean handleTileEntityEMP(World world, int tileX, int tileY, int tileZ, double x, double y, double z, float strength, int radius, float factor) {
		if (PluginManager.ic2Activated()) {
			BlockPos pos = new BlockPos(tileX, tileY, tileZ);
			IEnergyTile te = EnergyNet.instance.getTile(world, pos);
			factor *= strength;
			float energyDraw = factor * 10000;
			if (te instanceof IEnergySource) {
				((IEnergySource) te).drawEnergy((int) Math.max(((IEnergySource) te).getOfferedEnergy(), ((IEnergySource) te).getOfferedEnergy() * 0.2F + energyDraw));
				return true;
			} else if (te instanceof IEnergySink) {
				//((IEnergySink) te).injectEnergy(EnumFacing.DOWN, energyDraw, strength);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param factor 1 / distance to entity
	 */
	public static boolean handleItemEMP(@Nullable ItemStack stack, Entity entity, double x, double y, double z, float strength, int radius, float factor) {
		if (PluginManager.ic2Activated() && stack != null) {
			if (stack.getItem() instanceof IElectricItem) {
				ElectricItem.manager.discharge(stack, (factor * strength / 8) * ElectricItem.manager.getCharge(stack) + factor * strength * 100, 4, true, false, false);
				return true;
			}
		}
		return false;
	}
}
