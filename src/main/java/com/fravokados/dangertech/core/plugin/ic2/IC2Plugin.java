package com.fravokados.dangertech.core.plugin.ic2;

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

/**
 *
 */
public class IC2Plugin {

	private static ItemStack wrench;
	private static ItemStack electricWrench;

	public static void init() {
		wrench = IC2Items.getItem("wrench");
		electricWrench = IC2Items.getItem("electricWrench");
	}

	public static boolean isItemWrench(ItemStack stack) {
		return wrench.getItem() == stack.getItem() || electricWrench.getItem() == stack.getItem();
	}

}
