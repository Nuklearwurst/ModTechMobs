package com.fravokados.dangertech.techmobs.plugin;

import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.plugin.ic2.IC2TechDataIntegration;
import ic2.api.info.Info;

public class PluginManager {

	public static void init() {
		try {
			IC2TechDataIntegration.init();
		} catch (Exception e) {
			LogHelperTM.error(e, "IC2 TechData Integration Errored!");
		}
	}

	public static boolean ic2Activated() {
		return Info.isIc2Available();
	}

}
