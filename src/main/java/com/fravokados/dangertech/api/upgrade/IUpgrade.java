package com.fravokados.dangertech.api.upgrade;

import net.minecraft.item.ItemStack;

/**
 * implemented in items that are upgrades
 * @author Nuklearwurst
 */
public interface IUpgrade {


	IUpgradeDefinition getUpgradeDefinition(ItemStack item);
}
