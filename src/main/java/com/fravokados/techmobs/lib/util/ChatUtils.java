package com.fravokados.techmobs.lib.util;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * @author Nuklearwurst
 */
public class ChatUtils {

	public static IChatComponent getTranslatedChatComponentWithColor(String key, EnumChatFormatting color) {
		IChatComponent c = new ChatComponentTranslation(key);
		c.getChatStyle().setColor(color);
		return c;
	}
}
