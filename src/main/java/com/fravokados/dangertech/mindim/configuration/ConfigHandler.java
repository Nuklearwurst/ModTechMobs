package com.fravokados.dangertech.mindim.configuration;

import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.lib.Strings.Keys;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler {

	public final Configuration config;

	public ConfigHandler(File configFile) {
		config = new Configuration(configFile);
	}

	/**
	 * loads the config and applies settings<br>
	 * also saves changes to disk
	 * @param loadFromDisk whether the config should be loaded from disk, false when modifing config ingame
	 */
	public void load(boolean loadFromDisk) {
		//load config
		if(loadFromDisk) {
			try {
				config.load();
			} catch(Exception e) {
				LogHelperMD.error(Reference.MOD_NAME + " has had a problem loading its configuration!");
			}
		}
		//read config

		/////////////
		// GENERAL //
		/////////////

		Settings.PORTAL_SPAWN_WITH_CARD = config.getBoolean(Keys.General.PORTAL_SPAWN_WITH_CARD, Configuration.CATEGORY_GENERAL, DefaultSettings.General.PORTAL_SPAWN_WITH_CARD, "Should a portal to the Mining Dimension also spawn a Destination Card leading to the Origin-Portal?");
		Settings.MAX_PORTAL_CONNECTION_LENGTH = config.getInt(Keys.General.PORTAL_MAX_CONNECTION_LENGTH, Configuration.CATEGORY_GENERAL, DefaultSettings.General.PORTAL_MAX_CONNECTION_LENGTH, 0, 20 * 60 * 20, "Maximum duration of a connection");
		Settings.LOAD_CHUNKS = config.getBoolean(Keys.General.PORTAL_LOAD_CHUNKS, Configuration.CATEGORY_GENERAL, DefaultSettings.General.LOAD_CHUNKS, "Set this to false if you don't want the destination to be chunkloaeded. Portals will close if the destination chunk unloads");

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
	}


	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			load(false);
			LogHelperMD.info("Reloading config!");
		}
	}

}
