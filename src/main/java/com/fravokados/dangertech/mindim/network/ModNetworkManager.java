package com.fravokados.dangertech.mindim.network;

import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.network.message.MessageContainerIntegerUpdate;
import com.fravokados.dangertech.mindim.network.message.MessageContainerStringUpdate;
import com.fravokados.dangertech.mindim.network.message.MessageGuiElementClicked;
import com.fravokados.dangertech.mindim.network.message.MessageGuiTextUpdate;
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
		INSTANCE.registerMessage(MessageGuiTextUpdate.class, MessageGuiTextUpdate.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessageContainerStringUpdate.class, MessageContainerStringUpdate.class, 3, Side.CLIENT);
	}



}
