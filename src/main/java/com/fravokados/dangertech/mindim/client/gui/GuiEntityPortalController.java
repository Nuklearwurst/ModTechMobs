package com.fravokados.dangertech.mindim.client.gui;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.inventory.ContainerEntityPortalController;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.Textures;
import com.fravokados.dangertech.mindim.network.ModMDNetworkManager;
import com.fravokados.dangertech.mindim.network.message.MessageGuiElementClicked;
import com.fravokados.dangertech.mindim.network.message.MessageGuiTextUpdate;
import com.fravokados.dangertech.mindim.portal.PortalManager;
import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class GuiEntityPortalController extends GuiContainer {

	private static final int BUTTON_ID_START = 0;
	private static final int BUTTON_ID_STOP = 1;
	private static final int BUTTON_ID_EDIT = 2;

	private final TileEntityPortalControllerEntity te;

	private GuiButton btnStop;
	private GuiButton btnStart;

	private GuiTextField txtName;
	private GuiImageButton btnEdit;

	public GuiEntityPortalController(InventoryPlayer inv, TileEntityPortalControllerEntity te) {
		super(new ContainerEntityPortalController(inv, te));
		this.xSize = 176 + 47;
		this.ySize = 191;
		this.te = te;
	}

	@SuppressWarnings(value = {"unchecked"})
	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - 176) / 2 - 47;

		btnStart = new GuiButton(BUTTON_ID_START, guiLeft + 75, guiTop + 78, 56, 20, Strings.translate(Strings.Gui.GUI_START));
		this.buttonList.add(btnStart);
		btnStop = new GuiButton(BUTTON_ID_STOP, guiLeft + 133, guiTop + 78, 56, 20, Strings.translate(Strings.Gui.GUI_STOP));
		this.buttonList.add(btnStop);

		btnEdit = new GuiImageButton(BUTTON_ID_EDIT, guiLeft + 179, guiTop + 18, true, Textures.GUI_BUTTON_EDIT);
		this.buttonList.add(btnEdit);

		txtName = new GuiTextField(this.fontRendererObj, 58 + fontRendererObj.getStringWidth(Strings.translate(Strings.Gui.CONTROLLER_ID) + " "), 18, 90, 20);
		txtName.setTextColor(0x00FF00);
		txtName.setText(te.getInventoryName());
		txtName.setEnableBackgroundDrawing(false);
		txtName.setCanLoseFocus(false);
	}


	@Override
	protected void actionPerformed(GuiButton btn) {
		switch (btn.id) {
			case BUTTON_ID_START:
				ModMDNetworkManager.INSTANCE.sendToServer(new MessageGuiElementClicked(ContainerEntityPortalController.NETWORK_ID_START, 0));
				return;
			case BUTTON_ID_STOP:
				ModMDNetworkManager.INSTANCE.sendToServer(new MessageGuiElementClicked(ContainerEntityPortalController.NETWORK_ID_STOP, 0));
				return;
			case BUTTON_ID_EDIT:
				if(txtName.isFocused()) {
					updateNameText();
					txtName.setFocused(false);
				} else {
					if(!te.hasCustomInventoryName()) {
						txtName.setText("");
					}
					txtName.setFocused(true);
				}
				return;
		}
		super.actionPerformed(btn);
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		btnStart.enabled = te.getState() == TileEntityPortalControllerEntity.State.READY
				|| te.getState() == TileEntityPortalControllerEntity.State.INCOMING_CONNECTION
				|| (te.getState() == TileEntityPortalControllerEntity.State.INCOMING_PORTAL && (te.getUpgradeTrackerFlags() & TileEntityPortalControllerEntity.FLAG_CAN_REVERSE_PORTAL) == TileEntityPortalControllerEntity.FLAG_CAN_REVERSE_PORTAL);
		btnStop.enabled = te.getState() == TileEntityPortalControllerEntity.State.CONNECTING
				|| te.getState() == TileEntityPortalControllerEntity.State.OUTGOING_PORTAL
				|| (te.getState() == TileEntityPortalControllerEntity.State.INCOMING_PORTAL && (te.getUpgradeTrackerFlags() & TileEntityPortalControllerEntity.FLAG_CAN_DISCONNECT_INCOMING) == TileEntityPortalControllerEntity.FLAG_CAN_DISCONNECT_INCOMING);
		super.drawScreen(x, y, f);
		drawTooltips(x, y);
		if (!txtName.isFocused() && !te.getInventoryName().equals(txtName.getText())) {
			txtName.setText(te.getInventoryName());
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(Textures.GUI_ENTITY_PORTAL_CONTROLLER);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + 199, guiTop + 15 + 55 - getEnergyScaled(), 223, 36 + 55 - getEnergyScaled(), 16, getEnergyScaled());
	}

	private int getEnergyScaled() {
		return (int) (te.getEnergyStored() * 55 / te.getMaxEnergyStored());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
		//TODO hide/prettify destination and portal id
		drawString(this.fontRendererObj, Strings.translate(Strings.Gui.CONTROLLER_ID), 58, 18, 0xa2cc42);
		drawString(this.fontRendererObj, Strings.translateWithFormat(Strings.Gui.CONTROLLER_DESTINATION, getDestinationString(te.getDestination(), te.getStackInSlot(0))), 58, 30, 0xa2cc42);
		//TODO translate State
		drawString(this.fontRendererObj, Strings.translateWithFormat(Strings.Gui.CONTROLLER_STATE, te.getState()), 58, 40, 0xa2cc42);
		drawString(this.fontRendererObj, Strings.translateWithFormat(Strings.Gui.CONTROLLER_ERROR, te.getLastError() == TileEntityPortalControllerEntity.Error.NO_ERROR ? te.getLastError().getTranslationShort() : (EnumChatFormatting.RED + te.getLastError().getTranslationShort() + EnumChatFormatting.RESET)), 58, 50, 0xa2cc42);

		txtName.drawTextBox();
	}

	/**
	 * Draws Tooltips
	 *
	 * @param x mouse position x
	 * @param y mouse position y
	 */
	private void drawTooltips(int x, int y) {
		if(GeneralUtils.are2DCoordinatesInsideArea(x, y, guiLeft + 199, guiLeft + 199 + 16, guiTop + 15, guiTop + 15 + 55)) {
			List<String> list = new ArrayList<String>();
			//TODO support multiple energy types
			list.add(EnumChatFormatting.GRAY + "" + (int) te.getEnergyStored() + " EU / " + te.getMaxEnergyStored() + " EU" + EnumChatFormatting.RESET);
			drawHoveringText(list, x, y, fontRendererObj);
		} else if(GeneralUtils.are2DCoordinatesInsideArea(x, y, guiLeft + 55, guiLeft + 190, guiTop + 50, guiTop + 60)) {
			List<String> list = new ArrayList<String>();
			list.add((te.getLastError() == TileEntityPortalControllerEntity.Error.NO_ERROR ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + te.getLastError().getTranslationDetail() + EnumChatFormatting.RESET);
			drawHoveringText(list, x, y, fontRendererObj);
		} else if(GeneralUtils.are2DCoordinatesInsideArea(x, y, guiLeft + 179, guiLeft + 187, guiTop + 18, guiTop + 26)) {
			List<String> list = new ArrayList<String>();
			//TODO localization
			list.add("Edit name");
			drawHoveringText(list, x, y, fontRendererObj);
		}
	}


	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private String getDestinationString(int dest, ItemStack stack) {
		switch (dest) {
			case PortalManager.PORTAL_MINING_DIMENSION:
				return Strings.translate(Strings.Gui.CONTROLLER_DESTINATION_MINDIM);
			case PortalManager.PORTAL_NOT_CONNECTED:
				return Strings.translate(Strings.Gui.CONTROLLER_DESTINATION_NONE);
		}
		if(dest < 0) {
			return Strings.translate(Strings.Gui.CONTROLLER_DESTINATION_ERROR);// + " (" + dest + ")";
		}
		if(stack != null) {
			NBTTagCompound tag = ItemUtils.getNBTTagCompound(stack);
			if(tag.hasKey("destinationPortalName")) {
				return tag.getString("destinationPortalName");
			}
		}
		return Strings.translate(Strings.Gui.CONTROLLER_DESTINATION_UNKNOWN);// + " (" + dest + ")";
	}

	@Override
	protected void keyTyped(char c, int keyCode) {
		if(txtName.isFocused()) {
			switch (keyCode) {
				case 28:
				case 156:
					txtName.setFocused(false);
					updateNameText();
					return;
				case 1:
					txtName.setFocused(false);
					txtName.setText(te.getInventoryName());
					return;
			}
			txtName.textboxKeyTyped(c, keyCode);
			return;
		}
		super.keyTyped(c, keyCode);
	}

	@Override
	protected void mouseClicked(int x, int y, int btn) {
		super.mouseClicked(x, y, btn);
		if(txtName.isFocused()) {
			txtName.mouseClicked(x, y, btn);
		}
	}

	private void updateNameText() {
		String text = txtName.getText();
		ModMDNetworkManager.INSTANCE.sendToServer(new MessageGuiTextUpdate("controllerName", txtName.getText()));
		if(text.isEmpty()) {
			te.setName(null);
		} else {
			te.setName(text);
		}
		txtName.setText(te.getInventoryName());
	}
}
