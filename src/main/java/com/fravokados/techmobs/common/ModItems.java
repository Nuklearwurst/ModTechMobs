package com.fravokados.techmobs.common;

import com.fravokados.techmobs.item.*;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	public static final ItemTM monsterDetector = new ItemMonsterDetector();
	public static final ItemTM monsterDrop = new ItemMonsterDrop();
	public static final ItemTM upgradeTool = new ItemUpgradeTool();
	public static final ItemTM item_cot = new ItemCot();
	
	
	public static void registerItems() {
		GameRegistry.registerItem(monsterDetector, Strings.Item.MONSTER_DETECTOR);
		GameRegistry.registerItem(monsterDrop, Strings.Item.MONSTER_DROP);
		GameRegistry.registerItem(upgradeTool, Strings.Item.UPGRADE_TOOL);
		GameRegistry.registerItem(item_cot, Strings.Item.COT);
	}
}
