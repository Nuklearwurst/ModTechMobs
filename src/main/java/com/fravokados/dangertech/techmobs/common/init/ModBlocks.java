package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.techmobs.block.BlockCreativeTechnology;
import com.fravokados.dangertech.techmobs.block.BlockTM;
import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.lib.Reference;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModBlocks {

	/////////////////////
	// TechData Blocks //
	/////////////////////

	////////////////////
	// General Blocks //
	////////////////////
	public static final BlockTM block_cot = null;

	//////////////////
	// Debug Blocks //
	//////////////////
	public static final BlockCreativeTechnology creativeTechnology = null;
	
	public static void registerBlocks() {

		/////////////////////
		// TechData Blocks //
		/////////////////////

		////////////////////
		// General Blocks //
		////////////////////
//		GameRegistry.registerBlock(new BlockCot());

		//////////////////
		// Debug Blocks //
		//////////////////
		ModNwCore.proxy.registerBlock(new BlockCreativeTechnology());
	}
	
	public static void registerTileEntities() {

		GameRegistry.registerTileEntity(TileEntityCreativeTechnology.class, Strings.Block.CREATIVE_TECHNOLOGY);
	}
}
