package com.fravokados.dangertech.core.lib.util;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public class ModelUtils {

	@SideOnly(Side.CLIENT)
	public static void registerModelVariant(Item item, int meta, String modId, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modId + ":" + variant, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModelVariant(Item item, int meta, String fullVariantName) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(fullVariantName, "inventory"));
	}
}
