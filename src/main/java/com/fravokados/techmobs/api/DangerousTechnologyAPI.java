package com.fravokados.techmobs.api;

import com.fravokados.techmobs.api.techdata.effects.TDEffectRegistry;
import com.fravokados.techmobs.api.techdata.values.TDValueRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author Nuklearwurst
 */
public class DangerousTechnologyAPI {

	public static final String MOD_ID = "techmobs";
	public static final String API_ID = "dangerousTechnologyAPI";
	public static final String API_VERSION = "1.0";

	public static boolean isModLoaded() {
		return Loader.isModLoaded(MOD_ID);
	}

	public static TDEffectRegistry effectRegistry;
	public static TDValueRegistry valueRegistry;

	public static CreativeTabs creativeTab;
}
