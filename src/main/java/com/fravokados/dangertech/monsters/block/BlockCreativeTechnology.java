package com.fravokados.dangertech.monsters.block;

import com.fravokados.dangertech.monsters.ModTechMobs;
import com.fravokados.dangertech.monsters.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.monsters.lib.GUIIDs;
import com.fravokados.dangertech.monsters.lib.Strings;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class BlockCreativeTechnology extends BlockTM  implements ITileEntityProvider {

	public BlockCreativeTechnology() {
		super(Material.IRON, Strings.Block.CREATIVE_TECHNOLOGY);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCreativeTechnology();
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(ModTechMobs.instance, GUIIDs.CREATIVE_TECHNOLOGY, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
