package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.lib.Strings;

/**
 * @author Nuklearwurst
 */
public class ItemCot extends ItemTM {

	public ItemCot() {
		super(Strings.Item.COT);
	}

	/*
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (side != EnumFacing.UP) {
			return false;
		} else {
			y++;
			int rotation = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
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

			if (player.canPlayerEdit(pos, side, stack) && player.canPlayerEdit(x + offsetX, y, z + offsetZ, side, stack)) {
				if (world.isAirBlock(pos) && world.isAirBlock(x + offsetX, y, z + offsetZ) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + offsetX, y - 1, z + offsetZ)) {
					world.setBlockState(pos, ModBlocks.block_cot, rotation, 3);

					if (world.getBlockState(pos).getBlock() == ModBlocks.block_cot) {
						world.setBlockState(x + offsetX, y, z + offsetZ, ModBlocks.block_cot, rotation + 8, 3);
					}

					stack.stackSize--;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	*/
}
