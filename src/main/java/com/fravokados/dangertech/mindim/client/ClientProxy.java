package com.fravokados.dangertech.mindim.client;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.client.render.TileEntityControllerEntityRenderer;
import com.fravokados.dangertech.mindim.common.CommonProxy;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortalControllerEntity.class, new TileEntityControllerEntityRenderer());
	}
}
