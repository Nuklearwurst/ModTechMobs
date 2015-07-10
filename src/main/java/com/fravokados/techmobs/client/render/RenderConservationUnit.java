package com.fravokados.techmobs.client.render;

import com.fravokados.techmobs.entity.EntityConservationUnit;
import com.fravokados.techmobs.lib.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class RenderConservationUnit extends Render {
	private static final ResourceLocation RESOURCE_LOCATION = Textures.getResourceLocation("textures/entity/ConservationUnit.png");

	protected ModelConservationUnit modelConservationUnit;

	public RenderConservationUnit() {
		this.modelConservationUnit = new ModelConservationUnit();
		this.shadowSize = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return RESOURCE_LOCATION;
	}

	public void renderConservationUnit(EntityConservationUnit entity, double x, double y, double z, float par8, float rotate) {
		GL11.glPushMatrix();
		float yOffset = MathHelper.sin(((float) entity.age + rotate) / 10.0F + entity.hoverStart) * + 0.2F;
		float rotation = (((float) entity.age + rotate) / 100.0F + entity.hoverStart) * (180F / (float) Math.PI);
		GL11.glTranslatef((float) x, (float) y + 1.45F + yOffset, (float) z);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		this.bindEntityTexture(entity);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);

		this.modelConservationUnit.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float par8, float par9) {
		this.renderConservationUnit((EntityConservationUnit) entity, x, y, z, par8, par9);
	}
}