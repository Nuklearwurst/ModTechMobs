package com.fravokados.dangertech.monsters;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.monsters.command.CommandTechData;
import com.fravokados.dangertech.monsters.command.CommandTechMobs;
import com.fravokados.dangertech.monsters.command.CommandTechPlayer;
import com.fravokados.dangertech.monsters.common.CommonProxy;
import com.fravokados.dangertech.monsters.common.SleepingManager;
import com.fravokados.dangertech.monsters.common.handler.GuiHandler;
import com.fravokados.dangertech.monsters.common.init.*;
import com.fravokados.dangertech.monsters.configuration.ConfigHandler;
import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import com.fravokados.dangertech.monsters.network.ModTDNetworkManager;
import com.fravokados.dangertech.monsters.plugin.PluginManager;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffects;
import com.fravokados.dangertech.monsters.techdata.values.TDValues;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod(modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY,
		dependencies = Reference.DEPENDENCIES)
@Mod.EventBusSubscriber
public class ModTechMobs {

	@Mod.Instance(value = Reference.MOD_ID)
	public static ModTechMobs instance;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;

	public static CreativeTabs TAB_TM;

	public static ConfigHandler config;

	public static String[] cTileValues;
	public static String[] cItemValues;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		//load config
		config = new ConfigHandler(evt.getSuggestedConfigurationFile());
		config.load(true);


		//register EventHandlers

		//init networking

		//init keybindings

		//init API
		DangerousTechnologyAPI.effectRegistry = new TDEffects();
		DangerousTechnologyAPI.valueRegistry = new TDValues();
		DangerousTechnologyAPI.damageSourceEMP = new DamageSource(Strings.DAMAGE_SOURCE_EMP).setDamageBypassesArmor();

		//init rendering
		proxy.registerRenderer();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		//init modintegration
		PluginManager.init();

		//register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		//load Entities
		ModEntities.init();

		//load recipes
		ModRecipes.init();

		//init networking
		ModTDNetworkManager.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		//init TD effects
		TDEffects.init();
		TDValues.init();

		for (String s : cTileValues) {
			String[] s1 = s.split(":");
			if (s1.length == 2) {
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

		for (String s : cItemValues) {
			String[] s1 = s.split(":");
			if (s1.length < 5) {
				try {
					Item item = GameRegistry.findItem(s1[0], s1[1]);
					if (item == null) {
						LogHelperTM.warn("Custom Item not found! Please check configs! (" + s + ")");
						continue;
					}
					if (s1.length == 4) {
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

	private static void initCreativeTab() {
		if(TAB_TM != null) return;
		TAB_TM = new CreativeTabs(Strings.CREATIVE_TAB) {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				//noinspection ConstantConditions
				return ModItems.monsterDetector;
			}
		};
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		//register events
		evt.registerServerCommand(new CommandTechMobs());
		evt.registerServerCommand(new CommandTechData());
		evt.registerServerCommand(new CommandTechPlayer());
	}

	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent evt) {
		//make sure sleeping data does not get carried over
		SleepingManager.clear();
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		initCreativeTab();
		ModItems.registerItems();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		initCreativeTab();
		ModBlocks.registerBlocks();
		ModBlocks.registerTileEntities();
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		ModSounds.registerSounds();
	}
}
