package com.fravokados.dangertech.techmobs.client;

import com.fravokados.dangertech.techmobs.client.render.entity.factory.RenderFactoryConservationUnit;
import com.fravokados.dangertech.techmobs.client.render.entity.factory.RenderFactoryEMPCreeper;
import com.fravokados.dangertech.techmobs.common.CommonProxy;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.entity.EntityEMPCreeper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityConservationUnit.class, new RenderFactoryConservationUnit());
		RenderingRegistry.registerEntityRenderingHandler(EntityEMPCreeper.class, new RenderFactoryEMPCreeper());
	}
}
