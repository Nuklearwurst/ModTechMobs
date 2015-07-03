package com.fravokados.techmobs.common;

import com.fravokados.techmobs.item.*;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	///////////////////
	// General Items //
	///////////////////
	public static final ItemTM upgradeTool = new ItemUpgradeTool();
	public static final ItemTM item_cot = new ItemCot();

	////////////////////
	// TechData Items //
	////////////////////
	public static final ItemTM monsterDetector = new ItemMonsterDetector();
	public static final ItemTM tdAnalyzer = new ItemTDAnalyzer();

	///////////////
	// Resources //
	///////////////
	public static final ItemTM monsterDrop = new ItemMonsterDrop();

	/////////////////
	// Debug Items //
	/////////////////
	public static final ItemTM tdDebugger = new ItemTDDebugger();


	public static void registerItems() {
		///////////////////
		// General Items //
		///////////////////
		GameRegistry.registerItem(upgradeTool, Strings.Item.UPGRADE_TOOL);
		GameRegistry.registerItem(item_cot, Strings.Item.COT);

		////////////////////
		// TechData Items //
		////////////////////
		GameRegistry.registerItem(monsterDetector, Strings.Item.MONSTER_DETECTOR);
		GameRegistry.registerItem(tdAnalyzer, Strings.Item.TD_ANALYZER);

		///////////////
		// Resources //
		///////////////
		GameRegistry.registerItem(monsterDrop, Strings.Item.MONSTER_DROP);

		/////////////////
		// Debug Items //
		/////////////////
		GameRegistry.registerItem(tdDebugger, Strings.Item.TD_DEBUGGER);
	}
}
