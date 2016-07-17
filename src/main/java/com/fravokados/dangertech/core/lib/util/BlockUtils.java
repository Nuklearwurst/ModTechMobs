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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author Nuklearwurst
 */
public class BlockUtils {

	/**
	 * saves the blockpos data using the keys 'x', 'y' and 'z'
	 */
	public static NBTTagCompound writeBlockPosToNBT(@Nullable BlockPos pos, NBTTagCompound nbt) {
		if(pos != null) {
			nbt.setInteger("x", pos.getX());
			nbt.setInteger("y", pos.getY());
			nbt.setInteger("z", pos.getZ());
		}
		return nbt;
	}

	@Nullable
	public static BlockPos readBlockPosFromNBT(@Nullable NBTTagCompound nbt) {
		if(nbt == null || !nbt.hasKey("x") || !nbt.hasKey("y") || !nbt.hasKey("z")) {
			return null;
		}
		return new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}

	/**
	 * drops the upgrade inventory of a machine at the given position in the given world
	 */
	public static void dropUpgrades(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);

		if (!(tileEntity instanceof IUpgradable)) {
			return;
		}

		IUpgradeInventory inventory = ((IUpgradable) tileEntity).getUpgradeInventory();

		if(inventory == null) {
			return;
		}

		dropInventory(world, inventory, pos);
	}


	/**
	 * drop all items of the tileentity that is at the given position in the given world
	 */
	public static void dropInventory(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);

		if (!(tileEntity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory) tileEntity;

		dropInventory(world, inventory, pos);
	}

	/**
	 * drops all items contained in the given inventory into the world
	 * @param pos the position the items get sppawned at
	 */
	public static void dropInventory(World world, IInventory inventory, BlockPos pos) {

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack itemStack = inventory.getStackInSlot(i);

			if (itemStack != null && itemStack.stackSize > 0) {
				Random rand = new Random();

				float dX = rand.nextFloat() * 0.8F + 0.1F;
				float dY = rand.nextFloat() * 0.8F + 0.1F;
				float dZ = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack.copy());

				if (itemStack.hasTagCompound()) {
					//noinspection ConstantConditions
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

	public static List<ItemStack> addInventoryToList(List<ItemStack> list, @Nullable IInventory inv) {
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

	public static List<ItemStack> addInventoryToList(List<ItemStack> list, World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
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

	public static boolean isBlockReplaceable(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return world.isAirBlock(pos) || block.isReplaceable(world, pos) || block == Blocks.VINE || block == Blocks.TALLGRASS || block == Blocks.DEADBUSH;
	}

	public static boolean isTileEntityUsableByPlayer(TileEntity te, EntityPlayer player) {
		BlockPos pos = te.getPos();
		return te.getWorld().getTileEntity(pos) == te && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
	}
}
