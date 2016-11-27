package com.fravokados.dangertech.monsters.lib;

import net.minecraft.util.ResourceLocation;

public class Textures {

	public static final String GUI_LOCATION = "textures/gui/";
	public static final String ENTITY_LOCATION = "textures/entity/";

	public static final ResourceLocation GUI_CREATIVE_TECHNOLOGY = getResourceLocation(GUI_LOCATION + "gui_creative_technology.png");
	public static final ResourceLocation GUI_CONSERVATION_UNIT = getResourceLocation(GUI_LOCATION + "gui_conservation_unit.png");

	public static final ResourceLocation ENTITY_CONSERVATION_UNIT = getResourceLocation(ENTITY_LOCATION + "conservation_unit.png");

	public static final String MOD_ASSET_DOMAIN = Reference.ASSET_DIR + ":";

	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(Reference.ASSET_DIR, path);
	}

	
}
