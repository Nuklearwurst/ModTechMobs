package com.fravokados.dangertech.mindim.block;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortal;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.mindim.item.ItemBlockPortalFrame;
import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	@GameRegistry.ObjectHolder(Strings.Block.portalMachineBase)
	public static final BlockMD blockPortalFrame = null;

	@GameRegistry.ObjectHolder(Strings.Block.portal)
	public static final BlockMD blockPortalBlock = null;

	public static void registerBlocks() {
		ModNwCore.proxy.registerBlock(new BlockPortalFrame(), ItemBlockPortalFrame.class);
		ModNwCore.proxy.registerBlock(new BlockPortalMinDim());
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityPortalControllerEntity.class, Strings.TileEntity.TILE_ENTITY_PORTAL_CONTROLLER_ENTITY);
		GameRegistry.registerTileEntity(TileEntityPortal.class, Strings.TileEntity.TILE_ENTITY_PORTAL);
		GameRegistry.registerTileEntity(TileEntityPortalFrame.class, Strings.TileEntity.TILE_ENTITY_PORTAL_FRAME);
	}
}
