package com.fravokados.dangertech.mindim;

import com.fravokados.dangertech.mindim.block.ModBlocks;
import com.fravokados.dangertech.mindim.command.CommandEnterDimension;
import com.fravokados.dangertech.mindim.common.ChunkLoaderCallback;
import com.fravokados.dangertech.mindim.common.CommonProxy;
import com.fravokados.dangertech.mindim.common.GuiHandler;
import com.fravokados.dangertech.mindim.configuration.ConfigHandler;
import com.fravokados.dangertech.mindim.dimension.ModDimensions;
import com.fravokados.dangertech.mindim.event.ModEventHandler;
import com.fravokados.dangertech.mindim.item.ModItems;
import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import com.fravokados.dangertech.mindim.network.ModMDNetworkManager;
import com.fravokados.dangertech.mindim.plugin.PluginTechMobs;
import com.fravokados.dangertech.mindim.portal.PortalManager;
import com.fravokados.dangertech.mindim.recipes.ModRecipes;
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


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY, dependencies = Reference.MOD_DEPENDENCIES)
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
        ModRecipes.initRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        LogHelperMD.info("Mod " + Reference.MOD_NAME + " version: " + Reference.MOD_VERSION + " has successfully loaded.");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent evt) {
	    //register server commands
        evt.registerServerCommand(new CommandEnterDimension());
    }
}
