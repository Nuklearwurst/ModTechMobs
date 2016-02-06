package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.mindim.lib.Reference;
import com.fravokados.dangertech.mindim.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	@GameRegistry.ObjectHolder(Strings.Item.destinationCard)
	public static final ItemMD itemDestinationCard = null;

	@GameRegistry.ObjectHolder(Strings.Item.upgrade)
	public static final ItemMD itemUpgrade = null;

	public static void registerItems() {
		registerItem(new ItemDestinationCard());
		registerItem(new ItemMindDimUpgrade());
	}

	private static void registerItem(ItemMD item) {
		ModNwCore.proxy.registerItem(item);
	}
}
