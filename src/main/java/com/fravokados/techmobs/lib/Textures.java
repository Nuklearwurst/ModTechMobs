package com.fravokados.techmobs.lib;

import net.minecraft.util.ResourceLocation;

public class Textures {

	public static final String GUI_LOCATION = "textures/gui/";

	public static final ResourceLocation GUI_UPGRADE_TOOL = getResourceLocation(GUI_LOCATION + "GuiUpgradeTool.png");

	public static final String TEXTURE_PREFIX = Reference.ASSET_DIR + ":";

	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(Reference.ASSET_DIR, path);
	}

	
}
