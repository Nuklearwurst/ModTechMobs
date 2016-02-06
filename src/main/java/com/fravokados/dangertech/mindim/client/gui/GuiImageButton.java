package com.fravokados.dangertech.mindim.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Image-Button, loosely based on <a href="https://github.com/AppliedEnergistics/Applied-Energistics-2">AppliedEnergistics 2</a>
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class GuiImageButton extends GuiButton {

	private boolean half;
	private ResourceLocation texture;
	private int uvX, uvY;

	public GuiImageButton(int id, int x, int y, ResourceLocation texture) {
		this(id, x, y, false, texture);
	}

	public GuiImageButton(int id, int x, int y, boolean half, ResourceLocation texture) {
		this(id, x, y, half, texture, 0, 0);
	}

	public GuiImageButton(int id, int x, int y, boolean half, ResourceLocation texture, int uvX, int uvY) {
		super(id, x, y, half ? 8 : 16, half ? 8 : 16, "");
		this.half = half;
		this.texture = texture;
		this.uvX = uvX;
		this.uvY = uvY;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			if (this.half) {
				GL11.glPushMatrix();
				GL11.glTranslatef(this.xPosition, this.yPosition, 0.0F);
				GL11.glScalef(0.5f, 0.5f, 0.5f);

				if (this.enabled) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				} else {
					GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				}
				mc.renderEngine.bindTexture(texture);
				//needed?
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

				this.drawTexturedModalRect(0, 0, uvX, uvY, 16, 16);
				//needed?
				this.mouseDragged(mc, mouseX, mouseY);

				GL11.glPopMatrix();
			} else {
				if (this.enabled) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				} else {
					GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				}

				mc.renderEngine.bindTexture(texture);
				//needed?
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

				this.drawTexturedModalRect(this.xPosition, this.yPosition, uvX, uvY, 16, 16);
				//needed
				this.mouseDragged(mc, mouseX, mouseY);
			}
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void setVisibility(boolean vis) {
		this.visible = vis;
		this.enabled = vis;
	}
}
