package com.fravokados.mindim.util;

import net.minecraft.util.StatCollector;

import java.util.Arrays;
import java.util.List;

/**
 * Some of this code is adapted from <a href="https://github.com/micdoodle8/Galacticraft/blob/master/src/main/java/micdoodle8/mods/galacticraft/core/util/GCCoreUtil.java">Galacticraft</a><br>
 * Provides general Utility Functions
 *
 * @author Nuklearwurst
 */
public class GeneralUtils {

	/**
	 * translates a key to the current language stripping comment
	 * @param key the key that should get tramslated
	 * @return the translated String
	 */
	public static String translate(String key)
	{
		String result = StatCollector.translateToLocal(key);
		int comment = result.indexOf('#');
		return (comment > 0) ? result.substring(0, comment).trim() : result;
	}

	/**
	 * translates and splits a key to the current language stripping comment
	 * @param key the key that should get tramslated
	 * @return the translated String
	 */
	public static List<String> translateWithSplit(String key)
	{
		String translated = translate(key);
		int comment = translated.indexOf('#');
		translated = (comment > 0) ? translated.substring(0, comment).trim() : translated;
		return Arrays.asList(translated.split("\\$"));
	}

	/**
	 * translates and formats a key to the current language stripping comment
	 * @param key the key that should get tramslated
	 * @return the translated String
	 */
	public static String translateWithFormat(String key, Object... values)
	{
		String result = StatCollector.translateToLocalFormatted(key, values);
		int comment = result.indexOf('#');
		return (comment > 0) ? result.substring(0, comment).trim() : result;
	}

	/**
	 * checks coordinates
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param xMin x-minimum (inclusive)
	 * @param xMax x-maximum (exclusive)
	 * @param yMin y-minimum (inclusive)
	 * @param yMax y-maximum (exclusive)
	 * @return true if the coordinates are within the specified area
	 */
	public static boolean are2DCoordinatesInsideArea(int x, int y, int xMin, int xMax, int yMin, int yMax) {
		return x >= xMin && y >= yMin && x < xMax && y < yMax;
	}
}
