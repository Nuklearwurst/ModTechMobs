package com.fravokados.dangertech.core.lib.util;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.api.upgrade.IUpgradeInventory;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * @author Nuklearwurst
 */
public class BlockUtils {

	public static void dropUpgrades(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof IUpgradable)) {
			return;
		}

		IUpgradeInventory inventory = ((IUpgradable) tileEntity).getUpgradeInventory();

		if(inventory == null) {
			return;
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack itemStack = inventory.getStackInSlot(i);

			if (itemStack != null && itemStack.stackSize > 0) {
				Random rand = new Random();

				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

				if (itemStack.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				itemStack.stackSize = 0;
			}
		}
	}

	public static void dropInventory(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack itemStack = inventory.getStackInSlot(i);

			if (itemStack != null && itemStack.stackSize > 0) {
				Random rand = new Random();

				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

				if (itemStack.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				itemStack.stackSize = 0;
			}
		}
	}

	public static List<ItemStack> addInventoryToList(List<ItemStack> list, IInventory inv) {
		if(inv != null) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null) {
					list.add(stack);
				}
			}
		}
		return list;
	}

	public static List<ItemStack> addInventoryToList(List<ItemStack> list, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null) {
			if(te instanceof IInventory) {
				addInventoryToList(list, (IInventory) te);
			}
			if(te instanceof IUpgradable) {
				addInventoryToList(list, ((IUpgradable) te).getUpgradeInventory());
			}
		}
		return list;
	}

	public static boolean isBlockReplaceable(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block == null || world.isAirBlock(x, y, z) || block.isReplaceable(world, x, y, z) ||
				block == Blocks.vine  || block == Blocks.tallgrass || block == Blocks.deadbush;
	}

	public static boolean isTileEntityUsableByPlayer(TileEntity te, EntityPlayer player) {
		return te.getWorldObj().getTileEntity(te.xCoord, te.yCoord, te.zCoord) == te && player.getDistanceSq((double) te.xCoord + 0.5D, (double) te.yCoord + 0.5D, (double) te.zCoord + 0.5D) <= 64.0D;
	}
}
