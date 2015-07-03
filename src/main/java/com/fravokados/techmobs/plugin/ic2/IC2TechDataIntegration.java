package com.fravokados.techmobs.plugin.ic2;

import com.fravokados.techmobs.api.DangerousTechnologyAPI;
import com.fravokados.techmobs.api.techdata.effects.mob.TDMobEffectEquipment;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.plugin.PluginManager;
import com.fravokados.techmobs.techdata.values.TDValues;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class IC2TechDataIntegration {

	public static ItemStack itemQuantumHelmet;
	public static ItemStack itemQuantumArmor;
	public static ItemStack itemQuantumLeggins;
	public static ItemStack itemQuantumBoots;
	public static ItemStack itemNanoSaber;
	public static ItemStack itemChainsaw;

	public static void init() {
		if (!PluginManager.ic2Activated()) {
			return;
		}
		//TODO make values configurable
		LogHelper.info("Loading IC2 TechData values...");
		loadTileEntityValues();
		loadItemValues();
		loadEffects();
	}

	private static void loadItems() {
		itemNanoSaber = IC2Items.getItem("nanoSaber");

		itemQuantumHelmet = IC2Items.getItem("quantumHelmet");
		itemQuantumArmor = IC2Items.getItem("quantumBodyarmor");
		itemQuantumLeggins = IC2Items.getItem("quantumLeggings");
		itemQuantumBoots = IC2Items.getItem("quantumBoots");
		itemChainsaw = IC2Items.getItem("chainsaw");
	}

	@SuppressWarnings("unchecked")
	public static void loadTileEntityValues() {
		//////////
		// Beam //
		//////////

		///////////////
		// Generator //
		///////////////
		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntitySolarGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntitySolarGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityGeoGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityGeoGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityRTGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 100);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityRTGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityWindGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityWindGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityWaterGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityWaterGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityKineticGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityKineticGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntitySemiFluidGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 60);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntitySemiFluidGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityStirlingGenerator");
			TDValues.getInstance().registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityStirlingGenerator!");
		}

		////////////////////
		// Heat Generator //
		////////////////////

		///////////////////////
		// Kinetic Generator //
		///////////////////////

		/////////////
		// Machine //
		/////////////

		//////////////
		// Personal //
		//////////////

		/////////////
		// Reactor //
		/////////////

		////////////
		// Wiring //
		////////////
	}

	public static void loadItemValues() {

		////////////////////
		// Electric Tools //
		////////////////////
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("miningDrill"), 15);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("diamondDrill"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("iridiumDrill"), 200);
		TDValues.getInstance().registerItemEntry(itemChainsaw, 10);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricWrench"), 3);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricTreetap"), 4);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("miningLaser"), 100);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("ecMeter"), 8);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("odScanner"), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("ovScanner"), 35);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("obscurator"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("frequencyTransmitter"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoSaber"), 100);
		TDValues.getInstance().registerItemEntry(itemNanoSaber, 120);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("toolbox"), 5);

		///////////
		// Armor //
		///////////

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatHelmet"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatChestplate"), 40);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatLeggings"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatBoots"), 25);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("compositeArmor"), 40);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoHelmet"), 100);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoBodyarmor"), 300);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoLeggings"), 150);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoBoots"), 100);

		TDValues.getInstance().registerItemEntry(itemQuantumHelmet, 300);
		TDValues.getInstance().registerItemEntry(itemQuantumArmor, 600);
		TDValues.getInstance().registerItemEntry(itemQuantumLeggins, 400);
		TDValues.getInstance().registerItemEntry(itemQuantumBoots, 300);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("jetpack"), 40);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricJetpack"), 60);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("batPack"), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("advbatPack"), 40);
//		TDValues.getInstance().registerItemEntry(IC2Items.getItem("lapPack"), 70); legacy
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("energyPack"), 100);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("cfPack"), 16);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("solarHelmet"), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("staticBoots"), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nightvisionGoggles"), 50);
	}

	private static void loadEffects() {
		DangerousTechnologyAPI.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				itemNanoSaber,
				itemQuantumHelmet,
				itemQuantumArmor,
				itemQuantumLeggins,
				itemQuantumBoots
		}, false, false, new int[]{4000, 1000, 4000, 2000, 2000}).setDoesArmorDrop(false).checkContents());


		DangerousTechnologyAPI.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				itemChainsaw,
				IC2Items.getItem("nanoHelmet"),
				IC2Items.getItem("nanoBodyarmor"),
				IC2Items.getItem("nanoLeggings"),
				IC2Items.getItem("nanoBoots")
		}, false, false, new int[]{2000, 500, 2000, 1000, 1000}).setDoesArmorDrop(false).checkContents());
	}
}
