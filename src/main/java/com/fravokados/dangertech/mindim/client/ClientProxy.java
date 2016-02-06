package com.fravokados.dangertech.mindim.client;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.client.render.TileEntityControllerEntityRenderer;
import com.fravokados.dangertech.mindim.common.CommonProxy;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void initRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortalControllerEntity.class, new TileEntityControllerEntityRenderer());
	}
}
