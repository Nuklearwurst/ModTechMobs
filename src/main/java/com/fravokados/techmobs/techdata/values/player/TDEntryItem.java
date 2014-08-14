package com.fravokados.techmobs.techdata.values.player;

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
	 * @param item
	 * @return
	 */
	public abstract int getTechLevelForItem(ItemStack item);
}
