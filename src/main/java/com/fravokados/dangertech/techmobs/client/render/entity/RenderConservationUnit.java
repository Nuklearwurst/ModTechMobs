package com.fravokados.dangertech.techmobs.client.render.entity;

import com.fravokados.dangertech.techmobs.client.model.ModelConservationUnit;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.lib.Textures;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class RenderConservationUnit extends Render<EntityConservationUnit> {

	protected ModelConservationUnit modelConservationUnit;

	public RenderConservationUnit(RenderManager renderManager) {
		super(renderManager);
		this.modelConservationUnit = new ModelConservationUnit();
		this.shadowSize = 0.7F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityConservationUnit par1Entity) {
		return Textures.ENTITY_CONSERVATION_UNIT;
	}

	@Override
	public void doRender(EntityConservationUnit entity, double x, double y, double z, float par8, float rotate) {
		super.doRender(entity, x, y, z, par8, rotate);
		GL11.glPushMatrix();
		float yOffset = MathHelper.sin(((float) entity.age + rotate) / 10.0F + entity.hoverStart) * 0.2F + entity.getRenderYOffset();
		float rotation = (((float) entity.age + rotate) / 100.0F + entity.hoverStart) * (180F / (float) Math.PI);
		GL11.glTranslatef((float) x, (float) y + 1.45F + yOffset, (float) z);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		this.bindEntityTexture(entity);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);

		this.modelConservationUnit.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}