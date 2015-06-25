package com.fravokados.techmobs.configuration;

import net.minecraft.world.World;

public class Settings {

	public static final boolean IS_OBFUSCATED = !World.class.getSimpleName().equals("World");

	public static boolean DEBUG_TESTING = false;
	public static boolean DEBUG = false;

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
		 * used to set the percentage of chunks to scan based upon how many chunks are scheduled<br>
		 * this will make sure that when there are many chunks to scan more chunks will get scanned to decrease the queue<br>
		 * on the other hand it spreads the scanning on more ticks if only few scans are scheduled to be scanned<br><br>
		 *
		 * This value defines how many chunks get scanned in which step;
		 */
		public static double[] SPLIT_STEPS_VALUE = new double[] {0.1, 0.2, 0.4, 0.7, 1};

		/**
		 * used to set the percentage of chunks to scan based upon how many chunks are scheduled<br>
		 * this will make sure that when there are many chunks to scan more chunks will get scanned to decrease the queue<br>
		 * on the other hand it spreads the scanning on more ticks if only few scans are scheduled to be scanned<br><br>
		 *
		 * This value defines the steps
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

	public static class  TDPlayerEffects {
		public static int FAKE_EXPLOSION_VALUE = 500;
	}
}
