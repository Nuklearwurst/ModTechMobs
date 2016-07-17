package com.fravokados.dangertech.techmobs.block;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.lib.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTM extends BlockNW {

	public BlockTM(String registryName) {
		super(Material.ROCK, Reference.MOD_ID, registryName, ModTechMobs.TAB_TM);
	}

	public BlockTM(Material material, String registryName) {
		super(material, Reference.MOD_ID, registryName, ModTechMobs.TAB_TM);
	}

	public BlockTM(Material material, String registryName, CreativeTabs tabs) {
		super(material, Reference.MOD_ID, registryName, tabs);
	}
	
}
