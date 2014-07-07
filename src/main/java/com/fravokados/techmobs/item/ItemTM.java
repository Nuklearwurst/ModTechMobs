package com.fravokados.techmobs.item;

import com.fravokados.techmobs.creativetab.CreativeTabTechMobs;
import com.fravokados.techmobs.lib.Textures;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTM extends Item {

	public ItemTM() {
		super();
		this.setCreativeTab(CreativeTabTechMobs.TAB_TM);
	}
	
	public ItemTM(String name) {
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
		//TODO having modid in unlocalized name
		itemIcon = reg.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
	}
}
