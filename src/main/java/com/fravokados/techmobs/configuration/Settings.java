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
	
	public static class TechScanning {
		
		public static int MAX_SCANS_PER_TICK = 10;
		
		public static int MAX_TASKS_SCHEDULED = 100;
		
		public static boolean SPLIT_SCANS = false;
		
		/**
		 * 
		 */
		public static double[] SPLIT_STEPS_VALUE = new double[] {0.1, 0.2, 0.4, 0.7, 1};
		
		/**
		 * 
		 */
		public static double[] SPLIT_STEPS_KEY = new double[] {0.4, 0.8, 1.3, 2.1, 2.7}; 
		
		public static double DANGER_CHUNK_PERCENTAGE = 0.8;
		
		public static int DANGER_CHUNK_FLAT = 1000;
		
		public static double DANGER_PLAYER_PERCENTAGE = 0.8;
		
		public static int DANGER_PLAYER_FLAT = 1000;
		
		public static double SCOUTING_STEP_FACTOR = 0.1;
		
	}
}
