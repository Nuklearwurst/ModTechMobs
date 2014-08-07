package com.fravokados.techmobs.configuration;

public class DefaultSettings {

	/**
	 * tech data
	 */
	public static class TechData {

		public static final int TD_EFFECT_CHANCE = 100;
		public static final float TD_EFFECT_MIN_FACTOR = 0;
		public static final int TD_EFFECT_MIN = 10;
		public static final int MAX_EFFECTS = 10;
		public static final int TD_RANDOM_PLAYER_EVENT_CHANCE = 100;
		public static final int TD_RANDOM_WORLD_EVENT_CHANCE = 100;
		public static final int SAFE_TECH_VALUE = 20;
	}

	/**
	 * tech data scanning
	 */
	public static class TechScanning {
		public static final int MAX_SCANS_PER_TICK = 10;
		public static final int MAX_TASKS_SCHEDULED = 100;
		public static final boolean SPLIT_SCANS = false;
		public static final double[] SPLIT_STEPS_VALUE = new double[] {0.1, 0.2, 0.4, 0.7, 1};
		public static final double[] SPLIT_STEPS_KEY = new double[] {0.4, 0.8, 1.3, 2.1, 2.7}; 
		public static final float DANGER_CHUNK_PERCENTAGE = 0.8F;
		public static final int DANGER_CHUNK_FLAT = 1000;
		public static final float DANGER_PLAYER_PERCENTAGE = 0.8F;
		public static final int DANGER_PLAYER_FLAT = 1000;
		public static final float SCOUTING_STEP_FACTOR = 0.1F;
		public static final boolean INJECT_SCANNING_AI = false;
	}
}
