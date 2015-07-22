package com.fravokados.dangertech.api;

import com.fravokados.dangertech.api.techdata.effects.TDEffectRegistry;
import com.fravokados.dangertech.api.techdata.values.TDValueRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;

/**
 * @author Nuklearwurst
 */
public class DangerousTechnologyAPI {

	public static final String MOD_ID_CORE = "nwcore";
	public static final String API_ID = "dangerousTechnologyAPI";
	public static final String API_VERSION = "1.0";

	public static boolean isModLoaded() {
		return Loader.isModLoaded(MOD_ID_CORE);
	}

	public static TDEffectRegistry effectRegistry;
	public static TDValueRegistry valueRegistry;

	public static CreativeTabs creativeTab;

	public static DamageSource damageSourceEMP;
}
