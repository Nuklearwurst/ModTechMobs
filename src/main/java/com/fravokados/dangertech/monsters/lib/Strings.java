package com.fravokados.dangertech.monsters.lib;


import com.fravokados.dangertech.core.lib.util.GeneralUtils;

public class Strings {
	
	/**
	 * creative tab name
	 */
	public static final String CREATIVE_TAB = "techmobs";
	public static final String DAMAGE_SOURCE_EMP = "damage.emp";
	public static final String MATERIAL_QUANTUM_SWORD = "material.quantumSword";


	public static final class Entity {
		public static final String CYBER_ZOMBIE = "cyber_zombie";
		public static final String EMP_CREEPER = "emp_creeper";
		public static final String CON_UNIT = "con_unit";
	}
	
	public static final class Block {
		public static final String GATE_EXTENDER = "gateExtender";
		public static final String REPROCESSOR = "reprocessor";
		public static final String COT = "block_cot";
		public static final String CREATIVE_TECHNOLOGY = "creativeTechnology";
	}
	
	public static final class Item {
		public static final String MONSTER_DETECTOR = "monsterDetector";
		public static final String MONSTER_DROP = "monsterDrop";
		public static final String[] MONSTER_DROP_SUBTYPES = {"common"};
		public static final String[] MONSTER_DROP_TOOLTIP = {"common"};
		public static final String COT = "item_cot";
		public static final String TD_DEBUGGER = "tdDebugger";
		public static final String TD_ANALYZER = "tdAnalyzer";
		public static final String TD_ANALYZER_MODE_PREFIX = "item.tdAnalyzer.mode.";
		public static final String[] TD_ANALYZER_MODES = new String[] {"block", "item", "player", "chunk"};
		public static final String CONSERVATION_UNIT = "conservationUnit";
		public static final String QUANTUM_SWORD = "quantumSword";
	}
	
	public static final class Chat {

		public static final String mobTargetingWarning_generic = "chat.mobTargetingWarning.generic";
		public static final String mobTargetingWarning_exact_1 = "chat.mobTargetingWarning.exact.1";
		public static final String mobTargetingWarning_exact_2 = "chat.mobTargetingWarning.exact.2";
		public static final String mobTargetingWarning_creeper = "chat.mobTargetingWarning.creeper";
		public static final String mobTargetingWarning_babyZombie = "chat.mobTargetingWarning.babyZombie";

		public static final String effectNausea = "chat.effect.nausea";
		public static final String effectBlindness = "chat.effect.blindness";
		public static final String effectWeakness = "chat.effect.weakness";
		public static final String effectHunger = "chat.effect.hunger";
		public static final String effectMiningFatigue = "chat.effect.miningFatigue";
		public static final String effectSlowness = "chat.effect.slow";
		public static final String effectSlowness_2 = "chat.effect.slow_2";

		public static final String debuggerTileEntity = "chat.debugger.tileentity";
		public static final String debuggerTileEntityNotFound = "chat.debugger.tileentity.no_tile";
		public static final String debuggerPlayerTechLevel = "chat.debugger.player.techlevel";
		public static final String debuggerPlayerScouted = "chat.debugger.player.scouted";

		public static final String analyzerTileEntity = "chat.analyzer.tileentity";
		public static final String analyzerTileEntityNotFound = "chat.analyzer.tileentity.no_tile";
		public static final String analyzerPlayer = "chat.analyzer.player";
		public static final String analyzerPlayerNoPlayer = "chat.analyzer.player.no_player";
		public static final String analyzerChunk = "chat.analyzer.chunk";
		public static final String analyzerItem = "chat.analyzer.item";

		public static final String commandAnalyzeItem = "chat.command.analyze.item";
		public static final String commandAnalyzeItemNoItem = "chat.command.analyze.item.noitem";
		public static final String quantumSword = "chat.quantumSword";
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
			public static final String MAX_EFFECTS_MOB = "max_effects_mob";
			public static final String MAX_EFFECTS_PLAYER = "max_effects_player";
			public static final String MAX_EFFECTS_WORLD = "max_effects_world";
			public static final String TD_RANDOM_PLAYER_EVENT_CHANCE = "td_random_player_event_chance";
			public static final String TD_RANDOM_WORLD_EVENT_CHANCE = "td_random_world_event_chance";
			public static final String SAFE_TECH_VALUE = "safe_tech_value";
			public static final String CUSTOM_TILE_ENTITY_VALUES = "custom_tile_entity_values";
			public static final String CUSTOM_ITEM_VALUES = "custom_item_values";
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
			public static final String SCOUTING_STEP_FACTOR_WORLD = "scouting_step_factor_world";
			public static final String SCOUTING_STEP_FACTOR_PLAYER = "scouting_step_factor_player";
			public static final String INJECT_SCANNING_AI = "inject_scanning_ai";
		}

		public static final class Debug {
			public static final String DEBUG = "debug_deobf";
			public static final String DEBUG_TESTING = "debug_testing";
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

	public static String translate(String key) {
		return GeneralUtils.translate(key);
	}

	public static String translateWithFormat(String key, Object... values) {
		return GeneralUtils.translateWithFormat(key, values);
	}
}
