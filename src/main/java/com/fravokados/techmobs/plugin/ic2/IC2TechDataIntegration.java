package com.fravokados.techmobs.plugin.ic2;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.plugin.PluginManager;
import com.fravokados.techmobs.techdata.values.TDValues;
import ic2.api.item.IC2Items;

/**
 * @author Nuklearwurst
 */
public class IC2TechDataIntegration {

	public static void init() {
		if (!PluginManager.ic2Activated()) {
			return;
		}
		//TODO make values configurable
		LogHelper.info("Loading IC2 TechData values...");
		loadTileEntityValues();
		loadItemValues();
	}

	public static void loadTileEntityValues() {
		//////////
		// Beam //
		//////////

		///////////////
		// Generator //
		///////////////
		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityGenerator");
			TDValues.registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntitySolarGenerator");
			TDValues.registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntitySolarGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityGeoGenerator");
			TDValues.registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityGeoGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityRTGenerator");
			TDValues.registerTileEntityEntry(clazz, 100);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityRTGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityWindGenerator");
			TDValues.registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityWindGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityWaterGenerator");
			TDValues.registerTileEntityEntry(clazz, 10);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityWaterGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityKineticGenerator");
			TDValues.registerTileEntityEntry(clazz, 40);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntityKineticGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntitySemiFluidGenerator");
			TDValues.registerTileEntityEntry(clazz, 60);
		} catch (ClassNotFoundException e) {
			LogHelper.warn(e, "IC2Integration: Could not load TileEntitySemiFluidGenerator!");
		}

		try {
			Class clazz = Class.forName("ic2.core.block.generator.tileentity.TileEntityStirlingGenerator");
			TDValues.registerTileEntityEntry(clazz, 40);
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

		TDValues.registerItemEntry(IC2Items.getItem("miningDrill").getItem(), 15);
		TDValues.registerItemEntry(IC2Items.getItem("diamondDrill").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("iridiumDrill").getItem(), 200);
		TDValues.registerItemEntry(IC2Items.getItem("chainsaw").getItem(), 10);
		TDValues.registerItemEntry(IC2Items.getItem("electricWrench").getItem(), 3);
		TDValues.registerItemEntry(IC2Items.getItem("electricTreetap").getItem(), 4);
		TDValues.registerItemEntry(IC2Items.getItem("miningLaser").getItem(), 100);
		TDValues.registerItemEntry(IC2Items.getItem("ecMeter").getItem(), 8);
		TDValues.registerItemEntry(IC2Items.getItem("odScanner").getItem(), 20);
		TDValues.registerItemEntry(IC2Items.getItem("ovScanner").getItem(), 35);
		TDValues.registerItemEntry(IC2Items.getItem("obscurator").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("frequencyTransmitter").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("nanoSaber").getItem(), 100);
		TDValues.registerItemEntry(IC2Items.getItem("enabledNanoSaber").getItem(), 120);
		TDValues.registerItemEntry(IC2Items.getItem("toolbox").getItem(), 5);

		///////////
		// Armor //
		///////////

		TDValues.registerItemEntry(IC2Items.getItem("hazmatHelmet").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("hazmatChestplate").getItem(), 40);
		TDValues.registerItemEntry(IC2Items.getItem("hazmatLeggings").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("hazmatBoots").getItem(), 25);

		TDValues.registerItemEntry(IC2Items.getItem("compositeArmor").getItem(), 40);

		TDValues.registerItemEntry(IC2Items.getItem("nanoHelmet").getItem(), 100);
		TDValues.registerItemEntry(IC2Items.getItem("nanoBodyarmor").getItem(), 300);
		TDValues.registerItemEntry(IC2Items.getItem("nanoLeggings").getItem(), 150);
		TDValues.registerItemEntry(IC2Items.getItem("nanoBoots").getItem(), 100);

		TDValues.registerItemEntry(IC2Items.getItem("quantumHelmet").getItem(), 300);
		TDValues.registerItemEntry(IC2Items.getItem("quantumBodyarmor").getItem(), 600);
		TDValues.registerItemEntry(IC2Items.getItem("quantumLeggings").getItem(), 400);
		TDValues.registerItemEntry(IC2Items.getItem("quantumBoots").getItem(), 300);

		TDValues.registerItemEntry(IC2Items.getItem("jetpack").getItem(), 40);
		TDValues.registerItemEntry(IC2Items.getItem("electricJetpack").getItem(), 60);

		TDValues.registerItemEntry(IC2Items.getItem("batPack").getItem(), 20);
		TDValues.registerItemEntry(IC2Items.getItem("advbatPack").getItem(), 40);
		TDValues.registerItemEntry(IC2Items.getItem("lapPack").getItem(), 70);
		TDValues.registerItemEntry(IC2Items.getItem("energyPack").getItem(), 120);

		TDValues.registerItemEntry(IC2Items.getItem("cfPack").getItem(), 16);
		TDValues.registerItemEntry(IC2Items.getItem("solarHelmet").getItem(), 30);
		TDValues.registerItemEntry(IC2Items.getItem("staticBoots").getItem(), 20);
		TDValues.registerItemEntry(IC2Items.getItem("nightvisionGoggles").getItem(), 50);
	}
}
