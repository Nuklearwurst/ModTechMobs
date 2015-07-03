package com.fravokados.techmobs.plugin;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.plugin.ic2.IC2TechDataIntegration;
import com.fravokados.techmobs.plugin.ic2.IC2UpgradeIntegration;
import cpw.mods.fml.common.Loader;

public class PluginManager {

	public static void init() {
		try {
			IC2UpgradeIntegration.init();
		} catch (Exception e) {
			LogHelper.error(e, "IC2 Upgrade Integration Errored!");
		}
		try {
			IC2TechDataIntegration.init();
		} catch (Exception e) {
			LogHelper.error(e, "IC2 TechData Integration Errored!");
		}
	}

	public static boolean ic2Activated() {
		return Loader.isModLoaded("IC2");
	}

}
