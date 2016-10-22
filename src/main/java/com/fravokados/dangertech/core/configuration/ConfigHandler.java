package com.fravokados.dangertech.core.configuration;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.Strings;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
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
				LogHelperCore.error(Reference.MOD_NAME + " has had a problem loading its configuration!");
			}
		}
		//read config
		Property prop;
		
		///////////
		// DEBUG //
		///////////

		if(!Settings.IS_OBFUSCATED) {
			Settings.DEBUG = config.getBoolean(Strings.Keys.Debug.DEBUG, Configuration.CATEGORY_GENERAL, DefaultSettings.Debug.DEBUG, "Enables dev debug features");
		}
		Settings.DEBUG_TESTING = config.getBoolean(Strings.Keys.Debug.DEBUG_TESTING, Configuration.CATEGORY_GENERAL, DefaultSettings.Debug.DEBUG_TESTING, "Enables debug testing features");

		//save config
		if(config.hasChanged()) {
			config.save();
		}
	}


	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			ModNwCore.config.load(false);
			LogHelperCore.info("Reloading config!");
		}
	}

}
