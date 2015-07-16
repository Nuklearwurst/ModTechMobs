package com.fravokados.mindim.block;

import com.fravokados.mindim.block.tileentity.TileEntityPortal;
import com.fravokados.mindim.lib.Strings;
import com.fravokados.mindim.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

/**
 * Portal Block
 * @author Nuklearwurst
 */
public class BlockPortalMinDim extends BlockMD implements ITileEntityProvider{


	public BlockPortalMinDim() {
		super(Material.portal, Strings.Block.portal);
		this.setCreativeTab(null);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
			case 1: //X axis
				setBlockBounds(0.2F, 0, 0, 0.8F, 1, 1);
				break;
			case 2: //Y axis
				setBlockBounds(0, 0.2F, 0, 1, 0.8F, 1);
				break;
			case 3: //Z axis
				setBlockBounds(0, 0, 0.2F, 1, 1, 0.8F);
				break;
			default:
				setBlockBounds(0, 0, 0, 1, 1, 1);
				break;
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEntityPortal) {
				((TileEntityPortal) te).onEntityEnterPortal(entity);
			} else {
				LogHelper.error("Invalid Portal!");
				removePortalAndSurroundingPortals(world, x, y, z);
			}
		}
	}

	public void removePortalAndSurroundingPortals(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if(b != null && b instanceof BlockPortalMinDim) {
				((BlockPortalMinDim) b).removePortalAndSurroundingPortals(world, x, y, z);
			}
		}
	}



	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityPortal();
	}

	@Override
	public int quantityDropped(Random r) {
		return 0;
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemById(0);
	}

	public static void placePortalInWorld(World world, int x, int y, int z, int cx, int cy, int cz, int meta) {
		world.setBlock(x, y, z, ModBlocks.blockPortalBlock, meta, 3);
		TileEntityPortal te = (TileEntityPortal) world.getTileEntity(x, y, z);
		te.setPortalController(cx, cy, cz);
	}

	public static int convertFacingToMeta(int facing) {
		switch (facing) {
			case 0:
			case 1:
				return 2;
			case 2:
			case 3:
				return 3;
			case 4:
			case 5:
				return 1;
			default:
				return 0;
		}
	}
}
