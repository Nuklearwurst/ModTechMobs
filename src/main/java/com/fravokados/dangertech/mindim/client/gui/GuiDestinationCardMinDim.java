package com.fravokados.dangertech.mindim.client.gui;

import com.fravokados.dangertech.mindim.inventory.ContainerDestinationCardMinDim;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.Textures;
import com.fravokados.dangertech.mindim.network.ModMDNetworkManager;
import com.fravokados.dangertech.mindim.network.message.MessageGuiElementClicked;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class GuiDestinationCardMinDim extends GuiContainer {

	public static final int BUTTON_ID_ADD = 0;

	private final ItemStack heldInventory;

	public GuiDestinationCardMinDim(InventoryPlayer inv, ItemStack stack) {
		super(new ContainerDestinationCardMinDim(inv, stack));
		heldInventory = stack;
		this.xSize = 176;
		this.ySize = 166;
	}

	@SuppressWarnings(value = {"unchecked"})
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(BUTTON_ID_ADD, guiLeft + 76, guiTop + 44, 50, 20, Strings.translate(Strings.Gui.GUI_ADD)));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(Textures.GUI_DESTINATION_CARD_MIN_DIM);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		int count = (heldInventory.getTagCompound() == null) ? 0 : heldInventory.getTagCompound().getInteger("frame_blocks");
		String s = Strings.translateWithFormat(Strings.Gui.DESTINATION_CARD_PORTAL_BLOCKS_STORED, count);
		fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 20, 4210752);
	}

	@Override
	protected void actionPerformed(GuiButton btn) throws IOException {
		switch (btn.id) {
			case BUTTON_ID_ADD:
				ModMDNetworkManager.INSTANCE.sendToServer(new MessageGuiElementClicked(ContainerDestinationCardMinDim.NETWORK_ID_ADD, 0));
				return;
		}
		super.actionPerformed(btn);
	}

}
