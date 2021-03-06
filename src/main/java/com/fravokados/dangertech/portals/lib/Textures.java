package com.fravokados.dangertech.portals.lib;

import net.minecraft.util.ResourceLocation;

/**
 * @author Nuklearwurst
 */
public class Textures {

	public static final String GUI_LOCATION = "textures/gui/";

	public static final ResourceLocation GUI_ENTITY_PORTAL_CONTROLLER = getResourceLocation(GUI_LOCATION + "gui_entity_portal_controller.png");
	public static final ResourceLocation GUI_DESTINATION_CARD_MIN_DIM = getResourceLocation(GUI_LOCATION + "gui_destination_card_mindim.png");
	public static final ResourceLocation GUI_BUTTON_EDIT = getResourceLocation(GUI_LOCATION + "gui_button_edit.png");


	public static final String TEXTURE_PREFIX = Reference.ASSET_DIR + ":";

	/*
	public static final String BLOCK_PORTAL_CONTROLLER_ONLINE = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_online";
	public static final String BLOCK_PORTAL_CONTROLLER_OFFLINE = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_offline";
	public static final String BLOCK_PORTAL_CONTROLLER_DISABLED = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_disabled";
	public static final String BLOCK_PORTAL_CONTROLLER_ONLINE_FRONT = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_front_online";
	public static final String BLOCK_PORTAL_CONTROLLER_OFFLINE_FRONT = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_front_offline";
	public static final String BLOCK_PORTAL_CONTROLLER_DISABLED_FRONT = TEXTURE_PREFIX + Strings.Block.portalMachineController + "_front_disabled";

	public static final String BLOCK_PORTAL_FRAME = TEXTURE_PREFIX + Strings.Block.portalMachineFrame;
	public static final String[] BLOCK_PORTAL_FRAME_BACK = {BLOCK_PORTAL_FRAME + "_back_disabled", BLOCK_PORTAL_FRAME + "_back_offline", BLOCK_PORTAL_FRAME + "_back_online"};
	public static final String[] BLOCK_PORTAL_FRAME_FRONT = {BLOCK_PORTAL_FRAME + "_front_disabled", BLOCK_PORTAL_FRAME + "_front_offline", BLOCK_PORTAL_FRAME + "_front_online"};
	public static final String[] BLOCK_PORTAL_FRAME_SIDE = {BLOCK_PORTAL_FRAME + "_side_disabled", BLOCK_PORTAL_FRAME + "_side_offline", BLOCK_PORTAL_FRAME + "_side_online"};
	public static final String[] BLOCK_PORTAL_FRAME_SIDE_ROTATED = {BLOCK_PORTAL_FRAME + "_side_disabled_rotated", BLOCK_PORTAL_FRAME + "_side_offline_rotated", BLOCK_PORTAL_FRAME + "_side_online_rotated"};
	*/

	// Items
	public static final String ITEM_DESTINATION_CARD = Strings.Item.destinationCard;
	public static final String ITEM_DESTINATION_CARD_MINDIM = Strings.Item.destinationCardMinDim;

	public static final String ITEM_UPGRADE_REVERSE_DIRECTION = Strings.Item.upgradeReverse;
	public static final String ITEM_UPGRADE_DISCONNECT = Strings.Item.upgradeDisconnect;
	public static final String ITEM_UPGRADE_DUMMY = Strings.Item.upgrade;
	public static final String ITEM_BLOCK_PORTAL_FRAME = Strings.Block.portalMachineFrame;
	public static final String ITEM_BLOCK_PORTAL_CONTROLLER = Strings.Block.portalMachineController;

	//Other
	public static final ResourceLocation GUI_SLOT_DESTINATION_CARD = getResourceLocation("gui/slot_destination_card");


	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(Reference.ASSET_DIR, path);
	}
}
