package com.fravokados.mindim.configuration;

import net.minecraft.world.World;

public class Settings {

	/** simple test to determine wether we are in a dev environment */
	public static final boolean IS_OBFUSCATED = !World.class.getSimpleName().equals("World");

	/** dimension id of mining dimension, final for now */
	public static final int dimensionId = 20;

	/** general testing features */
	public static boolean DEBUG_TESTING = false;
	/** dev env only testing features */
	public static boolean DEBUG = false;

	/** maximum size of the portal frame in any direction */
	public static int MAX_PORTAL_SIZE = 20;
	/** minimum size inside the frame */
	public static int MIN_PORTAL_SIZE = 2;

	/** time in ticks the portal takes to open a connection */
	public static int PORTAL_CONNECTION_TIME = 100;
	/** maximum time in ticks a portal can be held open */
	public static int MAX_PORTAL_CONNECTION_LENGTH = 20 * 20;

	public static int ENERGY_USAGE_INIT = 10000;
	public static int ENERGY_USAGE_CREATE_PORTAL = ENERGY_USAGE_INIT;
	public static int ENERGY_USAGE = 100;
	public static int ENERGY_STORAGE = 300000;

	public static float ENERGY_USAGE_MODIFIER_SAME_DIMENSION = 2F;

	/** should a mindim portal spawn with a card leading back */
	public static boolean PORTAL_SPAWN_WITH_CARD = false;

	/** whether it is possible to use mining dimension destination card to create a portal back to the overworld */
	public static boolean CAN_CREATE_PORTAL_BACK_TO_OVERWORLD = true;

	/** whether you should only be able to create a portal into the mining dimension from the overworld */
	public static boolean CAN_ONLY_ENTER_MINING_DIMENSION_FROM_OVERWORLD = false;

	/** true if portal spawn location should start at sea level (mindim portal creation) */
	public static boolean START_SPAWN_SEARCH_FROM_BOTTOM = true;
}
