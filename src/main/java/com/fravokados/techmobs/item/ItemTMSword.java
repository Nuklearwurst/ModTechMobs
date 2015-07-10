package com.fravokados.techmobs.item;

import com.fravokados.techmobs.ModTechMobs;
import com.fravokados.techmobs.lib.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

/**
 * @author Nuklearwurst
 */
public class ItemTMSword extends ItemSword {

	public ItemTMSword(ToolMaterial material) {
		super(material);
		this.setCreativeTab(ModTechMobs.TAB_TM);
	}

	public ItemTMSword(ToolMaterial material, String name) {
		this(material);
		this.setUnlocalizedName(name);
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("item.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack s) {
		return String.format("item.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
	}
}
