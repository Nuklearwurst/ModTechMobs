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
		
		public static int MAX_EFFECTS_MOB =  10;
		
		public static int MAX_EFFECTS_PLAYER = 10;

		public static int MAX_EFFECTS_WORLD = 10;
		
//		/**
//		 * maximum strength modifier scaluing with techdata<br>
//		 * unused
//		 */
//		public static float TD_EFFECT_MAX_FACTOR = 0;
		
		/**
		 * 1 out of n<br>
		 * chance that a td effect is applied to a player
		 */
		public static int TD_RANDOM_PLAYER_EVENT_CHANCE = 100;
		
		/**
		 * 1 out of n<br>
		 * chance that a td effect is applied to a chunk
		 */
		public static int TD_RANDOM_WORLD_EVENT_CHANCE = 100;
		
		public static int TD_RANDOM_TICKS = 100; //TODO
		
		/**
		 * a techvalue below this value is considered safe (releveant is some special cases) <br>
		 * settings this to a high number will not protect the players!
		 */
		public static int SAFE_TECH_VALUE = 20;
	
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
		
		public static float DANGER_CHUNK_PERCENTAGE = 0.8F;
		
		public static int DANGER_CHUNK_FLAT = 1000;
		
		public static float DANGER_PLAYER_PERCENTAGE = 0.8F;
		
		public static int DANGER_PLAYER_FLAT = 1000;
		
		public static double SCOUTING_STEP_FACTOR_WORLD = 0.1;
		
		public static double SCOUTING_STEP_FACTOR_PLAYER = 0.1;

		public static boolean INJECT_SCANNING_AI = false;
		
	}
}
