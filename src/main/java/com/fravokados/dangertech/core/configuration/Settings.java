package com.fravokados.dangertech.core.configuration;

import net.minecraft.world.World;

public class Settings {

	public static final boolean IS_OBFUSCATED = !World.class.getSimpleName().equals("World");

	public static boolean DEBUG_TESTING = false;
	public static boolean DEBUG = false;
}
