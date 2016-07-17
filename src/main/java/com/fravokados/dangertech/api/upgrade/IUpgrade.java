package com.fravokados.dangertech.api.upgrade;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * implemented in items that are upgrades
 * @author Nuklearwurst
 */
public interface IUpgrade {


	@Nullable
	IUpgradeDefinition getUpgradeDefinition(ItemStack item);
}
