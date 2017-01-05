package com.fravokados.dangertech.monsters.plugin;

import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import com.fravokados.dangertech.monsters.plugin.ic2.IC2TechDataIntegration;

public class PluginManager {

	public static void init() {
		try {
			IC2TechDataIntegration.init();
		} catch (Exception e) {
			LogHelperTM.error(e, "IC2 TechData Integration Errored!");
		}
	}

	public static boolean ic2Activated() {
		return com.fravokados.dangertech.core.plugin.PluginManager.isIc2Available();
	}

}
