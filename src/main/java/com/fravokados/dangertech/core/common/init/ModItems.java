package com.fravokados.dangertech.core.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.item.ItemUpgradeTool;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Nuklearwurst
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	@GameRegistry.ObjectHolder(Strings.Item.UPGRADE_TOOL)
	public static final ItemUpgradeTool upgradeTool = null;

	public static void registerItems() {
		ModNwCore.proxy.registerItem(new ItemUpgradeTool());
	}
}
