package com.fravokados.dangertech.portals.block;

import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortal;
import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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

	private static final PropertyEnum<EnumFacing.Axis> AXIS_PROPERTY = PropertyEnum.create("axis", EnumFacing.Axis.class);

	protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.2F, 0, 0, 0.8F, 1, 1);
	protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0, 0.2F, 0, 1, 0.8F, 1);
	protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0, 0, 0.2F, 1, 1, 0.8F);

	public BlockPortalMinDim() {
		super(Material.PORTAL, Strings.Block.portal);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS_PROPERTY, EnumFacing.Axis.X));
		//noinspection ConstantConditions
		this.setCreativeTab(null);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(AXIS_PROPERTY)) {
			case X:
				return X_AABB;
			case Y:
				return Y_AABB;
			case Z:
			default:
				return Z_AABB;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS_PROPERTY);
	}

	@Override
	public boolean isFullCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState blockState) {
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
			if(b instanceof BlockPortalMinDim) {
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		//noinspection ConstantConditions
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
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
		//noinspection ConstantConditions
		world.setBlockState(pos, ModBlocks.blockPortalBlock.getDefaultState().withProperty(AXIS_PROPERTY, axis));
		TileEntityPortal te = (TileEntityPortal) world.getTileEntity(pos);
		if (te != null) {
			te.setPortalController(controllerPos);
		}
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
