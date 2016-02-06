package com.fravokados.dangertech.core.plugin;

import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import com.fravokados.dangertech.core.plugin.ic2.IC2UpgradeIntegration;

public class PluginManager {

	public static void init() {
		try {
			IC2UpgradeIntegration.init();
		} catch (Exception e) {
			LogHelperCore.error(e, "IC2 Upgrade Integration Errored!");
		}
	}

	public static boolean ic2Activated() {
		return false;
		//FIXME ic2 integration
//		return Info.isIc2Available();
	}

}
