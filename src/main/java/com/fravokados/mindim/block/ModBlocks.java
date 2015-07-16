package com.fravokados.mindim.block;

import com.fravokados.mindim.block.tileentity.TileEntityPortal;
import com.fravokados.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.mindim.item.ItemBlockPortalFrame;
import com.fravokados.mindim.lib.Reference;
import com.fravokados.mindim.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	@GameRegistry.ObjectHolder(Strings.Block.portalMachineBase)
	public static final BlockMD blockPortalFrame = new BlockPortalFrame();

	@GameRegistry.ObjectHolder(Strings.Block.portal)
	public static final BlockMD blockPortalBlock = new BlockPortalMinDim();

	public static void registerBlocks() {
		GameRegistry.registerBlock(ModBlocks.blockPortalFrame, ItemBlockPortalFrame.class, Strings.Block.portalMachineBase);
		GameRegistry.registerBlock(ModBlocks.blockPortalBlock, Strings.Block.portal);
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityPortalControllerEntity.class, Strings.TileEntity.TILE_ENTITY_PORTAL_CONTROLLER_ENTITY);
		GameRegistry.registerTileEntity(TileEntityPortal.class, Strings.TileEntity.TILE_ENTITY_PORTAL);
		GameRegistry.registerTileEntity(TileEntityPortalFrame.class, Strings.TileEntity.TILE_ENTITY_PORTAL_FRAME);
	}
}
