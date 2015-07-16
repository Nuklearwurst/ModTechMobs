package com.fravokados.mindim.item;

import com.fravokados.mindim.lib.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public abstract class ItemMDMultiType extends ItemMD {

	public ItemMDMultiType() {
		super();
		this.setHasSubtypes(true);
	}

	public ItemMDMultiType(String name) {
		super(name);
		this.setHasSubtypes(true);
	}

	@Override
	public abstract void getSubItems(Item item, CreativeTabs tabs, List list);

	@Override
	public String getUnlocalizedName(ItemStack s) {
		return String.format("item.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(getUnlocalizedNameForItem(s)));
	}

	protected abstract String getUnlocalizedNameForItem(ItemStack stack);

	@Override
	public abstract void registerIcons(IIconRegister reg);
}
