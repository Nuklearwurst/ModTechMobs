package com.fravokados.techmobs.client;

import com.fravokados.techmobs.client.render.RenderConservationUnit;
import com.fravokados.techmobs.common.CommonProxy;
import com.fravokados.techmobs.entity.EntityConservationUnit;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityConservationUnit.class, new RenderConservationUnit());
	}
}
