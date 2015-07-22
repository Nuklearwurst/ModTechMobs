package com.fravokados.dangertech.techmobs.plugin;

import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import cpw.mods.fml.common.Loader;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Nuklearwurst
 */
public class BuildcraftBuildersTechDataIntegration {

	public static final String MOD_ID = "BuildCraft|Builders";

	private static final String[] tiles = new String[]{"buildcraft.builders.TileQuarry"};
	private static final int[] values = new int[]{100};

	public static boolean isAvailable() {
		return Loader.isModLoaded(MOD_ID);
	}

	public static void init() {
		if(!isAvailable()) {
			return;
		}
		for (int i = 0; i < tiles.length; i++) {
			try {
				@SuppressWarnings("unchecked") Class<? extends TileEntity> clazz = (Class<? extends TileEntity>) Class.forName(tiles[i]);
				TDValues.getInstance().registerTileEntityEntry(clazz, values[i]);
			} catch (ClassNotFoundException e) {
				LogHelperTM.warn(e, "Did not find Buildcraft class: " + tiles[i]);
			} catch (ClassCastException e) {
				LogHelperTM.warn(e, "Unknown Error loading Buildcraft class: " + tiles[i]);
			}
		}

	}
}
