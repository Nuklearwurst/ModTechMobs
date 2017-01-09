package com.fravokados.dangertech.monsters.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.item.ItemNWSword;
import com.fravokados.dangertech.monsters.item.*;
import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	///////////////////
	// General Items //
	///////////////////
	@GameRegistry.ObjectHolder(Strings.Item.COT)
	public static ItemTM item_cot = null;

	////////////////////
	// TechData Items //
	////////////////////
	@GameRegistry.ObjectHolder(Strings.Item.MONSTER_DETECTOR)
	public static ItemTM monsterDetector = null;
	@GameRegistry.ObjectHolder(Strings.Item.TD_ANALYZER)
	public static ItemTM tdAnalyzer = null;
	@GameRegistry.ObjectHolder(Strings.Item.CONSERVATION_UNIT)
	public static ItemTM conservationUnit = null;
	//Weapons
	@GameRegistry.ObjectHolder(Strings.Item.QUANTUM_SWORD)
	public static ItemNWSword quantumSword = null;
	@GameRegistry.ObjectHolder(Strings.Item.TECHDATA_SWORD)
	public static ItemNWSword techdataSword = null;

	///////////////
	// Resources //
	///////////////
	@GameRegistry.ObjectHolder(Strings.Item.MONSTER_DROP)
	public static ItemTM monsterDrop = null;

	/////////////////
	// Debug Items //
	/////////////////
	@GameRegistry.ObjectHolder(Strings.Item.TD_DEBUGGER)
	public static ItemTM tdDebugger = null;


	public static void registerItems() {
		///////////////////
		// General Items //
		///////////////////

		////////////////////
		// TechData Items //
		////////////////////
		ModNwCore.proxy.registerItem(new ItemMonsterDetector());
		ModNwCore.proxy.registerItem(new ItemTDAnalyzer());
		ModNwCore.proxy.registerItem(new ItemConservationUnit());
		//Weapons
		ModNwCore.proxy.registerItem(new ItemQuantumSword());
		ModNwCore.proxy.registerItem(new ItemTechdataSword());

		///////////////
		// Resources //
		///////////////
		ModNwCore.proxy.registerItem(new ItemMonsterDrop());

		/////////////////
		// Debug Items //
		/////////////////
		ModNwCore.proxy.registerItem(new ItemTDDebugger());
	}

}
