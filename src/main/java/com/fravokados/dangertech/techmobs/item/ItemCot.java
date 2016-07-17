package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.common.init.ModBlocks;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemCot extends ItemTM {

	public ItemCot() {
		super(Strings.Item.COT);
	}


	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos blockPosFeet, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return EnumActionResult.SUCCESS;
		} else if (side != EnumFacing.UP) {
			return EnumActionResult.FAIL;
		} else {
			boolean replaceableClicked = world.getBlockState(blockPosFeet).getBlock().isReplaceable(world, blockPosFeet);

			if (!replaceableClicked) {
				blockPosFeet = blockPosFeet.up();
			}

			int fourWayRotationInt = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			EnumFacing rotation = EnumFacing.getHorizontal(fourWayRotationInt);
			BlockPos blockPosHead = blockPosFeet.offset(rotation);

			if (player.canPlayerEdit(blockPosFeet, side, stack) && player.canPlayerEdit(blockPosHead, side, stack)) {
				boolean replaceableHead = world.getBlockState(blockPosHead).getBlock().isReplaceable(world, blockPosHead);
				boolean isFeetBlockFree = replaceableClicked || world.isAirBlock(blockPosFeet);
				boolean isHeadBlockFree = replaceableHead || world.isAirBlock(blockPosHead);

				if (isFeetBlockFree && isHeadBlockFree && world.isSideSolid(blockPosFeet.down(), EnumFacing.UP) && world.isSideSolid(blockPosHead.down(), EnumFacing.UP)) {
					//noinspection ConstantConditions
					IBlockState blockStateFoot = ModBlocks.block_cot.getDefaultState().withProperty(BlockBed.OCCUPIED, false).withProperty(BlockBed.FACING, rotation).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);

					if (world.setBlockState(blockPosFeet, blockStateFoot, 3)) {
						IBlockState blockStateHead = blockStateFoot.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
						world.setBlockState(blockPosHead, blockStateHead, 3);
					}

					--stack.stackSize;
					return EnumActionResult.SUCCESS;
				} else {
					return EnumActionResult.FAIL;
				}
			} else {
				return EnumActionResult.FAIL;
			}
		}
	}

}
