package com.fravokados.techmobs.plugin;

import com.fravokados.techmobs.plugin.ic2.IC2UpgradeIntegration;

public class PluginManager {

	public static void init() {
		IC2UpgradeIntegration.init();
	}

	public static boolean ic2Activated() {
		return true;
	}

}
