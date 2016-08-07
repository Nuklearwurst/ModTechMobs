package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.core.item.ItemNWSword;
import com.fravokados.dangertech.monsters.ModTechMobs;
import com.fravokados.dangertech.monsters.lib.Reference;

/**
 * @author Nuklearwurst
 */
public class ItemTMSword extends ItemNWSword {

	public ItemTMSword(ToolMaterial material, String registryName) {
		super(material, Reference.MOD_ID, registryName, ModTechMobs.TAB_TM);
	}
}
