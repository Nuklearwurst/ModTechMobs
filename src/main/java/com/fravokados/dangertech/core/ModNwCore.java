package com.fravokados.dangertech.core;


import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.core.common.CommonProxy;
import com.fravokados.dangertech.core.common.handler.GuiHandler;
import com.fravokados.dangertech.core.common.init.ModItems;
import com.fravokados.dangertech.core.configuration.ConfigHandler;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.Strings;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import com.fravokados.dangertech.core.network.ModNetworkManager;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.common.init.ModRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


/**
 * Shared code for Dangerous Technology and Mining Dimension Mod
 * @author Nuklearwurst
 */
@Mod(modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY,
		dependencies = Reference.DEPENDENCIES,
		acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class ModNwCore {

	@Mod.Instance(value = Reference.MOD_ID)
	public static ModNwCore instance;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;

	public static CreativeTabs CREATIVE_TABS;

	public static ConfigHandler config;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);
		//init networking
		ModNetworkManager.init();
		//init keybindings

		//init CreativeTab
		CREATIVE_TABS = new CreativeTabs(Strings.CREATIVE_TAB) {
			@Override
			public Item getTabIconItem() {
				//noinspection ConstantConditions
				return ModItems.upgradeTool;
			}
		};

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
		MinecraftForge.EVENT_BUS.register(config);


		//load recipes
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
