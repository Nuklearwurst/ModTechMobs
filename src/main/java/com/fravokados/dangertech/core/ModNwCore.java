package com.fravokados.dangertech.core;


import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.core.common.CommonProxy;
import com.fravokados.dangertech.core.common.handler.GuiHandler;
import com.fravokados.dangertech.core.common.init.ModItems;
import com.fravokados.dangertech.core.common.init.ModRecipes;
import com.fravokados.dangertech.core.configuration.ConfigHandler;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.Strings;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2RecipeIntegrationCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLModDisabledEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;



/**
 * Shared code for Dangerous Technology and Mining Dimension Mod
 * @author Nuklearwurst
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, canBeDeactivated=false)
public class ModNwCore {

	@Mod.Instance(value = Reference.MOD_ID)
	public static ModNwCore instance;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;

	public static final CreativeTabs CREATIVE_TABS = new CreativeTabs(Strings.CREATIVE_TAB) {
		@Override
		public Item getTabIconItem() {
			//TODO proper creative tab item icon
			return Items.rotten_flesh;
		}
	};

	public static ConfigHandler config;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);
		//init networking

		//init keybindings

		//init API
		DangerousTechnologyAPI.creativeTab = CREATIVE_TABS;

		//registerItems items
		ModItems.registerItems();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		//init mod integration
		PluginManager.init();

		//register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		//register EventHandlers
		proxy.registerEvents();

		//Config handler
		FMLCommonHandler.instance().bus().register(config);

		//load recipes
		try {
			IC2RecipeIntegrationCore.init();
		} catch (Exception e) {
			LogHelperCore.error(e, "Critical error loading ic2 recipes!");
		}
		ModRecipes.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		LogHelperCore.info(Reference.MOD_NAME + ", version: " + Reference.VERSION + ", has successfully loaded!");
	}

	@Mod.EventHandler
	public void disableMod(FMLModDisabledEvent evt) {
		LogHelperCore.info("Disabled " + Reference.MOD_NAME + " version: " + Reference.VERSION + "!");
	}

}
