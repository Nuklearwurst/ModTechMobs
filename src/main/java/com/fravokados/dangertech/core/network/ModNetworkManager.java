package com.fravokados.dangertech.core.network;

import com.fravokados.dangertech.core.lib.Reference;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @author Nuklearwurst
 */
public class ModNetworkManager {

	public static SimpleNetworkWrapper INSTANCE;

	public static void init() {
		INSTANCE = new SimpleNetworkWrapper(Reference.MOD_ID.toLowerCase());

		//register messages, Side--> Side where the message should be processed

	}



}
