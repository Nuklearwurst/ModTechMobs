package com.fravokados.techmobs.common;

import com.fravokados.techmobs.block.BlockGateExtender;
import com.fravokados.techmobs.block.BlockTM;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.tileentity.TileEntityGateExtender;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModBlocks {

	public static final BlockTM gateExtender = new BlockGateExtender();
	
	public static void init() {
		GameRegistry.registerBlock(gateExtender, Strings.Block.GATE_EXTENDER);
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGateExtender.class, Strings.Block.GATE_EXTENDER);
	}
}
