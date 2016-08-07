package com.fravokados.dangertech.monsters.client.render.entity.factory;

import com.fravokados.dangertech.monsters.client.render.entity.RenderEMPCreeper;
import com.fravokados.dangertech.monsters.entity.EntityEMPCreeper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * @author Nuklearwurst
 */
public class RenderFactoryEMPCreeper implements IRenderFactory<EntityEMPCreeper> {
	@Override
	public Render<? super EntityEMPCreeper> createRenderFor(RenderManager manager) {
		return new RenderEMPCreeper(manager);
	}
}
