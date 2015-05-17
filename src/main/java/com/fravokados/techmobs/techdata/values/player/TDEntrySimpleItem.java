package com.fravokados.techmobs.techdata.values.player;

import net.minecraft.item.ItemStack;

public class TDEntrySimpleItem extends TDEntryItem {
	
	private final int meta;
	private final int value;
	
	/**
	 * @param meta when this is negative, the item damage is ignored
	 * @param value
	 */
	public TDEntrySimpleItem(int meta, int value) {
		this.meta = meta;
		this.value = value;
	}
	
	/**
	 * Constructor for items where item damage should be ignored
	 * @param value
	 */
	public TDEntrySimpleItem(int value) {
		this(-1, value);
	}

	@Override
	public int getTechLevelForItem(ItemStack item) {
		if(meta < 0 || meta == item.getItemDamage()) {
			return value;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Simple ItemTDEntry:" + value;
	}

}
