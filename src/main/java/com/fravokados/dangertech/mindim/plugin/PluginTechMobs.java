package com.fravokados.dangertech.mindim.plugin;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import cpw.mods.fml.common.Loader;

public class PluginTechMobs {
	public static final String MOD_ID = "techmobs";

	public static boolean isAvailable() {
		return Loader.isModLoaded(MOD_ID);
	}

	public static void init() {
		if(!isAvailable()) {
			return;
		}
		LogHelperMD.debug("Loading Dangerous Technology Integration");
		DangerousTechnologyAPI.valueRegistry.registerTileEntityEntry(TileEntityPortalControllerEntity.class, 100);
		DangerousTechnologyAPI.valueRegistry.registerTileEntityEntry(TileEntityPortalFrame.class, 2);
	}
}
