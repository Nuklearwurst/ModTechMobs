package com.fravokados.techmobs.lib;


public class Strings {
	
	/**
	 * creative tab name
	 */
	public static final String CREATIVE_TAB = "techmobs";
	

	public static final class Entity {
		public static final String CYBER_ZOMBIE = "cyber_zombie";
	}
	
	public static final class Block {
		public static final String GATE_EXTENDER = "gateExtender";
	}
	
	public static final class Item {
		public static final String MONSTER_DETECTOR = "monsterDetector";
	}
	
	public static final class Chat {

		public static final String mobTargetingWarning_generic = "chat.mobTargetingWarning.generic";
		public static final String mobTargetingWarning_exact_1 = "chat.mobTargetingWarning.exact.1";
		public static final String mobTargetingWarning_exact_2 = "chat.mobTargetingWarning.exact.2";
		public static final String mobTargetingWarning_creeper_1 = "chat.mobTargetingWarning.creeper.1";
		public static final String mobTargetingWarning_babyZombie_1 = "chat.mobTargetingWarning.babyZombie.1";
		
	}
	
	/**
	 * Config Keys
	 * @author Nuklearwurst
	 *
	 */
	public static final class Keys {
		/**
		 * TechData Category
		 * @author Nuklearwurst
		 *
		 */
		public static final class TechData {

			public static final String TD_EFFECT_CHANCE = "td_effect_chance";
			public static final String TD_EFFECT_MIN_FACTOR = "td_effect_min_factor";
			public static final String TD_EFFECT_MIN = "td_effect_min";
			public static final String MAX_EFFECTS = "max_effects";
			public static final String TD_RANDOM_PLAYER_EVENT_CHANCE = "td_random_player_event_chance";
			public static final String TD_RANDOM_WORLD_EVENT_CHANCE = "td_random_world_event_chance";
			public static final String SAFE_TECH_VALUE = "safe_tech_value";
		}
		/**
		 * TechScanning category
		 * @author Nuklearwurst
		 *
		 */
		public static final class TechScanning {
			public static final String MAX_SCANS_PER_TICK = "max_scans_per_tick";
			public static final String MAX_TASKS_SCHEDULED = "max_scheduled_scans";
			public static final String SPLIT_SCANS = "split_scans";
			public static final String SPLIT_STEPS_VALUE = "split_steps_value";
			public static final String SPLIT_STEPS_KEY = "split_steps_key"; 
			public static final String DANGER_CHUNK_PERCENTAGE = "dangerous_chunk_factor";
			public static final String DANGER_CHUNK_FLAT = "dangerous_chunk_flat";
			public static final String DANGER_PLAYER_PERCENTAGE = "dangerous_player_factor";
			public static final String DANGER_PLAYER_FLAT = "dangerous_player_flat";
			public static final String SCOUTING_STEP_FACTOR = "scouting_step_factor";
		}
		/**
		 * tech data
		 */
		public static final String CATEGORY_TECH_DATA = "techdata";
		/**
		 * tech data scanning
		 */
		public static final String CATEGORY_TECH_SCANNING = "tech-scanning";
	}
}
