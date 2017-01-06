package com.fravokados.dangertech.api.monsters.techdata.values.player;

import net.minecraft.item.ItemStack;

/**
 * Item techdata handler
 * 
 * @author Nuklearwurst
 *
 */
public abstract class TDEntryItem {

	/**
	 * returns techdata for the given itemstack
	 */
	public abstract int getTechLevelForItem(ItemStack item);
}
