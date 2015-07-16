package com.fravokados.mindim.item;

import com.fravokados.mindim.ModMiningDimension;
import com.fravokados.mindim.lib.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMD extends Item {

	public ItemMD() {
		super();
		this.setCreativeTab(ModMiningDimension.TAB_MD);
	}

	public ItemMD(String name) {
		this();
		this.setUnlocalizedName(name);
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
	
	@Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
	@Override
    public String getUnlocalizedName(ItemStack s)
    {
        return String.format("item.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
	}
}
