package com.fravokados.dangertech.core.client.gui;

import com.fravokados.dangertech.api.core.upgrade.IUpgradable;
import com.fravokados.dangertech.core.inventory.ContainerUpgradeTool;
import com.fravokados.dangertech.core.lib.Textures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

/**
 * @author Nuklearwurst
 */
public class GuiUpgradeTool extends GuiContainer {

	public GuiUpgradeTool(InventoryPlayer invPlayer, IUpgradable te) {
		super(new ContainerUpgradeTool(invPlayer, te));
		this.xSize = 176;
		this.ySize = 166;
	}

	@SuppressWarnings(value = {"unchecked"})
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(Textures.GUI_UPGRADE_TOOL);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
