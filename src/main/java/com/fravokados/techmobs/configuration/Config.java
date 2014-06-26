package com.fravokados.techmobs.configuration;

import java.io.File;

import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.util.LogHelper;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	public static void init(File configFile) {
		
		Configuration config = new Configuration(configFile);
		
		try {
			config.load();
			//configs
			
		} catch(Exception e) {
			LogHelper.error(Reference.MOD_NAME + " has had a problem loading its configuration!");
		} finally {
			if(config.hasChanged()) {
				config.save();
			}
		}
	}

}
