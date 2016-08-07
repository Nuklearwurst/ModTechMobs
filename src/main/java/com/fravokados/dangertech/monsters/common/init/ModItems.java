package com.fravokados.dangertech.monsters.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.item.ItemNWSword;
import com.fravokados.dangertech.monsters.item.*;
import com.fravokados.dangertech.monsters.lib.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	///////////////////
	// General Items //
	///////////////////
	public static final ItemTM item_cot = null;

	////////////////////
	// TechData Items //
	////////////////////
	public static final ItemTM monsterDetector = null;
	public static final ItemTM tdAnalyzer = null;
	public static final ItemTM conservationUnit = null;
	//Weapons
	public static final ItemNWSword quantumSword = null;

	///////////////
	// Resources //
	///////////////
	public static final ItemTM monsterDrop = null;

	/////////////////
	// Debug Items //
	/////////////////
	public static final ItemTM tdDebugger = null;


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
