package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.techmobs.block.BlockCot;
import com.fravokados.dangertech.techmobs.block.BlockCreativeTechnology;
import com.fravokados.dangertech.techmobs.block.BlockTM;
import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.lib.Reference;
import com.fravokados.dangertech.techmobs.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModBlocks {

	/////////////////////
	// TechData Blocks //
	/////////////////////

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

		GameRegistry.registerTileEntity(TileEntityCreativeTechnology.class, Strings.Block.CREATIVE_TECHNOLOGY);
	}
}
