package com.fravokados.techmobs.plugin.ic2;

import com.fravokados.techmobs.plugin.PluginManager;
import com.fravokados.techmobs.upgrade.IUpgradeDefinition;
import com.fravokados.techmobs.upgrade.SimpleIntegerUpgrade;
import com.fravokados.techmobs.upgrade.UpgradeTypes;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

/**
 * @author Nuklearwurst
 */
public class IC2UpgradeIntegration {

	private static ItemStack upgradeEnergyStorage;
	private static ItemStack upgradeEnergyTier;
	private static ItemStack upgradeOverclocker;
	private static ItemStack upgradeEjector;

	public static void init() {
		if(PluginManager.ic2Activated()) {
			upgradeEnergyStorage = IC2Items.getItem("energyStorageUpgrade");
			upgradeEnergyTier = IC2Items.getItem("transformerUpgrade");
			upgradeOverclocker = IC2Items.getItem("overclockerUpgrade");
			upgradeEjector = IC2Items.getItem("ejectorUpgrade");
		}
	}


	public static boolean isUpgrade(ItemStack item) {
		return getUpgrade(item) != null;
	}

	public static IUpgradeDefinition getUpgrade(ItemStack item) {
		if(PluginManager.ic2Activated()) {
			if(item != null) {
				if(upgradeEjector != null && item.isItemEqual(upgradeEjector)) {
					//TODO: Ejector Upgrade
				} else if(upgradeOverclocker != null && item.isItemEqual(upgradeOverclocker)) {
					//TODO: overclocker upgrade
				} else if(upgradeEnergyTier != null && item.isItemEqual(upgradeEnergyTier)) {
					return new SimpleIntegerUpgrade(item.stackSize, 1, UpgradeTypes.ENERGY_TIER.id);
				} else if(upgradeEnergyStorage != null && item.isItemEqual(upgradeEnergyStorage)) {
					return new SimpleIntegerUpgrade(item.stackSize, 10000, UpgradeTypes.ENERGY_STORAGE.id);
				}
			}
		}
		return null;
	}
}
