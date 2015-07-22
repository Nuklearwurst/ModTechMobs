package com.fravokados.dangertech.techmobs.block;

import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.lib.GUIIDs;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class BlockCreativeTechnology extends BlockTM  implements ITileEntityProvider {

	public BlockCreativeTechnology() {
		super(Material.iron, Strings.Block.CREATIVE_TECHNOLOGY);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCreativeTechnology();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float a1, float a2, float a3) {
		if(!world.isRemote) {
			player.openGui(ModTechMobs.instance, GUIIDs.CREATIVE_TECHNOLOGY, world, x, y, z);
		}
		return true;
	}
}
