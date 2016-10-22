package com.fravokados.dangertech.monsters.configuration;

import com.fravokados.dangertech.monsters.ModTechMobs;
import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.lib.Strings.Keys;
import com.fravokados.dangertech.monsters.lib.Strings.Keys.TechData;
import com.fravokados.dangertech.monsters.lib.Strings.Keys.TechScanning;
import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import com.fravokados.dangertech.monsters.techdata.TDTickManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {

	public Configuration config; 

	public ConfigHandler(File configFile) {
		config = new Configuration(configFile);
	}

	public void load(boolean load) {
		//load config
		if(load) {
			try {
				config.load();
			} catch(Exception e) {
				LogHelperTM.error(e, Reference.MOD_NAME + " has had a problem loading its configuration!");
			}
		}
		//read config
		Property prop;
		
		config.addCustomCategoryComment(Keys.CATEGORY_TECH_DATA, "contains settings for techdata");
		config.addCustomCategoryComment(Keys.CATEGORY_TECH_SCANNING, "contains settings for techdata scanning");
		
		//////////////
		// TechData //
		//////////////
		
		Settings.TechData.MAX_EFFECTS_MOB = config.getInt(TechData.MAX_EFFECTS_MOB, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.MAX_EFFECTS_MOB, 0, Integer.MAX_VALUE, "Maximum number of effects that can be applied to one mob");
		Settings.TechData.MAX_EFFECTS_PLAYER = config.getInt(TechData.MAX_EFFECTS_PLAYER, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.MAX_EFFECTS_PLAYER, 0, Integer.MAX_VALUE, "Maximum number of effects that can be applied to one player");
		Settings.TechData.MAX_EFFECTS_CHUNK = config.getInt(TechData.MAX_EFFECTS_WORLD, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.MAX_EFFECTS_WORLD, 0, Integer.MAX_VALUE, "Maximum number of effects that can be applied to one chunk");
		Settings.TechData.TD_EFFECT_CHANCE = config.getInt(TechData.TD_EFFECT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_CHANCE, 0, Integer.MAX_VALUE,	"chance that an techdata effect gets applied to a mob.\ntechvalue out of n.\nlower value means higher chance");
		Settings.TechData.TD_EFFECT_MIN = config.getInt(TechData.TD_EFFECT_MIN, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_MIN, 0, Integer.MAX_VALUE, "Minimum strength of the effects");
		Settings.TechData.TD_EFFECT_MIN_FACTOR = config.getFloat(TechData.TD_EFFECT_MIN_FACTOR, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_MIN_FACTOR, 0, Float.MAX_VALUE, "Minimum strength of the effects scaling with techdata");
		//TODO check min values
		Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE = config.getInt(TechData.TD_RANDOM_PLAYER_EVENT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE, 0, Integer.MAX_VALUE, "chance that a techdata effect is applied to a player with high techvalue\n(1 / n)\nSetting this to 0 will disable random player effects");
		Settings.TechData.TD_RANDOM_CHUNK_EVENT_CHANCE = config.getInt(TechData.TD_RANDOM_WORLD_EVENT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE, 0, Integer.MAX_VALUE, "chance that a random chunk effect gets triggered\n(1 / n)\nSetting this to 0 will disable random chunk effects");
		Settings.TechData.SAFE_TECH_VALUE = config.getInt(TechData.SAFE_TECH_VALUE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.SAFE_TECH_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, "a techvalue below this value is considered safe (releveant is some special cases)\nSettings this to a high number will not protect the players!");

		//note: this doesn't need server client synchronization as techdata only ever gets evaluated on the server side
		//This gets handled by ModTechMobs.postInit
		ModTechMobs.cTileValues = config.getStringList(TechData.CUSTOM_TILE_ENTITY_VALUES, Keys.CATEGORY_TECH_DATA, new String[] {}, "Here you can define the fully qualified Names of TileEntity Classes and set custom techdata-values\nThe format is: tileentity:value");
		ModTechMobs.cItemValues = config.getStringList(TechData.CUSTOM_ITEM_VALUES, Keys.CATEGORY_TECH_DATA, new String[] {}, "Here you can define the ItemIds (Names) and set custom techdata-values\nThe format is: modid:item:value or modid:item:meta:value");
		//////////////////
		// TechScanning //
		//////////////////
		
		Settings.TechScanning.DANGER_CHUNK_FLAT = config.getInt(TechScanning.DANGER_CHUNK_FLAT, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_CHUNK_FLAT, 0, Integer.MAX_VALUE, "Flat value indicating when a chunk techlevel is treated as dangerous");
		Settings.TechScanning.DANGER_CHUNK_PERCENTAGE = config.getFloat(TechScanning.DANGER_CHUNK_PERCENTAGE, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_CHUNK_PERCENTAGE, 0, 1000, "Factor which works in addition to the flat value");
		Settings.TechScanning.DANGER_PLAYER_FLAT = config.getInt(TechScanning.DANGER_PLAYER_FLAT, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_PLAYER_FLAT, 0, Integer.MAX_VALUE, "Flat value indicating when a player techlevel is treated as dangerous");
		Settings.TechScanning.DANGER_PLAYER_PERCENTAGE = config.getFloat(TechScanning.DANGER_PLAYER_PERCENTAGE, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_PLAYER_PERCENTAGE, 0, 1000, "Factor which works in addition to the flat value");
		Settings.TechScanning.MAX_SCANS_PER_TICK = config.getInt(TechScanning.MAX_SCANS_PER_TICK, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.MAX_SCANS_PER_TICK, 0, Integer.MAX_VALUE, "Value that controls how many chunks can be scanned in one tick (a high value can have high impact on server tickrate)"); 
		Settings.TechScanning.MAX_TASKS_SCHEDULED = config.getInt(TechScanning.MAX_TASKS_SCHEDULED, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.MAX_TASKS_SCHEDULED, 0, Integer.MAX_VALUE, "This value states how many scans may be scheduled at the same time (technical setting)");
		Settings.TechScanning.SCOUTING_STEP_FACTOR_WORLD = config.getFloat(TechScanning.SCOUTING_STEP_FACTOR_WORLD, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.SCOUTING_STEP_FACTOR_WORLD, 0, 1, "Defines the speed at which mobs can scout an area");
		Settings.TechScanning.SCOUTING_STEP_FACTOR_PLAYER = config.getFloat(TechScanning.SCOUTING_STEP_FACTOR_PLAYER, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.SCOUTING_STEP_FACTOR_PLAYER, 0, 1, "Defines the speed at which mobs can scout a player");
		Settings.TechScanning.SPLIT_SCANS = config.getBoolean(TechScanning.SPLIT_SCANS, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.SPLIT_SCANS, "When this is set to true the amount of chunks scanned on tick gets adjusted to the amount of chunks that are queued to be scanned");
		
		
		prop = config.get(Keys.CATEGORY_TECH_SCANNING, Keys.TechScanning.SPLIT_STEPS_KEY, DefaultSettings.TechScanning.SPLIT_STEPS_KEY);
		prop.setComment("This defines the steps how chunks are scanned\n(eg. if step one is 0.2 and there are less then 20% of the maximum amount of chunks to be scanned in queue the amount of chunks defined in split_steps_value is scanned)");
		Settings.TechScanning.SPLIT_STEPS_KEY = prop.getDoubleList();
		
		prop = config.get(Keys.CATEGORY_TECH_SCANNING, Keys.TechScanning.SPLIT_STEPS_VALUE, DefaultSettings.TechScanning.SPLIT_STEPS_VALUE);
		prop.setComment("The percentage of chunks that get scanned for each step (in relation to the maximal amount of chunks that would otherwise be scanned)");
		Settings.TechScanning.SPLIT_STEPS_VALUE = prop.getDoubleList();
		
		Settings.TechScanning.INJECT_SCANNING_AI = config.getInt(TechScanning.INJECT_SCANNING_AI, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.INJECT_SCANNING_AI, 0, Integer.MAX_VALUE, "Inject Scanning AI into vanilla mobs during spawn\nThis is experimental!\nchance (n out of value) that a zombie or skeleton will spawn with scanning ai");

		////////////
		// DEBUG //
		///////////

		if(!Settings.IS_OBFUSCATED) {
			Settings.DEBUG = config.getBoolean(Keys.Debug.DEBUG, Configuration.CATEGORY_GENERAL, DefaultSettings.Debug.DEBUG, "Enables dev debug features");
		}
		Settings.DEBUG_TESTING = config.getBoolean(Keys.Debug.DEBUG_TESTING, Configuration.CATEGORY_GENERAL, DefaultSettings.Debug.DEBUG_TESTING, "Enables debug testing features");

		//save config
		if(config.hasChanged()) {
			config.save();
		}

		//recalculate scanning steps
		TDTickManager.calculateScanningSteps();
	}


	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			ModTechMobs.config.load(false);
			LogHelperTM.info("Reloading config!");
		}
	}

}
