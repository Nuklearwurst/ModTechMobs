package com.fravokados.dangertech.techmobs.block;

import com.fravokados.dangertech.techmobs.ModTechMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.fravokados.dangertech.techmobs.lib.Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTM extends Block {

	public BlockTM() {
		this(Material.rock);
	}
	
	public BlockTM(Material material) {
		super(material);
		this.setCreativeTab(ModTechMobs.TAB_TM);
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
        return String.format("tile.%s%s", Textures.MOD_ASSET_DOMAIN, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
    }
	
}
