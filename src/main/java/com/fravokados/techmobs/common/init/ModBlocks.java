package com.fravokados.techmobs.common.init;

import com.fravokados.techmobs.block.BlockCot;
import com.fravokados.techmobs.block.BlockCreativeTechnology;
import com.fravokados.techmobs.block.BlockGateExtender;
import com.fravokados.techmobs.block.BlockTM;
import com.fravokados.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.techmobs.block.tileentity.TileEntityGateExtender;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModBlocks {

	/////////////////////
	// TechData Blocks //
	/////////////////////
	public static final BlockTM gateExtender = new BlockGateExtender();

	////////////////////
	// General Blocks //
	////////////////////
	public static final BlockTM block_cot = new BlockCot();

	//////////////////
	// Debug Blocks //
	//////////////////
	public static final BlockCreativeTechnology creativeTechnology = new BlockCreativeTechnology();
	
	public static void registerBlocks() {

		/////////////////////
		// TechData Blocks //
		/////////////////////
		GameRegistry.registerBlock(gateExtender, Strings.Block.GATE_EXTENDER);

		////////////////////
		// General Blocks //
		////////////////////
		GameRegistry.registerBlock(block_cot, Strings.Block.COT);

		//////////////////
		// Debug Blocks //
		//////////////////
		GameRegistry.registerBlock(creativeTechnology, Strings.Block.CREATIVE_TECHNOLOGY);
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGateExtender.class, Strings.Block.GATE_EXTENDER);

		GameRegistry.registerTileEntity(TileEntityCreativeTechnology.class, Strings.Block.CREATIVE_TECHNOLOGY);
	}
}
