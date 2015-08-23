package com.fravokados.dangertech.mindim.lib;

import com.fravokados.dangertech.mindim.plugin.PluginIC2;

public class Reference {

    public static final String MOD_ID = "MiningDimension";
    public static final String MOD_NAME = "Mining Dimension";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES =   "required-after:" + "nwcore" + ";" +
//                                                    "required-after:" + PluginTechMobs.MOD_ID + ";" +
		                                            "required-after:" + PluginIC2.MOD_ID;


    public static final String PROXY_CLIENT = "com.fravokados.dangertech.mindim.client.ClientProxy";
    public static final String PROXY_SERVER = "com.fravokados.dangertech.mindim.common.CommonProxy";

    public static final String GUI_FACTORY = "com.fravokados.dangertech.mindim.configuration.gui.GuiFactoryConfig";

	public static final String ASSET_DIR = "mindim";
}
