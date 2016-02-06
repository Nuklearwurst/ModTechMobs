package com.fravokados.dangertech.mindim.block;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortal;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Portal Block
 * @author Nuklearwurst
 */
public class BlockPortalMinDim extends BlockMD implements ITileEntityProvider{

	public static final PropertyEnum<EnumFacing.Axis> AXIS_PROPERTY = PropertyEnum.create("axis", EnumFacing.Axis.class);


	public BlockPortalMinDim() {
		super(Material.portal, Strings.Block.portal);
		this.setCreativeTab(null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS_PROPERTY, EnumFacing.Axis.X));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		switch (world.getBlockState(pos).getValue(AXIS_PROPERTY)) {
			case X: //X axis
				setBlockBounds(0.2F, 0, 0, 0.8F, 1, 1);
				break;
			case Y: //Y axis
				setBlockBounds(0, 0.2F, 0, 1, 0.8F, 1);
				break;
			case Z: //Z axis
				setBlockBounds(0, 0, 0.2F, 1, 1, 0.8F);
				break;
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, AXIS_PROPERTY);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isFullBlock() {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		super.onEntityCollidedWithBlock(world, pos, state, entity);
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te != null && te instanceof TileEntityPortal) {
				((TileEntityPortal) te).onEntityEnterPortal(entity);
			} else {
				LogHelperMD.error("Invalid Portal!");
				removePortalAndSurroundingPortals(world, pos);
			}
		}
	}

	public void removePortalAndSurroundingPortals(World world, BlockPos pos) {
		world.setBlockToAir(pos);
		for(EnumFacing dir : EnumFacing.VALUES) {
			BlockPos newPos = pos.offset(dir);
			Block b = world.getBlockState(newPos).getBlock();
			if(b != null && b instanceof BlockPortalMinDim) {
				this.removePortalAndSurroundingPortals(world, newPos);
			}
		}
	}



	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPortal();
	}

	@Override
	public int quantityDropped(Random r) {
		return 0;
	}

	@Override
	public Item getItem(World worldIn, BlockPos pos) {
		return null;
	}


	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta > EnumFacing.Axis.values().length) {
			meta = 0;
		}
		return getDefaultState().withProperty(AXIS_PROPERTY, EnumFacing.Axis.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(AXIS_PROPERTY).ordinal();
	}


	public static void placePortalInWorld(World world, BlockPos pos, BlockPos controllerPos, EnumFacing.Axis axis) {
		world.setBlockState(pos, ModBlocks.blockPortalBlock.getDefaultState().withProperty(AXIS_PROPERTY, axis));
		TileEntityPortal te = (TileEntityPortal) world.getTileEntity(pos);
		te.setPortalController(controllerPos);
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
