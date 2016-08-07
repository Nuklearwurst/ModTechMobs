package com.fravokados.dangertech.portals.item;

import com.fravokados.dangertech.core.item.ItemNW;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.lib.Reference;

public class ItemMD extends ItemNW {

	public ItemMD(String registryName) {
		super(Reference.MOD_ID, registryName, ModMiningDimension.TAB_MD);
	}
}
