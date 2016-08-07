package com.fravokados.dangertech.portals.network;

import com.fravokados.dangertech.portals.lib.Reference;
import com.fravokados.dangertech.portals.network.message.MessageContainerIntegerUpdate;
import com.fravokados.dangertech.portals.network.message.MessageContainerStringUpdate;
import com.fravokados.dangertech.portals.network.message.MessageGuiElementClicked;
import com.fravokados.dangertech.portals.network.message.MessageGuiTextUpdate;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Nuklearwurst
 */
public class ModMDNetworkManager {

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
