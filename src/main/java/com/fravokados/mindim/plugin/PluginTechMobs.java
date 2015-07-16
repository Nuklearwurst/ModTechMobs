package com.fravokados.mindim.plugin;

import com.fravokados.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.mindim.util.LogHelper;
import com.fravokados.techmobs.techdata.values.TDValues;
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
		LogHelper.debug("Loading Dangerous Technology Integration");
		TDValues.getInstance().registerTileEntityEntry(TileEntityPortalControllerEntity.class, 100);
		TDValues.getInstance().registerTileEntityEntry(TileEntityPortalFrame.class, 2);
	}
}
