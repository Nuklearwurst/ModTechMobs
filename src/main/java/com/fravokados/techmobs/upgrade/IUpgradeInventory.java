package com.fravokados.techmobs.upgrade;

import net.minecraft.inventory.IInventory;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public interface IUpgradeInventory extends IInventory {

	public List<IUpgradeDefinition> getUpgrades();
}
