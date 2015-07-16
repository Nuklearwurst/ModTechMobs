package com.fravokados.mindim.network;

import com.fravokados.mindim.lib.Reference;
import com.fravokados.mindim.network.message.MessageContainerIntegerUpdate;
import com.fravokados.mindim.network.message.MessageGuiElementClicked;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Nuklearwurst
 */
public class ModNetworkManager {

	public static SimpleNetworkWrapper INSTANCE;

	public static void init() {
		INSTANCE = new SimpleNetworkWrapper(Reference.MOD_ID.toLowerCase());

		//register messages, Side--> Side where the message should be processed
		INSTANCE.registerMessage(MessageGuiElementClicked.class, MessageGuiElementClicked.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageContainerIntegerUpdate.class, MessageContainerIntegerUpdate.class, 1, Side.CLIENT);
	}



}
