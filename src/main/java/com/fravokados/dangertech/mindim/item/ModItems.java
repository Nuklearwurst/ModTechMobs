package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	@GameRegistry.ObjectHolder(Strings.Item.destinationCard)
	public static final ItemMD itemDestinationCard = new ItemDestinationCard();

	@GameRegistry.ObjectHolder(Strings.Item.upgrade)
	public static final ItemMD itemUpgrade = new ItemMindDimUpgrade();

	public static void registerItems() {
		GameRegistry.registerItem(ModItems.itemDestinationCard, Strings.Item.destinationCard);
		GameRegistry.registerItem(ModItems.itemUpgrade, Strings.Item.upgrade);
	}
}
