package com.fravokados.techmobs.lib.util;

import org.apache.logging.log4j.Level;

import com.fravokados.techmobs.lib.Reference;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	public static void log(Level logLevel, Object object, Object ... param) {
		FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object), param);
	}

	public static void log(Level logLevel, Throwable t,  Object object, Object ... param) {
		FMLLog.log(Reference.MOD_NAME, logLevel, t, String.valueOf(object), param);
	}

	public static void log(Level logLevel, Throwable t, Object object) {
		FMLLog.log(Reference.MOD_NAME, logLevel, t, String.valueOf(object));
	}

	public static void log(Level logLevel, Object object) {
		FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
	}

	public static void all(Object object) {
		log(Level.ALL, object);
	}

	public static void debug(Object object) {
		log(Level.DEBUG, object);
	}

	public static void error(Object object) {
		log(Level.ERROR, object);
	}
	public static void error(Throwable t, Object object) {
		log(Level.ERROR, t, object);
	}

	public static void fatal(Object object) {
		log(Level.FATAL, object);
	}
	public static void fatal(Throwable t, Object object) {
		log(Level.FATAL, t, object);
	}

	public static void info(Object object) {
		log(Level.INFO, object);
	}

	public static void info(Throwable t, Object object) {
		log(Level.INFO, t, object);
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
	public static void warn(Throwable t, Object object) {
		log(Level.WARN, t, object);
	}
}