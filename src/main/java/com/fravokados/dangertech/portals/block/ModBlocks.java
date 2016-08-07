package com.fravokados.dangertech.portals.block;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortal;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.portals.lib.Reference;
import com.fravokados.dangertech.portals.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	@GameRegistry.ObjectHolder(Strings.Block.portalMachineBase)
	public static final BlockMD blockPortalFrame = null;

	@GameRegistry.ObjectHolder(Strings.Block.portal)
	public static final BlockMD blockPortalBlock = null;

	public static void registerBlocks() {
		ModNwCore.proxy.registerBlock(new BlockPortalFrame());
		ModNwCore.proxy.registerBlock(new BlockPortalMinDim());
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityPortalControllerEntity.class, Strings.TileEntity.TILE_ENTITY_PORTAL_CONTROLLER_ENTITY);
		GameRegistry.registerTileEntity(TileEntityPortal.class, Strings.TileEntity.TILE_ENTITY_PORTAL);
		GameRegistry.registerTileEntity(TileEntityPortalFrame.class, Strings.TileEntity.TILE_ENTITY_PORTAL_FRAME);
	}
}
