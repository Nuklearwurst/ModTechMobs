package com.fravokados.mindim;

import com.fravokados.mindim.block.ModBlocks;
import com.fravokados.mindim.command.CommandEnterDimension;
import com.fravokados.mindim.common.CommonProxy;
import com.fravokados.mindim.common.GuiHandler;
import com.fravokados.mindim.configuration.ConfigHandler;
import com.fravokados.mindim.configuration.Settings;
import com.fravokados.mindim.dimension.WorldProviderMiningDimension;
import com.fravokados.mindim.event.ModEventHandler;
import com.fravokados.mindim.item.ModItems;
import com.fravokados.mindim.lib.Reference;
import com.fravokados.mindim.lib.Strings;
import com.fravokados.mindim.network.ModNetworkManager;
import com.fravokados.mindim.plugin.PluginTechMobs;
import com.fravokados.mindim.portal.PortalManager;
import com.fravokados.mindim.recipes.RecipeManager;
import com.fravokados.mindim.util.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY, dependencies = Reference.MOD_DEPENDENCIES, canBeDeactivated=false)
public class ModMiningDimension {

    public static final CreativeTabs TAB_MD = new CreativeTabs(Strings.CREATIVE_TAB) {
	    @Override
	    public Item getTabIconItem() {
		    return Items.boat;
	    }
    };

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
        DimensionManager.registerProviderType(Settings.dimensionId, WorldProviderMiningDimension.class, false);
        DimensionManager.registerDimension(Settings.dimensionId, Settings.dimensionId);

	    //initalize NetworkHandler
	    ModNetworkManager.init();

	    PluginTechMobs.init();

		//register recipes
        RecipeManager.initRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        LogHelper.info("Mod " + Reference.MOD_NAME + " version: " + Reference.MOD_VERSION + " has successfully loaded.");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent evt) {
	    //register server commands
        evt.registerServerCommand(new CommandEnterDimension());
    }
}
