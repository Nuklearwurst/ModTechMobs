package com.fravokados.dangertech.portals;

import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.command.CommandEnterDimension;
import com.fravokados.dangertech.portals.common.ChunkLoaderCallback;
import com.fravokados.dangertech.portals.common.CommonProxy;
import com.fravokados.dangertech.portals.common.GuiHandler;
import com.fravokados.dangertech.portals.configuration.ConfigHandler;
import com.fravokados.dangertech.portals.dimension.ModDimensions;
import com.fravokados.dangertech.portals.event.ModEventHandler;
import com.fravokados.dangertech.portals.common.init.ModItems;
import com.fravokados.dangertech.portals.lib.Reference;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import com.fravokados.dangertech.portals.network.ModMDNetworkManager;
import com.fravokados.dangertech.portals.plugin.techmobs.PluginTechMobs;
import com.fravokados.dangertech.portals.portal.PortalManager;
import com.fravokados.dangertech.portals.common.init.ModRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


@Mod(modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY,
		dependencies = Reference.DEPENDENCIES)
public class ModMiningDimension {

	public static CreativeTabs TAB_MD;

	public PortalManager portalManager;

	public static ConfigHandler config;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER, modId = Reference.MOD_ID)
	public static CommonProxy proxy;

	@Mod.Instance(value = Reference.MOD_ID)
	public static ModMiningDimension instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);

		//register Creative Tab
		TAB_MD = new CreativeTabs(Strings.CREATIVE_TAB) {
			@Override
			public Item getTabIconItem() {
				//noinspection ConstantConditions
				return ModItems.itemDestinationCard;
			}
		};

		//register blocks and tileentities
		ModBlocks.registerBlocks();
		ModBlocks.registerTileEntities();

		//register items
		ModItems.registerItems();

		//register event handlers
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(config);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {

		//register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		//register Dimension
		ModDimensions.init();

		//initalize NetworkHandler
		ModMDNetworkManager.init();

		PluginTechMobs.init();
		//Disable LookingGlass integration for now
//        FMLInterModComms.sendMessage("LookingGlass", "API", "com.fravokados.dangertech.mindim.plugin.PluginLookingGlass.register");

		//Chunkloading
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoaderCallback());

		proxy.initRendering();

		//register recipes
		ModRecipes.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		LogHelperMD.info("Mod " + Reference.MOD_NAME + " version: " + Reference.VERSION + " has successfully loaded.");
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		//register server commands
		evt.registerServerCommand(new CommandEnterDimension());
	}
}
