package com.fravokados.dangertech.core.plugin.ic2;

import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import ic2.api.item.IC2Items;
import ic2.api.item.IItemAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 *
 */
public class IC2Plugin {

	private static Item wrench;
	private static Item electricWrench;

	public static boolean init() {
		IItemAPI itemAPI = IC2Items.getItemAPI();

		if(itemAPI != null) {
			wrench = itemAPI.getItem("wrench");
			electricWrench = itemAPI.getItem("electric_wrench");
			return true;
		}
		LogHelperCore.error("IC2 Item-Integration errored!");
		return false;
	}

	public static boolean isItemWrench(ItemStack stack) {
		return wrench == stack.getItem() || electricWrench == stack.getItem();
	}

}
