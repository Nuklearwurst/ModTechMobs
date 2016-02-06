package com.fravokados.dangertech.techmobs.client.gui;

import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.inventory.ContainerConservationUnit;
import com.fravokados.dangertech.techmobs.lib.Textures;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class GuiConservationUnit extends GuiContainer {

	private EntityConservationUnit conservationUnit;
	private GuiButton btnTakeAll;



	public GuiConservationUnit(EntityConservationUnit conservationUnit, ContainerConservationUnit container) {
		super(container);
		this.conservationUnit = conservationUnit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Textures.GUI_CONSERVATION_UNIT);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

	}

	@Override
	protected void actionPerformed(GuiButton btn) {

	}
}
