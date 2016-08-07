package com.fravokados.dangertech.portals.lib.util;

import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.lib.Reference;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class LogHelperMD {
	public static void log(Level logLevel, Object object) {
		FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
	}

	public static void all(Object object) {
		log(Level.ALL, object);
	}

	public static void debug(Object object) {
		log(Level.DEBUG, object);
	}

	/**
	 * logs info message only when in dev environment and DEBUG features are enabled
	 */
	public static void logDev(Object object) {
		if (Settings.DEBUG) {
			info(object);
		}
	}

	/**
	 * logs info message only when DEBUG_TESTING features are enabled
	 */
	public static void logTest(Object object) {
		if (Settings.DEBUG_TESTING) {
			info(object);
		}
	}

	public static void error(Object object) {
		log(Level.ERROR, object);
	}

	public static void fatal(Object object) {
		log(Level.FATAL, object);
	}

	public static void info(Object object) {
		log(Level.INFO, object);
	}

	public static void off(Object object) {
		log(Level.OFF, object);
	}

	public static void trace(Object object) {
		log(Level.TRACE, object);
	}

	public static void warn(Object object) {
		log(Level.WARN, object);
	}
}