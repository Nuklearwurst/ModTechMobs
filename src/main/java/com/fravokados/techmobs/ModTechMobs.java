package com.fravokados.techmobs;

import com.fravokados.techmobs.command.CommandTechData;
import com.fravokados.techmobs.command.CommandTechMobs;
import com.fravokados.techmobs.command.CommandTechPlayer;
import com.fravokados.techmobs.common.CommonProxy;
import com.fravokados.techmobs.common.ModBlocks;
import com.fravokados.techmobs.common.ModEntities;
import com.fravokados.techmobs.common.ModItems;
import com.fravokados.techmobs.common.handler.GuiHandler;
import com.fravokados.techmobs.configuration.ConfigHandler;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.plugin.PluginManager;
import com.fravokados.techmobs.techdata.effects.TDEffects;
import com.fravokados.techmobs.techdata.values.TDValues;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, canBeDeactivated=false)
public class ModTechMobs {
	
	@Instance(value = Reference.MOD_ID)
	public static ModTechMobs instance;
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;
	
	public static ConfigHandler config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);
		//init networking
		
		//init keybindings
		
		//init items
		ModItems.init();
		//init blocks
		ModBlocks.init();	
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		//init modintegration
		PluginManager.init();
		//register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		//register TileEntities
		ModBlocks.registerTileEntities();
		
		//init rendering
		
		//register EventHandlers
		proxy.registerEvents();
		
		//Config handler
		FMLCommonHandler.instance().bus().register(config);
		
		//load Entities
		ModEntities.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		//init TD effects
		TDEffects.init();
		TDValues.init();
		
		LogHelper.info(Reference.MOD_NAME + ", version: " + Reference.VERSION +  ", has successfully loaded!");
	}
	
	@EventHandler
	public void disableMod(FMLModDisabledEvent evt) {
		LogHelper.info("Disabled " + Reference.MOD_NAME + " version: " + Reference.VERSION + "!");
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		//register events
		evt.registerServerCommand(new CommandTechMobs());
		evt.registerServerCommand(new CommandTechData());
		evt.registerServerCommand(new CommandTechPlayer());
	}

}
