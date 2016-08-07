package com.fravokados.dangertech.monsters.block;

import com.fravokados.dangertech.monsters.common.SleepingManager;
import com.fravokados.dangertech.monsters.common.init.ModItems;
import com.fravokados.dangertech.monsters.item.ItemCot;
import com.fravokados.dangertech.monsters.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.BlockBed.EnumPartType.FOOT;

/**
 * @author Nuklearwurst
 */
public class BlockCot extends BlockTM {

	public static final PropertyEnum<BlockBed.EnumPartType> PART = BlockBed.PART;
	public static final PropertyBool OCCUPIED = BlockBed.OCCUPIED;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	protected static final AxisAlignedBB BED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);


	public BlockCot() {
		super(Material.CLOTH, Strings.Block.COT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PART, FOOT).withProperty(OCCUPIED, false));
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			if (state.getValue(PART) != BlockBed.EnumPartType.HEAD) {
				pos = pos.offset(state.getValue(FACING));
				state = world.getBlockState(pos);

				if (state.getBlock() != this) {
					return true;
				}
			}

			if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(pos) != Biomes.HELL) {
				//Look for already sleeping players
				if (state.getValue(OCCUPIED)) {
					EntityPlayer entityplayer = this.getPlayerInBed(world, pos);

					if (entityplayer != null) {
						player.addChatComponentMessage(new TextComponentTranslation("tile.bed.occupied"));
						return true;
					}
					//in case the bed is in an invalid state (no player found)
					state = state.withProperty(OCCUPIED, false);
					world.setBlockState(pos, state, 4);
				}

				EntityPlayer.SleepResult result = player.trySleep(pos);

				if (result == EntityPlayer.SleepResult.OK) {
					state = state.withProperty(OCCUPIED, true);
					world.setBlockState(pos, state, 4);
					SleepingManager.addPlayer(player);
					return true;
				} else {
					if (result == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
						player.addChatComponentMessage(new TextComponentTranslation("tile.bed.noSleep"));
					} else if (result == EntityPlayer.SleepResult.NOT_SAFE) {
						player.addChatComponentMessage(new TextComponentTranslation("tile.bed.notSafe"));
					}

					return true;
				}
			} else {
				//sleeping in the nether
				world.setBlockToAir(pos);
				BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

				if (world.getBlockState(blockpos).getBlock() == this) {
					world.setBlockToAir(blockpos);
				}

				world.newExplosion(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, true);
				return true;
			}
		}
	}

	@Override
	public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player) {
		return true;
	}

	private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
		for (EntityPlayer entityplayer : worldIn.playerEntities) {
			if (entityplayer.isPlayerSleeping() && entityplayer.playerLocation.equals(pos)) {
				return entityplayer;
			}
		}

		return null;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		EnumFacing enumfacing = state.getValue(FACING);

		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		} else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
			worldIn.setBlockToAir(pos);

			if (!worldIn.isRemote) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : ModItems.item_cot;
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (state.getValue(PART) == FOOT) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		//noinspection ConstantConditions
		return new ItemStack(ModItems.item_cot);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

			if (worldIn.getBlockState(blockpos).getBlock() == this) {
				worldIn.setBlockToAir(blockpos);
			}
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, (meta & 4) > 0) : this.getDefaultState().withProperty(PART, FOOT).withProperty(FACING, enumfacing);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(PART) == FOOT) {
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(state.getValue(FACING)));

			if (iblockstate.getBlock() == this) {
				state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
			}
		}

		return state;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			i |= 8;

			if (state.getValue(OCCUPIED)) {
				i |= 4;
			}
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, PART, OCCUPIED);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BED_AABB;
	}

	@Override
	public Item createItemBlock() {
		return new ItemCot();
	}
}
