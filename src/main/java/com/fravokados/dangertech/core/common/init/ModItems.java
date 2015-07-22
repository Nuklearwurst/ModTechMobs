package com.fravokados.dangertech.core.common.init;

import com.fravokados.dangertech.core.item.ItemUpgradeTool;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Nuklearwurst
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	public static final ItemUpgradeTool upgradeTool = new ItemUpgradeTool();

	public static void registerItems() {
		GameRegistry.registerItem(upgradeTool, Strings.Item.UPGRADE_TOOL);
	}
}
