package com.fravokados.dangertech.api.core.upgrade;

import net.minecraft.inventory.IInventory;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public interface IUpgradeInventory extends IInventory {

	List<IUpgradeDefinition> getUpgrades();
}
