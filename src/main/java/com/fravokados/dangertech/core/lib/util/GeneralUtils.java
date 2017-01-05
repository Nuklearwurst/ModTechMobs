package com.fravokados.dangertech.core.lib.util;

import net.minecraft.util.text.translation.I18n;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Some of this code is adapted from <a href="https://github.com/micdoodle8/Galacticraft/blob/master/src/main/java/micdoodle8/mods/galacticraft/core/util/GCCoreUtil.java">Galacticraft</a><br>
 * Provides general Utility Functions
 *
 * @author Nuklearwurst
 */
public class GeneralUtils {

	/**
	 * public Random instance
	 */
	public static Random random = new Random();

	/**
	 * translates a key to the current language stripping comment
	 * @param key the key that should get translated
	 * @return the translated String
	 */
	public static String translate(String key)
	{
		String result = I18n.translateToLocal(key);
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
	 * @param key the key that should get translated
	 * @return the translated String
	 */
	public static String translateWithFormat(String key, Object... values)
	{
		String result = I18n.translateToLocalFormatted(key, values);
		int comment = result.indexOf('#');
		return (comment > 0) ? result.substring(0, comment).trim() : result;
	}

	/**
	 * Parses an integer from the current .lang file
	 * TODO: should this throw an NumberFormatException instead of providing a default value?
	 * @param key the unlocalized key
	 * @param def default if number could not be parsed
	 * @return number
	 */
	public static int getIntegerFromTranslatableKey(String key, int def) {
		return parseIntWithDefault(translate(key), def);
	}

	/**
	 * returns a random translation of the specified key<br>
	 * the amount of translations available has to be defined in the translation of: key + ".count"<br>
	 * all available translation keys have fit this scheme: key + ".NUMBER"
	 * @param key base-key of the random translations
	 * @param rand Random instance
	 * @return a Randomly chosen Translated String
	 */
	public static String getRandomTranslation(String key, Random rand) {
		return translate(getRandomTranslationKey(key, rand));
	}

	/**
	 * returns a random untranslated key for the specified key<br>
	 * the amount of translations available has to be defined in the translation of: key + ".count"<br>
	 * all available translation keys have fit this scheme: key + ".NUMBER"
	 * @param key base-key of the random translations
	 * @param rand Random instance
	 * @return a Randomly chosen untranslated key (following the pattern "key.index" where index is a random number from 0 to the number given by "key.count"
	 */
	public static String getRandomTranslationKey(String key, Random rand) {
		int index = getRandomIndexFromTranslationKey(key, rand);
		return key + "." + index;
	}

	/**
	 * the amount of translations available has to be defined in the translation of: key + ".count"<br>
	 * @param key base-key of the random translations
	 * @param rand Random instance
	 * @return a random number between 0 and "key.count"
	 */
	public static int getRandomIndexFromTranslationKey(String key, Random rand) {
		return rand.nextInt(getIntegerFromTranslatableKey(key + ".count", 1));
	}

	/**
	 * parses an Integer, uses the given default value if NaN
	 * @param i String to parse
	 * @param def default used when a NumberFormatException is thrown
	 * @return parsed Integer
	 */
	public static int parseIntWithDefault(String i, int def) {
		try {
			return Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * returns the absolute difference between the two given values
	 */
	public static int getDifference(int i1, int i2) {
		return Math.max(i1, i2) - Math.min(i1, i2);
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
