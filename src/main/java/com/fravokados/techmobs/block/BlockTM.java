package com.fravokados.techmobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.fravokados.techmobs.creativetab.CreativeTabTechMobs;
import com.fravokados.techmobs.lib.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTM extends Block {

	public BlockTM() {
		this(Material.rock);
	}
	
	public BlockTM(Material material) {
		super(material);
		this.setCreativeTab(CreativeTabTechMobs.TAB_TM);
	}
	
	public BlockTM(String name) {
		this();
		this.setBlockName(name);
	}
	
	public BlockTM(Material mat, String name) {
		this(mat);
		this.setBlockName(name);
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

	@Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
    }
	
}
