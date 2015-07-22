package com.fravokados.dangertech.techmobs.client;

import com.fravokados.dangertech.techmobs.client.render.entity.RenderConservationUnit;
import com.fravokados.dangertech.techmobs.client.render.entity.RenderEMPCreeper;
import com.fravokados.dangertech.techmobs.common.CommonProxy;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.entity.EntityEMPCreeper;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityConservationUnit.class, new RenderConservationUnit());
		RenderingRegistry.registerEntityRenderingHandler(EntityEMPCreeper.class, new RenderEMPCreeper());
	}
}
