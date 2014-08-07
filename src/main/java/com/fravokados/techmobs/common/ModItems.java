package com.fravokados.techmobs.common;

import com.fravokados.techmobs.item.ItemMonsterDetector;
import com.fravokados.techmobs.item.ItemMonsterDrop;
import com.fravokados.techmobs.item.ItemTM;
import com.fravokados.techmobs.lib.Reference;
import com.fravokados.techmobs.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(value = Reference.MOD_ID)
public class ModItems {

	public static final ItemTM monsterDetector = new ItemMonsterDetector();
	public static final ItemTM  monsterDrop = new ItemMonsterDrop();
	
	
	public static void init() {
		GameRegistry.registerItem(monsterDetector, Strings.Item.MONSTER_DETECTOR);
		GameRegistry.registerItem(monsterDrop, Strings.Item.MONSTER_DROP);
	}
}
