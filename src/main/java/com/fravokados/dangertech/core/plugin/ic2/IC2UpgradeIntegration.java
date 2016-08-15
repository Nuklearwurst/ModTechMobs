package com.fravokados.dangertech.core.plugin.ic2;

import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.SimpleIntegerUpgrade;
import com.fravokados.dangertech.api.upgrade.UpgradeTypes;
import com.fravokados.dangertech.core.plugin.PluginManager;
import ic2.api.info.Info;
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

	public static boolean init() {
		if(Info.isIc2Available()) {
			upgradeEnergyStorage = IC2Items.getItem("upgrade", "energy_storage");
			upgradeEnergyTier = IC2Items.getItem("upgrade", "transformer");
			upgradeOverclocker = IC2Items.getItem("upgrade", "overclocker");
			upgradeEjector = IC2Items.getItem("upgrade", "ejector");
			return true;
		}
		return false;
	}


	public static boolean isUpgrade(ItemStack item) {
		return getUpgrade(item) != null;
	}

	public static IUpgradeDefinition getUpgrade(ItemStack item) {
		if(PluginManager.ic2Activated()) {
			if(upgradeEjector != null && item.isItemEqual(upgradeEjector)) {
				//TODO: Ejector Upgrade
			} else if(upgradeOverclocker != null && item.isItemEqual(upgradeOverclocker)) {
				return new UpgradeOverclocker(item.stackSize);
			} else if(upgradeEnergyTier != null && item.isItemEqual(upgradeEnergyTier)) {
				return new SimpleIntegerUpgrade(item.stackSize, 1, UpgradeTypes.ENERGY_TIER.id);
			} else if(upgradeEnergyStorage != null && item.isItemEqual(upgradeEnergyStorage)) {
				return new SimpleIntegerUpgrade(item.stackSize, 10000, UpgradeTypes.ENERGY_STORAGE.id);
			}
		}
		return null;
	}
}
