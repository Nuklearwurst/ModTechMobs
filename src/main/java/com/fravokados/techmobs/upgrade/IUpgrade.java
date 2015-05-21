package com.fravokados.techmobs.upgrade;

import net.minecraft.item.ItemStack;

/**
 * implemented in items that are upgrades
 * @author Nuklearwurst
 */
public interface IUpgrade {


	public IUpgradeDefinition getUpgradeDefinition(ItemStack item);
}
