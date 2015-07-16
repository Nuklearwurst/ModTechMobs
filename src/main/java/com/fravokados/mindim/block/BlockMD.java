package com.fravokados.mindim.block;

/**
 * @author Nuklearwurst
 */

import com.fravokados.mindim.ModMiningDimension;
import com.fravokados.mindim.lib.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockMD extends Block {

	public BlockMD() {
		this(Material.rock);
	}

	public BlockMD(Material material) {
		super(material);
		this.setCreativeTab(ModMiningDimension.TAB_MD);
	}

	public BlockMD(String name) {
		this();
		this.setBlockName(name);
	}

	public BlockMD(Material mat, String name) {
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
		return String.format("tile.%s%s", Textures.TEXTURE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName())); //tile.MOD_ID:BLOCK_NAME
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getUnwrappedUnlocalizedName(this.getUnlocalizedName()));
	}
}