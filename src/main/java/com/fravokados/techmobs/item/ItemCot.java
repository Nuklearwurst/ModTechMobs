package com.fravokados.techmobs.item;

import com.fravokados.techmobs.block.BlockCot;
import com.fravokados.techmobs.common.ModBlocks;
import com.fravokados.techmobs.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemCot extends ItemTM {

	public ItemCot() {
		super(Strings.Item.COT);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (world.isRemote) {
			return true;
		} else if (side != 1) {
			return false;
		} else {
			y++;
			BlockCot blockCot = (BlockCot) ModBlocks.cot;
			int rotation = MathHelper.floor_double((double) (entityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte offsetX = 0;
			byte offsetZ = 0;

			switch (rotation) {
				case 0:
					offsetZ = 1;
					break;
				case 1:
					offsetX = -1;
					break;
				case 2:
					offsetZ = -1;
					break;
				case 3:
					offsetX = 1;
					break;
			}

			if (entityPlayer.canPlayerEdit(x, y, z, side, itemStack) && entityPlayer.canPlayerEdit(x + offsetX, y, z + offsetZ, side, itemStack)) {
				if (world.isAirBlock(x, y, z) && world.isAirBlock(x + offsetX, y, z + offsetZ) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + offsetX, y - 1, z + offsetZ)) {
					world.setBlock(x, y, z, blockCot, rotation, 3);

					if (world.getBlock(x, y, z) == blockCot) {
						world.setBlock(x + offsetX, y, z + offsetZ, blockCot, rotation + 8, 3);
					}

					itemStack.stackSize--;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}
