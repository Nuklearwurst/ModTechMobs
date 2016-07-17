package com.fravokados.dangertech.techmobs.client.render.entity;

import com.fravokados.dangertech.techmobs.entity.EntityEMPCreeper;
import com.fravokados.dangertech.techmobs.lib.Textures;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEMPCreeper extends RenderLiving<EntityEMPCreeper> {
	private static final ResourceLocation armoredCreeperTextures = Textures.getResourceLocation("textures/entity/emp_creeper_armor");
	private static final ResourceLocation creeperTextures = Textures.getResourceLocation("textures/entity/emp_creeper");

	/**
	 * The creeper model.
	 */
	private final ModelBase creeperModel = new ModelCreeper(2.0F);

	public RenderEMPCreeper(RenderManager renderManager) {
		super(renderManager, new ModelCreeper(), 0.5F);
	}

	@Override
	protected void preRenderCallback(EntityEMPCreeper entityCreeper, float partialTickTime) {
		float flashIntensity = entityCreeper.getCreeperFlashIntensity(partialTickTime);
		float flashCycle = 1.0F + MathHelper.sin(flashIntensity * 100.0F) * flashIntensity * 0.01F;

		if (flashIntensity < 0.0F) {
			flashIntensity = 0.0F;
		}

		if (flashIntensity > 1.0F) {
			flashIntensity = 1.0F;
		}

		flashIntensity *= flashIntensity;
		flashIntensity *= flashIntensity;
		float scaleXZ = (1.0F + flashIntensity * 0.4F) * flashCycle;
		float scaleY = (1.0F + flashIntensity * 0.1F) / flashCycle;
		GL11.glScalef(scaleXZ, scaleY, scaleXZ);
	}

	@Override
	protected int getColorMultiplier(EntityEMPCreeper entityCreeper, float lightBrightness, float partialTickTime) {
		float flashIntensity = entityCreeper.getCreeperFlashIntensity(partialTickTime);

		if ((int) (flashIntensity * 10.0F) % 2 == 0) {
			return 0;
		} else {
			int i = (int) (flashIntensity * 0.2F * 255.0F);

			if (i < 0) {
				i = 0;
			}

			if (i > 255) {
				i = 255;
			}

			return i << 24 | 0xffffff;
		}
	}


/*
	@Override
	protected int shouldRenderPass(EntityLivingBase entityLiving, int pass, float partialTickTime) {
		if (((EntityEMPCreeper) entityLiving).isPowered()) {
			if (entityLiving.isInvisible()) {
				GL11.glDepthMask(false);
			} else {
				GL11.glDepthMask(true);
			}

			if (pass == 1) {
				float age = (float) ((EntityEMPCreeper) entityLiving).ticksExisted + partialTickTime;
				this.bindTexture(armoredCreeperTextures);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float translation = age * 0.01F;
				GL11.glTranslatef(translation, translation, 0.0F);
				this.setRenderPassModel(this.creeperModel);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float color = 0.5F;
				GL11.glColor4f(color, color, color, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}

			if (pass == 2) {
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}
	*/

	/*
	@Override
	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
		return -1;
	}
	*/

	@Override
	protected ResourceLocation getEntityTexture(EntityEMPCreeper creeper) {
		return creeperTextures;
	}
}