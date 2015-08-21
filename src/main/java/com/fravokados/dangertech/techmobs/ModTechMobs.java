package com.fravokados.dangertech.techmobs;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.techmobs.command.CommandTechData;
import com.fravokados.dangertech.techmobs.command.CommandTechMobs;
import com.fravokados.dangertech.techmobs.command.CommandTechPlayer;
import com.fravokados.dangertech.techmobs.common.CommonProxy;
import com.fravokados.dangertech.techmobs.common.handler.GuiHandler;
import com.fravokados.dangertech.techmobs.common.init.ModBlocks;
import com.fravokados.dangertech.techmobs.common.init.ModEntities;
import com.fravokados.dangertech.techmobs.common.init.ModItems;
import com.fravokados.dangertech.techmobs.common.init.ModRecipes;
import com.fravokados.dangertech.techmobs.configuration.ConfigHandler;
import com.fravokados.dangertech.techmobs.lib.Reference;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.network.ModTDNetworkManager;
import com.fravokados.dangertech.techmobs.plugin.PluginManager;
import com.fravokados.dangertech.techmobs.plugin.ic2.IC2RecipeIntegration;
import com.fravokados.dangertech.techmobs.techdata.effects.TDEffects;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, canBeDeactivated=false)
public class ModTechMobs {

	@Instance(value = Reference.MOD_ID)
	public static ModTechMobs instance;
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;

	public static final CreativeTabs TAB_TM = new CreativeTabs(Strings.CREATIVE_TAB) {
		@Override
		public Item getTabIconItem() {
			//TODO proper creative tab item icon
			return Items.rotten_flesh;
		}
	};
	
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

		//init API
		DangerousTechnologyAPI.effectRegistry = new TDEffects();
		DangerousTechnologyAPI.valueRegistry = new TDValues();
		DangerousTechnologyAPI.creativeTab = TAB_TM;
		DangerousTechnologyAPI.damageSourceEMP = new DamageSource(Strings.DAMAGE_SOURCE_EMP).setDamageBypassesArmor();

		//registerItems items
		ModItems.registerItems();
		//registerBlocks blocks
		ModBlocks.registerBlocks();
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
		proxy.registerRenderer();
		
		//register EventHandlers
		proxy.registerEvents();
		
		//Config handler
		FMLCommonHandler.instance().bus().register(config);
		
		//load Entities
		ModEntities.init();

		//load recipes
		try {
			IC2RecipeIntegration.init();
		} catch (Exception e) {
			LogHelperTM.error(e, "Critical error loading ic2 recipes!");
		}
		ModRecipes.init();

		//init networking
		ModTDNetworkManager.init();
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
					LogHelperTM.warn("Custom TileEntity not found! Please check configs! (" + s + ")");
				} catch (ClassCastException e) {
					LogHelperTM.warn("Custom TileEntity Class is no TileEntity! Please check configs! (" + s + ")");
				} catch (NumberFormatException e) {
					LogHelperTM.warn("Invalid custom TE TechValue! Please check configs! (" + s + ")");
				}
			} else {
				LogHelperTM.warn("Invalid custom TE TechValue! Please check configs! (" + s + ")");
			}
		}

		for(String s : cItemValues) {
			String[] s1 = s.split(":");
			if(s1.length < 5) {
				try {
					Item item = GameRegistry.findItem(s1[0], s1[1]);
					if(item == null) {
						LogHelperTM.warn("Custom Item not found! Please check configs! (" + s + ")");
						continue;
					}
					if(s1.length == 4) {
						TDValues.getInstance().registerItemEntry(item, Integer.parseInt(s1[2]), Integer.parseInt(s1[3]));
					} else {
						TDValues.getInstance().registerItemEntry(item, Integer.parseInt(s1[2]));
					}
				} catch (NumberFormatException e) {
					LogHelperTM.warn("Invalid custom Item TechValue! Please check configs! (" + s + ")");
				}
			} else {
				LogHelperTM.warn("Invalid custom Item TechValue! Please check configs! (" + s + ")");
			}
		}
		
		LogHelperTM.info(Reference.MOD_NAME + ", version: " + Reference.VERSION + ", has successfully loaded!");
	}
	
	@EventHandler
	public void disableMod(FMLModDisabledEvent evt) {
		LogHelperTM.info("Disabled " + Reference.MOD_NAME + " version: " + Reference.VERSION + "!");
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		//register events
		evt.registerServerCommand(new CommandTechMobs());
		evt.registerServerCommand(new CommandTechData());
		evt.registerServerCommand(new CommandTechPlayer());
	}

}
