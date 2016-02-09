package com.fravokados.dangertech.techmobs.block;

import com.fravokados.dangertech.techmobs.common.SleepingManager;
import com.fravokados.dangertech.techmobs.common.init.ModItems;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * @author Nuklearwurst
 */
public class BlockCot extends BlockTM {

	public static final PropertyEnum<BlockBed.EnumPartType> PART = BlockBed.PART;
	public static final PropertyBool OCCUPIED = BlockBed.OCCUPIED;
	public static final PropertyDirection FACING = BlockDirectional.FACING;


	public BlockCot() {
		super(Material.cloth, Strings.Block.COT, null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(OCCUPIED, false));
		this.setBedBounds();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			if (state.getValue(PART) != BlockBed.EnumPartType.HEAD) {
				pos = pos.offset(state.getValue(FACING));
				state = worldIn.getBlockState(pos);

				if (state.getBlock() != this) {
					return true;
				}
			}

			if (worldIn.provider.canRespawnHere() && worldIn.getBiomeGenForCoords(pos) != BiomeGenBase.hell) {
				//Look for already sleeping players
				if (state.getValue(OCCUPIED)) {
					EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

					if (entityplayer != null) {
						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied"));
						return true;
					}
					//in case the bed is in an invalid state (no player found)
					state = state.withProperty(OCCUPIED, false);
					worldIn.setBlockState(pos, state, 4);
				}

				EntityPlayer.EnumStatus enumStatus = player.trySleep(pos);

				if (enumStatus == EntityPlayer.EnumStatus.OK) {
					state = state.withProperty(OCCUPIED, true);
					worldIn.setBlockState(pos, state, 4);
					SleepingManager.addPlayer(player);
					return true;
				} else {
					if (enumStatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
					} else if (enumStatus == EntityPlayer.EnumStatus.NOT_SAFE) {
						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));
					}

					return true;
				}
			} else {
				//sleeping in the nether
				worldIn.setBlockToAir(pos);
				BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

				if (worldIn.getBlockState(blockpos).getBlock() == this) {
					worldIn.setBlockToAir(blockpos);
				}

				worldIn.newExplosion(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, true);
				return true;
			}
		}
	}

	@Override
	public boolean isBed(IBlockAccess world, BlockPos pos, Entity player) {
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
	public boolean isFullCube() {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		this.setBedBounds();
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
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

	private void setBedBounds() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {
		return Items.bed;
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
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, (meta & 4) > 0) : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(state.getValue(FACING)));

			if (iblockstate.getBlock() == this) {
				state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
			}
		}

		return state;
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
	protected BlockState createBlockState() {
		return new BlockState(this, FACING, PART, OCCUPIED);
	}
}
