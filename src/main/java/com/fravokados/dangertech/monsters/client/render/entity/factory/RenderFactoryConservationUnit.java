package com.fravokados.dangertech.monsters.client.render.entity.factory;

import com.fravokados.dangertech.monsters.client.render.entity.RenderConservationUnit;
import com.fravokados.dangertech.monsters.entity.EntityConservationUnit;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * @author Nuklearwurst
 */
public class RenderFactoryConservationUnit implements IRenderFactory<EntityConservationUnit> {
	@Override
	public Render<? super EntityConservationUnit> createRenderFor(RenderManager manager) {
		return new RenderConservationUnit(manager);
	}
}
