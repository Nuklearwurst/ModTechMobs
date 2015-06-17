package com.fravokados.techmobs.plugin;

import com.fravokados.techmobs.plugin.ic2.IC2TechDataIntegration;
import com.fravokados.techmobs.plugin.ic2.IC2UpgradeIntegration;
import cpw.mods.fml.common.Loader;

public class PluginManager {

	public static void init() {
		IC2UpgradeIntegration.init();
		IC2TechDataIntegration.init();
	}

	public static boolean ic2Activated() {
		return Loader.isModLoaded("IC2");
	}

}
