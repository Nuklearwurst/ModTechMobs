package com.fravokados.dangertech.portals.client;

import com.fravokados.dangertech.portals.common.CommonProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void initRendering() {
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortalControllerEntity.class, new TESRPortalControllerEntity());
	}
}
