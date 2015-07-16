package com.fravokados.mindim.plugin;

import cpw.mods.fml.common.Loader;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class PluginIC2 {

	public static final int ENERGY_STORAGE_UPGRADE = 10000;

	public static final String MOD_ID = "IC2";

	public static boolean isAvailable() {
		return Loader.isModLoaded(MOD_ID);
	}

	public static boolean isItemWrench(ItemStack stack) {
		return isAvailable() && stack != null && (stack.getItem() == IC2Items.getItem("wrench").getItem() || stack.getItem() == IC2Items.getItem("electricWrench").getItem());
	}
}
