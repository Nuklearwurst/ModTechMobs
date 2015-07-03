package com.fravokados.techmobs.client.gui;

import com.fravokados.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.techmobs.inventory.ContainerCreativeTechnology;
import com.fravokados.techmobs.lib.Textures;
import com.fravokados.techmobs.network.ModNetworkManager;
import com.fravokados.techmobs.network.message.MessageContainerIntegerUpdateServer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;

import java.awt.event.KeyEvent;

/**
 * @author Nuklearwurst
 */
public class GuiCreativeTechnology extends GuiContainer {

	private TileEntityCreativeTechnology te;
	private GuiTextField txtValue;
	private int oldValue = 0;



	public GuiCreativeTechnology(TileEntityCreativeTechnology te, ContainerCreativeTechnology container) {
		super(container);
		this.te = te;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		txtValue = new GuiTextField(fontRendererObj, width / 2 - 60, height / 2 - 25, 120, 20);
		txtValue.setFocused(true);
		txtValue.setText(String.valueOf(te.getTechData()));
		oldValue = te.getTechData();
		this.buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 + 25, 100, 20, "Save"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Textures.GUI_CREATIVE_TECHNOLOGY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if(te.getTechData() != oldValue && txtValue.getText().equals(String.valueOf(oldValue))) {
			txtValue.setText(String.valueOf(te.getTechData()));
			oldValue = te.getTechData();
		}
		this.txtValue.drawTextBox();
	}

	@Override
	protected void actionPerformed(GuiButton btn) {
		if(btn.id == 0) {
			try {
				ModNetworkManager.INSTANCE.sendToServer(new MessageContainerIntegerUpdateServer((byte) 0, Integer.parseInt(txtValue.getText())));
				this.mc.thePlayer.closeScreen();
			} catch (NumberFormatException e) {
				txtValue.setText(String.valueOf(te.getTechData()));
			}
		}
	}

	protected void keyTyped(char keyPressed, int keyCode) {
		this.txtValue.textboxKeyTyped(keyPressed, keyCode);

		if (keyCode == KeyEvent.VK_ENTER) {
			this.actionPerformed((GuiButton) this.buttonList.get(0));
		}

		super.keyTyped(keyPressed, keyCode);
	}
}
