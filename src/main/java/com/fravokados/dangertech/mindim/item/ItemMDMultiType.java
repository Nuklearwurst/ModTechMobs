package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.mindim.lib.Textures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public abstract class ItemMDMultiType extends ItemMD {
	public ItemMDMultiType(String registryName) {
		super(registryName);
		this.setHasSubtypes(true);
	}

	@Override
	public abstract void getSubItems(Item item, CreativeTabs tabs, List list);

	@Override
	public abstract void registerModels();


	@Override
	public String getUnlocalizedName(ItemStack s) {
		String name = getNameForItemStack(s);
		if(name.startsWith("item.")) {
			return name;
		}
		return "item." + Textures.TEXTURE_PREFIX + name;
	}

	/**
	 * @return unlocalized name for the given itemstack with out any prefixes
	 */
	protected abstract String getNameForItemStack(ItemStack s);
}
