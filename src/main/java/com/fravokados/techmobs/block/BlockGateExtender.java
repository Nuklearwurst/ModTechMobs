package com.fravokados.techmobs.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.fravokados.techmobs.creativetab.CreativeTabTechMobs;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.lib.Textures;
import com.fravokados.techmobs.tileentity.TileEntityGateExtender;


/**
 * drawbridge like block
 * TODO gate extender (--> similar feature: tinker's construct)
 * @author Nuklearwurst
 *
 */
public class BlockGateExtender extends BlockTM implements ITileEntityProvider {

	public BlockGateExtender() {
		super(Material.wood);
		this.setBlockName(Strings.Block.GATE_EXTENDER);
		this.setCreativeTab(CreativeTabTechMobs.TAB_TM);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGateExtender();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Textures.TEXTURE_PREFIX + Strings.Block.GATE_EXTENDER);
	}
	
	
}