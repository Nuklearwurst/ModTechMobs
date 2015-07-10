package com.fravokados.techmobs.plugin;

import com.fravokados.techmobs.api.DangerousTechnologyAPI;
import com.fravokados.techmobs.common.IEmpHandler;
import com.fravokados.techmobs.plugin.ic2.IC2EMPIntegration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class EMPHandler {

	public static void applyEMP(World world, int tileX, int tileY, int tileZ, double x, double y, double z, float strength, int radius) {
		TileEntity te = world.getTileEntity(tileX, tileY, tileZ);
		double deltaX = tileX - x;
		double deltaY = tileY - y;
		double deltaZ = tileZ - z;
		float factor = (float) (1 / Math.max(1, Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)));
		if (te != null) {
			if (te instanceof IEmpHandler) {
				((IEmpHandler) te).handleEMP(world, x, y, z, strength, radius, factor);
			} else {
				IC2EMPIntegration.handleTileEntityEMP(world, tileX, tileY, tileZ, x, y, z, strength, radius, factor);
			}
		}
	}

	public static void applyEMPOnEntity(Entity entity, double x, double y, double z, float strength, int radius) {
		float factor = (float) (1 / Math.max(1, entity.getDistance(x, y, z)));
		if (entity instanceof IEmpHandler) {
			((IEmpHandler) entity).handleEMP(entity.worldObj, x, y, z, strength, radius, factor);
		} else if(entity instanceof EntityPlayer) {
			for(ItemStack stack : ((EntityPlayer) entity).inventory.mainInventory) {
				handleItemEMP(stack, entity, x, y, z, strength, radius, factor);
			}
			for(ItemStack stack : ((EntityPlayer) entity).inventory.mainInventory) {
				handleItemEMP(stack, entity, x, y, z, strength, radius, factor);
			}
			entity.attackEntityFrom(DangerousTechnologyAPI.damageSourceEMP, factor * strength * 4);
		} else if (entity instanceof EntityLiving) {
			entity.attackEntityFrom(DangerousTechnologyAPI.damageSourceEMP, factor * strength * 4);
		}
	}

	public static void handleItemEMP(ItemStack stack, Entity entity, double x, double y, double z, float strength, int radius, float factor) {
		IC2EMPIntegration.handleItemEMP(stack, entity, x, y, z, strength, radius, factor);
	}
}
