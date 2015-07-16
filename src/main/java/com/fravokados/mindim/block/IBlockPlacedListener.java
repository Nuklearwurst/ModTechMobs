package com.fravokados.mindim.block;

import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public interface IBlockPlacedListener {
	void onBlockPostPlaced(World world, int x,  int y, int z, int meta);
}
