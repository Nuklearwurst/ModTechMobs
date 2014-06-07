package com.fravokados.techmobs.configuration;

public class Settings {

	public static class TechData {
		/**
		 * modifies chance that an effect gets applied to a mob<br>
		 * techdata out of n<br>
		 * lower value means higher chance
		 */
		public static int TD_EFFECT_CHANCE = 100;
		
		
		/**
		 * minimum strength of the effect scaling with techdata (added with flat value)
		 */
		public static float TD_EFFECT_MIN_FACTOR = 0;
		
		/**
		 * minimum strenth of the effect
		 */
		public static int TD_EFFECT_MIN = 10;
		
		public static int MAX_EFFECTS = 10;
		
//		/**
//		 * maximum strength modifier scaluing with techdata<br>
//		 * unused
//		 */
//		public static float TD_EFFECT_MAX_FACTOR = 0;
	
	}
}
