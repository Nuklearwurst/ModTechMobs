package com.fravokados.dangertech.monsters.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.monsters.block.BlockCot;
import com.fravokados.dangertech.monsters.block.BlockCreativeTechnology;
import com.fravokados.dangertech.monsters.block.BlockTM;
import com.fravokados.dangertech.monsters.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModBlocks {

	/////////////////////
	// TechData Blocks //
	/////////////////////

	////////////////////
	// General Blocks //
	////////////////////
	@GameRegistry.ObjectHolder(Strings.Block.COT)
	public static final BlockTM block_cot = null;

	//////////////////
	// Debug Blocks //
	//////////////////
	@GameRegistry.ObjectHolder(Strings.Block.CREATIVE_TECHNOLOGY)
	public static final BlockCreativeTechnology creativeTechnology = null;
	
	public static void registerBlocks() {

		/////////////////////
		// TechData Blocks //
		/////////////////////

		////////////////////
		// General Blocks //
		////////////////////
		ModNwCore.proxy.registerBlock(new BlockCot());

		//////////////////
		// Debug Blocks //
		//////////////////
		ModNwCore.proxy.registerBlock(new BlockCreativeTechnology());
	}
	
	public static void registerTileEntities() {

		GameRegistry.registerTileEntity(TileEntityCreativeTechnology.class, Strings.Block.CREATIVE_TECHNOLOGY);
	}
}
