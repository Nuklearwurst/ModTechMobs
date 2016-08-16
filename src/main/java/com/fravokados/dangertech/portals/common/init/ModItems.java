package com.fravokados.dangertech.portals.common.init;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.item.ItemMD;
import com.fravokados.dangertech.portals.item.ItemMDDebugger;
import com.fravokados.dangertech.portals.item.ItemMindDimUpgrade;
import com.fravokados.dangertech.portals.lib.Reference;
import com.fravokados.dangertech.portals.lib.Strings;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	@GameRegistry.ObjectHolder(Strings.Item.destinationCard)
	public static final ItemMD itemDestinationCard = null;

	@GameRegistry.ObjectHolder(Strings.Item.upgrade)
	public static final ItemMD itemUpgrade = null;

	@GameRegistry.ObjectHolder(Strings.Item.debugger)
	public static final ItemMD itemDebugger = null;

	public static void registerItems() {
		registerItem(new ItemDestinationCard());
		registerItem(new ItemMindDimUpgrade());
		registerItem(new ItemMDDebugger());
	}

	private static void registerItem(ItemMD item) {
		ModNwCore.proxy.registerItem(item);
	}
}
