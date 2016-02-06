package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.core.item.ItemNWSword;
import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.lib.Reference;

/**
 * @author Nuklearwurst
 */
public class ItemTMSword extends ItemNWSword {

	public ItemTMSword(ToolMaterial material, String registryName) {
		super(material, Reference.MOD_ID, registryName, ModTechMobs.TAB_TM);
	}
}
