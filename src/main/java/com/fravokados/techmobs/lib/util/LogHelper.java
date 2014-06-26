package com.fravokados.techmobs.lib.util;

import org.apache.logging.log4j.Level;

import com.fravokados.techmobs.lib.Reference;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * code adapted from pahimar's "Equivalent Exchange 3"
 * <br>
 * <a href=https://github.com/pahimar/Equivalent-Exchange-3>https://github.com/pahimar/Equivalent-Exchange-3</a>
 *
 */
public class LogHelper
{
    public static void log(Level logLevel, Object object)
    {
    	//this part was unneeded as already done by FML
        FMLLog.log(Reference.MOD_NAME, logLevel, /*"[" + Reference.MOD_ID.toUpperCase() + "] " +*/ String.valueOf(object));
    }

    public static void all(Object object)
    {
        log(Level.ALL, object);
    }

    public static void debug(Object object)
    {
        log(Level.DEBUG, object);
    }

    public static void error(Object object)
    {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object)
    {
        log(Level.FATAL, object);
    }

    public static void info(Object object)
    {
        log(Level.INFO, object);
    }

    public static void off(Object object)
    {
        log(Level.OFF, object);
    }

    public static void trace(Object object)
    {
        log(Level.TRACE, object);
    }

    public static void warn(Object object)
    {
        log(Level.WARN, object);
    }
}