package com.fravokados.dangertech.mindim.block;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.mindim.ModMiningDimension;
import com.fravokados.dangertech.mindim.lib.Reference;
import net.minecraft.block.material.Material;

/**
 * @author Nuklearwurst
 */

public class BlockMD extends BlockNW {


	public BlockMD(String registryName) {
		super(Material.rock, Reference.MOD_ID, registryName, ModMiningDimension.TAB_MD);
	}

	public BlockMD(Material material, String registryName) {
		super(material, Reference.MOD_ID, registryName, ModMiningDimension.TAB_MD);
	}
}