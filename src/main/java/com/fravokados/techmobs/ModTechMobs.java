package com.fravokados.techmobs;

import com.fravokados.techmobs.api.DangerousTechnologyAPI;
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
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, canBeDeactivated=false)
public class ModTechMobs {
	
	@Instance(value = Reference.MOD_ID)
	public static ModTechMobs instance;
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;
	
	public static ConfigHandler config;

	public static String[] cTileValues;
	public static String[] cItemValues;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);
		//init networking
		
		//init keybindings
		
		//registerItems items
		ModItems.registerItems();
		//registerBlocks blocks
		ModBlocks.registerBlocks();
		//init API
		DangerousTechnologyAPI.effectRegistry = new TDEffects();
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

		for(String s : cTileValues) {
			String[] s1 = s.split(":");
			if(s1.length == 2) {
				try {
					Class clazz = Class.forName(s1[0]);
					int value = Integer.parseInt(s1[1]);
					//noinspection unchecked
					TDValues.getInstance().registerTileEntityEntry(clazz, value);
				} catch (ClassNotFoundException e) {
					LogHelper.warn("Custom TileEntity not found! Please check configs! (" + s + ")");
				} catch (ClassCastException e) {
					LogHelper.warn("Custom TileEntity Class is no TileEntity! Please check configs! (" + s + ")");
				} catch (NumberFormatException e) {
					LogHelper.warn("Invalid custom TE TechValue! Please check configs! (" + s + ")");
				}
			} else {
				LogHelper.warn("Invalid custom TE TechValue! Please check configs! (" + s + ")");
			}
		}

		for(String s : cItemValues) {
			String[] s1 = s.split(":");
			if(s1.length < 5) {
				try {
					Item item = GameRegistry.findItem(s1[0], s1[1]);
					if(item == null) {
						LogHelper.warn("Custom Item not found! Please check configs! (" + s + ")");
						continue;
					}
					if(s1.length == 4) {
						TDValues.getInstance().registerItemEntry(item, Integer.parseInt(s1[2]), Integer.parseInt(s1[3]));
					} else {
						TDValues.getInstance().registerItemEntry(item, Integer.parseInt(s1[2]));
					}
				} catch (NumberFormatException e) {
					LogHelper.warn("Invalid custom Item TechValue! Please check configs! (" + s + ")");
				}
			} else {
				LogHelper.warn("Invalid custom Item TechValue! Please check configs! (" + s + ")");
			}
		}
		
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
