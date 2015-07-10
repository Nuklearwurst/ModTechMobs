package com.fravokados.techmobs.client;

import com.fravokados.techmobs.client.render.entity.RenderConservationUnit;
import com.fravokados.techmobs.client.render.entity.RenderEMPCreeper;
import com.fravokados.techmobs.common.CommonProxy;
import com.fravokados.techmobs.entity.EntityConservationUnit;
import com.fravokados.techmobs.entity.EntityEMPCreeper;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityConservationUnit.class, new RenderConservationUnit());
		RenderingRegistry.registerEntityRenderingHandler(EntityEMPCreeper.class, new RenderEMPCreeper());
	}
}
