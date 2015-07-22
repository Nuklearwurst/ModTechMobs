package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.techmobs.item.*;
import com.fravokados.dangertech.techmobs.lib.Reference;
import com.fravokados.dangertech.techmobs.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	///////////////////
	// General Items //
	///////////////////
	public static final ItemTM item_cot = new ItemCot();

	////////////////////
	// TechData Items //
	////////////////////
	public static final ItemTM monsterDetector = new ItemMonsterDetector();
	public static final ItemTM tdAnalyzer = new ItemTDAnalyzer();
	public static final ItemTM conservationUnit = new ItemConservationUnit();
	//Weapons
	public static final ItemTMSword quantumSword = new ItemQuantumSword();

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
		GameRegistry.registerItem(item_cot, Strings.Item.COT);

		////////////////////
		// TechData Items //
		////////////////////
		GameRegistry.registerItem(monsterDetector, Strings.Item.MONSTER_DETECTOR);
		GameRegistry.registerItem(tdAnalyzer, Strings.Item.TD_ANALYZER);
		GameRegistry.registerItem(conservationUnit, Strings.Item.CONSERVATION_UNIT);
		//Weapons
		GameRegistry.registerItem(quantumSword, Strings.Item.QUANTUM_SWORD);

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
