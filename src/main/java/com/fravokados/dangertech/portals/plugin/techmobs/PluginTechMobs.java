package com.fravokados.dangertech.portals.plugin.techmobs;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import net.minecraftforge.fml.common.Loader;

public class PluginTechMobs {
	public static final String MOD_ID = "dangertechmobs";

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
