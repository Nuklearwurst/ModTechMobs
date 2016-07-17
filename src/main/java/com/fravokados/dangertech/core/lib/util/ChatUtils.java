package com.fravokados.dangertech.core.lib.util;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Nuklearwurst
 */
public class ChatUtils {

	public static ITextComponent getTranslatedChatComponentWithColor(String key, TextFormatting color) {
		ITextComponent c = new TextComponentTranslation(key);
		c.getStyle().setColor(color);
		return c;
	}
}
