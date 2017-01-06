package com.fravokados.dangertech.api;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;

/**
 * @author Nuklearwurst
 */
public class DangerousTechnologyAPI {

	public static final String MOD_ID_CORE = "dangertech";
	public static final String MOD_ID_MONSTERS = "dangertechmobs";
	public static final String MOD_ID_PORTALS = "dangertechportals";

	public static final String API_ID = "dangerousTechnologyAPI";
	public static final String API_VERSION = "1.0";

	public static boolean isLoadedCore() {
		return Loader.isModLoaded(MOD_ID_CORE);
	}

	public static boolean isLoadedMonsters() {
		return Loader.isModLoaded(MOD_ID_MONSTERS);
	}

	public static boolean isLoadedPortals() {
		return Loader.isModLoaded(MOD_ID_PORTALS);
	}

	public static CreativeTabs creativeTabCore;
	public static CreativeTabs creativeTabMonsters;
	public static CreativeTabs creativeTabPortals;

}
