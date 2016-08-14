package com.fravokados.dangertech.core.lib;

import com.fravokados.dangertech.core.plugin.PluginManager;

public class Reference {

	public static final String MOD_ID = "dangertech";
	public static final String MOD_NAME = "Dangerous Technology";

	public static final String MOD_NAME_SHORT = "DT-Core";

	public static final String VERSION = "@VERSION@";

	public static final String PROXY_CLIENT = "com.fravokados.dangertech.core.client.ClientProxy";
	public static final String PROXY_SERVER = "com.fravokados.dangertech.core.common.CommonProxy";

	public static final String GUI_FACTORY = "com.fravokados.dangertech.core.configuration.gui.GuiFactoryConfig";

	public static final String ASSET_DIR = MOD_ID;

	public static final String DEPENDENCIES = "after:" + PluginManager.IC2;

	public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.10,1.10.2]";
}
