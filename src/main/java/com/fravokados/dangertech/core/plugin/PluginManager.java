package com.fravokados.dangertech.core.plugin;

import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2UpgradeIntegration;
import net.minecraftforge.fml.common.Loader;

public class PluginManager {

	public final static String IC2 = "ic2";
	public final static String TESLA = "Tesla";

	private static boolean ic2Available = false;

	public static void init() {

		if(Loader.isModLoaded(IC2)) {
			loadIC2();
		}

		EnergyManager.init();
	}

	public static boolean ic2Activated() {
		return ic2Available;
	}

	private static void loadIC2() {
		try {
			ic2Available = IC2UpgradeIntegration.init();
		} catch (Exception e) {
			LogHelperCore.error(e, "IC2 Upgrade Integration Errored!");
		}
	}

}
