package com.fravokados.dangertech.techmobs.lib;

import net.minecraft.util.ResourceLocation;

public class Textures {

	public static final String GUI_LOCATION = "textures/gui/";

	public static final ResourceLocation GUI_CREATIVE_TECHNOLOGY = getResourceLocation(GUI_LOCATION + "GuiCreativeTechnology.png");
	public static final ResourceLocation GUI_CONSERVATION_UNIT = getResourceLocation(GUI_LOCATION + "GuiConservationUnit.png");

	public static final String MOD_ASSET_DOMAIN = Reference.ASSET_DIR + ":";

	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(Reference.ASSET_DIR, path);
	}

	
}
