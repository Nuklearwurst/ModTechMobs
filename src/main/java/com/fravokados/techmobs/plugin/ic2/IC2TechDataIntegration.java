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

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("miningDrill").getItem(), 15);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("diamondDrill").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("iridiumDrill").getItem(), 200);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("chainsaw").getItem(), 10);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricWrench").getItem(), 3);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricTreetap").getItem(), 4);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("miningLaser").getItem(), 100);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("ecMeter").getItem(), 8);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("odScanner").getItem(), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("ovScanner").getItem(), 35);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("obscurator").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("frequencyTransmitter").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoSaber").getItem(), 100);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("enabledNanoSaber").getItem(), 120);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("toolbox").getItem(), 5);

		///////////
		// Armor //
		///////////

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatHelmet").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatChestplate").getItem(), 40);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatLeggings").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("hazmatBoots").getItem(), 25);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("compositeArmor").getItem(), 40);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoHelmet").getItem(), 100);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoBodyarmor").getItem(), 300);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoLeggings").getItem(), 150);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nanoBoots").getItem(), 100);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("quantumHelmet").getItem(), 300);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("quantumBodyarmor").getItem(), 600);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("quantumLeggings").getItem(), 400);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("quantumBoots").getItem(), 300);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("jetpack").getItem(), 40);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("electricJetpack").getItem(), 60);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("batPack").getItem(), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("advbatPack").getItem(), 40);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("lapPack").getItem(), 70);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("energyPack").getItem(), 120);

		TDValues.getInstance().registerItemEntry(IC2Items.getItem("cfPack").getItem(), 16);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("solarHelmet").getItem(), 30);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("staticBoots").getItem(), 20);
		TDValues.getInstance().registerItemEntry(IC2Items.getItem("nightvisionGoggles").getItem(), 50);
	}

	private static void loadEffects() {
		DangerousTechnologyAPI.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				IC2Items.getItem("enabledNanoSaber"),
				IC2Items.getItem("quantumHelmet"),
				IC2Items.getItem("quantumBodyarmor"),
				IC2Items.getItem("quantumLeggings"),
				IC2Items.getItem("quantumBoots")
		}, false, false, new int[]{4000, 1000, 4000, 2000, 2000}).setDoesArmorDrop(false));


		DangerousTechnologyAPI.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				IC2Items.getItem("chainsaw"),
				IC2Items.getItem("nanoHelmet"),
				IC2Items.getItem("nanoBodyarmor"),
				IC2Items.getItem("nanoLeggings"),
				IC2Items.getItem("nanoBoots")
		}, false, false, new int[]{2000, 500, 2000, 1000, 1000}).setDoesArmorDrop(false));
	}
}
