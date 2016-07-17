package com.fravokados.dangertech.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public interface IBlockPlacedListener {
	void onBlockPostPlaced(World world, BlockPos pos, IBlockState state);
}
