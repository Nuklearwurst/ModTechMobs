package com.fravokados.techmobs.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings.Keys;
import com.fravokados.techmobs.lib.Strings.Keys.TechData;
import com.fravokados.techmobs.lib.Strings.Keys.TechScanning;
import com.fravokados.techmobs.lib.util.LogHelper;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
				LogHelper.error(Reference.MOD_NAME + " has had a problem loading its configuration!");
			}
		}
		//read config
		Property prop;
		
		config.addCustomCategoryComment(Keys.CATEGORY_TECH_DATA, "contains settings for techdata");
		config.addCustomCategoryComment(Keys.CATEGORY_TECH_SCANNING, "contains settings for techdata scanning");
		
		//////////////
		// TechData //
		//////////////
		
		Settings.TechData.MAX_EFFECTS = config.getInt(TechData.MAX_EFFECTS, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.MAX_EFFECTS, 0, Integer.MAX_VALUE, "Maximum number of effects that can be applied to one mob");
		Settings.TechData.TD_EFFECT_CHANCE = config.getInt(TechData.TD_EFFECT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_CHANCE, 0, Integer.MAX_VALUE,	"chance that an techdata effect gets applied to a mob.\ntechvalue out of n.\nlower value means higher chance");
		Settings.TechData.TD_EFFECT_MIN = config.getInt(TechData.TD_EFFECT_MIN, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_MIN, 0, Integer.MAX_VALUE, "Minimum strength of the effects");
		Settings.TechData.TD_EFFECT_MIN_FACTOR = config.getFloat(TechData.TD_EFFECT_MIN_FACTOR, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_EFFECT_MIN_FACTOR, 0, Float.MAX_VALUE, "Minimum strength of the effects scaling with techdata");
		//TODO check min values
		Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE = config.getInt(TechData.TD_RANDOM_PLAYER_EVENT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE, 0, Integer.MAX_VALUE, "chance that a techdata effect is applied to a player with high techvalue\n(1 / n)");
		Settings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE = config.getInt(TechData.TD_RANDOM_WORLD_EVENT_CHANCE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE, 0, Integer.MAX_VALUE, "chance that a random chunk effect gets triggered\n(1 / n)");
		Settings.TechData.SAFE_TECH_VALUE = config.getInt(TechData.SAFE_TECH_VALUE, Keys.CATEGORY_TECH_DATA, DefaultSettings.TechData.SAFE_TECH_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, "a techvalue below this value is considered safe (releveant is some special cases)\nSettings this to a high number will not protect the players!");
		
		//////////////////
		// TechScanning //
		//////////////////
		
		Settings.TechScanning.DANGER_CHUNK_FLAT = config.getInt(TechScanning.DANGER_CHUNK_FLAT, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_CHUNK_FLAT, 0, Integer.MAX_VALUE, "Flat value indicating when a chunk techlevel is treated as dangerous");
		Settings.TechScanning.DANGER_CHUNK_PERCENTAGE = config.getFloat(TechScanning.DANGER_CHUNK_PERCENTAGE, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_CHUNK_PERCENTAGE, 0, 1000, "Factor which works in addition to the flat value");
		Settings.TechScanning.DANGER_PLAYER_FLAT = config.getInt(TechScanning.DANGER_PLAYER_FLAT, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_PLAYER_FLAT, 0, Integer.MAX_VALUE, "Flat value indicating when a player techlevel is treated as dangerous");
		Settings.TechScanning.DANGER_PLAYER_PERCENTAGE = config.getFloat(TechScanning.DANGER_PLAYER_PERCENTAGE, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.DANGER_PLAYER_PERCENTAGE, 0, 1000, "Factor which works in addition to the flat value");
		Settings.TechScanning.MAX_SCANS_PER_TICK = config.getInt(TechScanning.MAX_SCANS_PER_TICK, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.MAX_SCANS_PER_TICK, 0, Integer.MAX_VALUE, "Value that controls how many chunks can be scanned in one tick (a high value can have high impact on server tickrate)"); 
		Settings.TechScanning.MAX_TASKS_SCHEDULED = config.getInt(TechScanning.MAX_TASKS_SCHEDULED, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.MAX_TASKS_SCHEDULED, 0, Integer.MAX_VALUE, "This value states how many scans may be scheduled at the same time (technical setting)");
		Settings.TechScanning.SCOUTING_STEP_FACTOR = config.getFloat(TechScanning.SCOUTING_STEP_FACTOR, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.SCOUTING_STEP_FACTOR, 0, 1, "Defines the speed at which mobs can scout an area");
		Settings.TechScanning.SPLIT_SCANS = config.getBoolean(TechScanning.SPLIT_SCANS, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.SPLIT_SCANS, ""); //TODO comment
		
		prop = config.get(Keys.CATEGORY_TECH_SCANNING, Keys.TechScanning.SPLIT_STEPS_KEY, DefaultSettings.TechScanning.SPLIT_STEPS_KEY);
		prop.comment = ""; //TODO comment
		Settings.TechScanning.SPLIT_STEPS_KEY = prop.getDoubleList();
		
		prop = config.get(Keys.CATEGORY_TECH_SCANNING, Keys.TechScanning.SPLIT_STEPS_VALUE, DefaultSettings.TechScanning.SPLIT_STEPS_VALUE);
		prop.comment = ""; //TODO comment
		Settings.TechScanning.SPLIT_STEPS_VALUE = prop.getDoubleList();
		
		Settings.TechScanning.INJECT_SCANNING_AI = config.getBoolean(TechScanning.INJECT_SCANNING_AI, Keys.CATEGORY_TECH_SCANNING, DefaultSettings.TechScanning.INJECT_SCANNING_AI, "Inject Scanning AI into vanilla mobs during spawn\nThis is experimental!\nDon't enable this if you don't know what you are doing!");
		
		
		//save config
		if(config.hasChanged()) {
			config.save();
		}
	}


	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals(Reference.MOD_ID))
			load(false);
	}

}
