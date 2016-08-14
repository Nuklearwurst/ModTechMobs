package com.fravokados.dangertech.core.plugin;

import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2Plugin;
import com.fravokados.dangertech.core.plugin.ic2.IC2UpgradeIntegration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;

public class PluginManager {

	public final static String IC2 = "IC2";
	public final static String TESLA = "tesla";

	private static boolean ic2Available = false;
	private static boolean teslaAvailable = false;

	public static void init() {

		if(Loader.isModLoaded(IC2)) {
			loadIC2();
		}

		if(Loader.isModLoaded(TESLA)) {
			loadTesla();
		}

		EnergyManager.init();
	}

	public static boolean ic2Activated() {
		return ic2Available;
	}

	public static boolean teslaActivated() {
		return teslaAvailable;
	}

	private static void loadTesla() {
		teslaAvailable = true;
	}

	private static void loadIC2() {
		try {
			ic2Available = IC2UpgradeIntegration.init();
			if(ic2Available) {
				ic2Available = IC2Plugin.init();
			}
		} catch (Exception e) {
			LogHelperCore.error(e, "IC2 Upgrade Integration Errored!");
		}
	}

	public static boolean isItemWrench(@Nullable ItemStack stack) {
		if(stack == null) {
			return false;
		}
		if(ic2Activated()) {
			return IC2Plugin.isItemWrench(stack);
		}
		return false;
	}

}
