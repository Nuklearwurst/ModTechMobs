package com.fravokados.dangertech.monsters.plugin.ic2;

import com.fravokados.dangertech.api.monsters.techdata.TechdataRegistries;
import com.fravokados.dangertech.api.monsters.techdata.effects.mob.TDMobEffectEquipment;
import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import com.fravokados.dangertech.monsters.plugin.PluginManager;
import com.fravokados.dangertech.monsters.techdata.values.TDValues;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class IC2TechDataIntegration {

	public static void init() {
		if (!PluginManager.ic2Activated()) {
			return;
		}
		//TODO make values configurable
		LogHelperTM.info("Loading IC2 TechData values...");
		loadTileEntityValues();
		loadItemValues();
		loadEffects();
	}

	private static void loadTileEntityValues() {

		///////////////
		// Generator //
		///////////////

		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityGenerator", 10);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntitySolarGenerator", 40);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityGeoGenerator", 40);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityRTGenerator", 100);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityWindGenerator", 10);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityWaterGenerator", 10);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityKineticGenerator", 40);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator", 60);
		registerTileIC2("ic2.core.block.generator.tileentity.TileEntityStirlingGenerator", 40);

		////////////////////
		// Heat Generator //
		////////////////////

		registerTileIC2("ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator", 20);
		registerTileIC2("ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator", 10);
		registerTileIC2("ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator", 40);
		registerTileIC2("ic2.core.block.heatgenerator.tileentity.TileEntitySolidHeatGenerator", 30);

		///////////////////////
		// Kinetic Generator //
		///////////////////////

		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator", 20);
		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntityManualKineticGenerator", 5);
		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator", 20);
		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator", 30);
		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator", 10);
		registerTileIC2("ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator", 5);

		/////////////
		// Machine //
		/////////////


		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityAdvMiner", 40);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityBlastFurnace", 40);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityBlockCutter", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCanner", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCentrifuge", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCompressor", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCondenser", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCropHarvester", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityCropmatron", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityElectricFurnace", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityElectrolyzer", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityExtractor", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityFermenter", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityFluidBottler", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityFluidDistributor", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityFluidRegulator", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityInduction", 40);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityIronFurnace", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityItemBuffer", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger", 30);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityMacerator", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityMagnetizer", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityMatter", 140);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityMiner", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityNuke", 200);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityOreWashing", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityPatternStorage", 40);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityPump", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityRecycler", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityReplicator", 100);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityScanner", 100);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntitySolarDestiller", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntitySolidCanner", 5);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntitySortingMachine", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntitySteamGenerator", 20);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityTank", 10);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityTeleporter", 120);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityTerra", 80);
		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityTesla", 50);

		registerTileIC2("ic2.core.block.machine.tileentity.TileEntityChunkloader", 30);

		//////////////
		// Personal //
		//////////////

		/////////////
		// Reactor //
		/////////////

		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric", 80);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityReactorAccessHatch", 40);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric", 30);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityReactorFluidPort", 30);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityReactorRedstonePort", 20);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityReactorVessel", 20);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityRCI_LZH", 40);
		registerTileIC2("ic2.core.block.reactor.tileentity.TileEntityRCI_RSH", 40);

		////////////
		// Wiring //
		////////////

		registerTileIC2("ic2.core.block.wiring.TileEntityElectricBatBox", 30);
		registerTileIC2("ic2.core.block.wiring.TileEntityElectricCESU", 40);
		registerTileIC2("ic2.core.block.wiring.TileEntityElectricMFE", 50);
		registerTileIC2("ic2.core.block.wiring.TileEntityElectricMFSU", 60);

		registerTileIC2("ic2.core.block.wiring.TileEntityChargepadBatBox", 35);
		registerTileIC2("ic2.core.block.wiring.TileEntityChargepadCESU", 45);
		registerTileIC2("ic2.core.block.wiring.TileEntityChargepadMFE", 55);
		registerTileIC2("ic2.core.block.wiring.TileEntityChargepadMFSU", 65);

		registerTileIC2("ic2.core.block.wiring.TileEntityTransformerEV", 50);
		registerTileIC2("ic2.core.block.wiring.TileEntityTransformerHV", 40);
		registerTileIC2("ic2.core.block.wiring.TileEntityTransformerMV", 30);
		registerTileIC2("ic2.core.block.wiring.TileEntityTransformerLV", 20);
	}


	@SuppressWarnings("unchecked")
	private static void registerTileIC2(String className, int value) {
		try {
			Class clazz = Class.forName(className);
			TDValues.getInstance().registerTileEntityEntry(clazz, value);
		} catch (ClassNotFoundException e) {
			LogHelperTM.warn(e, "IC2Integration: Could not load class: " + className);
		}
	}

	private static void loadItemValues() {

		////////////////////
		// Electric Tools //
		////////////////////

		registerItemIC2("drill", 15);
		registerItemIC2("diamond_drill", 30);
		registerItemIC2("iridium_drill", 200);
		registerItemIC2("chainsaw", 10);
		registerItemIC2("electric_wrench", 3);
		registerItemIC2("electric_treetap", 4);
		registerItemIC2("mining_laser", 100);
		registerItemIC2("meter", 8);
		registerItemIC2("scanner", 20);
		registerItemIC2("advanced_scanner", 35);
		registerItemIC2("obscurator", 30);
		registerItemIC2("frequency_transmitter", 30);
		registerItemIC2("nano_saber", 120);
		registerItemIC2("tool_box", 5);

		///////////
		// Armor //
		///////////

		registerItemIC2("hazmat_helmet", 30);
		registerItemIC2("hazmat_chestplate", 40);
		registerItemIC2("hazmat_leggings", 30);

		registerItemIC2("alloy_chestplate", 40);

		registerItemIC2("nano_helmet", 100);
		registerItemIC2("nano_chestplate", 300);
		registerItemIC2("nano_leggings", 150);
		registerItemIC2("nano_boots", 100);

		registerItemIC2("quantum_helmet", 300);
		registerItemIC2("quantum_chestplate", 600);
		registerItemIC2("quantum_leggings", 400);
		registerItemIC2("quantum_boots", 300);

		registerItemIC2("jetpack", 40);
		registerItemIC2("jetpack_electric", 60);

		registerItemIC2("batpack", 20);
		registerItemIC2("advanced_batpack", 40);
		registerItemIC2("lappack", 70);
		registerItemIC2("energy_pack", 100);

		registerItemIC2("cf_pack", 16);
		registerItemIC2("solar_helmet", 30);
		registerItemIC2("static_boots", 20);
		registerItemIC2("nightvision_goggles", 50);

	}

	private static void register(@Nullable ItemStack stack, int value) {
		if(stack == null) {
			LogHelperTM.error("Could not register item value! Item cannot be null!");
		} else {
			TDValues.getInstance().registerItemEntry(stack, value);
		}
	}

	private static void registerItemIC2(String name, int value) {
		ItemStack stack = IC2Items.getItem(name);
		if(stack == null) {
			LogHelperTM.error("Could not register ic2 item value! Item not found: " + name);
		} else {
			TDValues.getInstance().registerItemEntry(stack, value);
		}
	}


	private static void registerItemIC2(String name, String variant, int value) {
		ItemStack stack = IC2Items.getItem(name, variant);
		if(stack == null) {
			LogHelperTM.error("Could not register ic2 item value! Item not found: " + name + ", " + variant);
		} else {
			TDValues.getInstance().registerItemEntry(stack, value);
		}
	}

	private static void loadEffects() {

		TechdataRegistries.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				IC2Items.getItem("nano_saber"),
				null,
				IC2Items.getItem("quantum_helmet"),
				IC2Items.getItem("quantum_chestplate"),
				IC2Items.getItem("quantum_leggings"),
				IC2Items.getItem("quantum_boots")
		}, false, false, new int[]{4000, 0, 1000, 4000, 2000, 2000}).setDoesArmorDrop(false).checkContents());


		TechdataRegistries.effectRegistry.addMobEffect(new TDMobEffectEquipment(new ItemStack[]{
				IC2Items.getItem("chainsaw"),
				null,
				IC2Items.getItem("nano_helmet"),
				IC2Items.getItem("nano_chestplate"),
				IC2Items.getItem("nano_leggings"),
				IC2Items.getItem("nano_boots")
		}, false, false, new int[]{2000, 0, 500, 2000, 1000, 1000}).setDoesArmorDrop(false).checkContents());


	}
}
