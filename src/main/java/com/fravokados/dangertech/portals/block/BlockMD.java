package com.fravokados.dangertech.portals.block;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.lib.Reference;
import net.minecraft.block.material.Material;

/**
 * @author Nuklearwurst
 */

public class BlockMD extends BlockNW {


	public BlockMD(String registryName) {
		super(Material.ROCK, Reference.MOD_ID, registryName, ModMiningDimension.TAB_MD);
	}

	public BlockMD(Material material, String registryName) {
		super(material, Reference.MOD_ID, registryName, ModMiningDimension.TAB_MD);
	}
}