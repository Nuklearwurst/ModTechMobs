package com.fravokados.dangertech.monsters.network;

import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.network.message.MessageContainerIntegerUpdateClient;
import com.fravokados.dangertech.monsters.network.message.MessageContainerIntegerUpdateServer;
import com.fravokados.dangertech.monsters.network.message.MessageEMP;
import com.fravokados.dangertech.monsters.network.message.MessageGuiElementClicked;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Nuklearwurst
 */
public class ModTDNetworkManager {

	public static SimpleNetworkWrapper INSTANCE;

	public static void init() {
		INSTANCE = new SimpleNetworkWrapper(Reference.MOD_ID.toLowerCase());

		//register messages, Side--> Side where the message should be processed
		INSTANCE.registerMessage(MessageGuiElementClicked.class, MessageGuiElementClicked.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageContainerIntegerUpdateClient.class, MessageContainerIntegerUpdateClient.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageContainerIntegerUpdateServer.class, MessageContainerIntegerUpdateServer.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessageEMP.class, MessageEMP.class, 3, Side.CLIENT);
	}



}
