//package com.fravokados.dangertech.techmobs.block;
//
//import com.fravokados.dangertech.techmobs.common.SleepingManager;
//import com.fravokados.dangertech.techmobs.common.init.ModItems;
//import com.fravokados.dangertech.techmobs.lib.Strings;
//import com.fravokados.dangertech.techmobs.lib.Textures;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockBed;
//import net.minecraft.block.BlockDirectional;
//import net.minecraft.block.material.Material;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.Item;
//import net.minecraft.util.ChatComponentTranslation;
//import net.minecraft.util.ChunkCoordinates;
//import net.minecraft.util.Direction;
//import net.minecraft.util.IIcon;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.BiomeGenBase;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * @author Nuklearwurst
// */
//public class BlockCot extends BlockTM {
//
//
//	@SideOnly(Side.CLIENT)
//	private IIcon[] iconEnd;
//	@SideOnly(Side.CLIENT)
//	private IIcon[] iconSide;
//	@SideOnly(Side.CLIENT)
//	private IIcon[] iconTop;
//
//
//	public BlockCot() {
//		super(Material.cloth, Strings.Block.COT);
//		this.setBlockTextureName(Textures.MOD_ASSET_DOMAIN + Strings.Block.COT);
//		setBlockBounds();
//	}
//
//	private void setBlockBounds() {
//		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
//	}
//
//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
//		if (world.isRemote) {
//			return true;
//		} else {
//			int metadata = world.getBlockMetadata(x, y, z);
//
//			if (!BlockBed.isBlockHeadOfBed(metadata)) {
//				int direction = BlockDirectional.getDirection(metadata);
//				x += Direction.offsetX[direction];
//				z += Direction.offsetZ[direction];
//
//				if (world.getBlock(x, y, z) != this) {
//					return true;
//				}
//
//				metadata = world.getBlockMetadata(x, y, z);
//			}
//
//			if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell) {
//				if (isBedInUse(metadata)) {
//					EntityPlayer sleepingPlayer = null;
//
//					//noinspection unchecked
//					for (EntityPlayer playerEntity : (List<EntityPlayer>) world.playerEntities) {
//						if (playerEntity.isPlayerSleeping()) {
//							ChunkCoordinates location = playerEntity.playerLocation;
//							if (location.posX == x && location.posY == y && location.posZ == z) {
//								sleepingPlayer = playerEntity;
//							}
//						}
//					}
//					if (sleepingPlayer != null) {
//						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied"));
//						return true;
//					}
//				}
//
//				switch (player.sleepInBedAt(x, y, z)) {
//					case OK:
//						setBedInUse(world, x, y, z, player);
//						break;
//					case NOT_POSSIBLE_NOW:
//						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
//						break;
//					case NOT_POSSIBLE_HERE:
//						player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));
//						break;
//
//				}
//				return true;
//			} else {
//				//sleeping in the nether
//				double xPos = (double) x + 0.5D;
//				double yPos = (double) y + 0.5D;
//				double zPos = (double) z + 0.5D;
//				world.setBlockToAir(x, y, z);
//				int direction = BlockDirectional.getDirection(metadata);
//				x += Direction.offsetX[direction];
//				z += Direction.offsetZ[direction];
//
//				if (world.getBlock(x, y, z) == this) {
//					world.setBlockToAir(x, y, z);
//					xPos = (xPos + (double) x + 0.5D) / 2.0D;
//					yPos = (yPos + (double) y + 0.5D) / 2.0D;
//					zPos = (zPos + (double) z + 0.5D) / 2.0D;
//				}
//
//				world.newExplosion(null, xPos, yPos, zPos, 5.0F, true, true);
//				return true;
//			}
//		}
//	}
//
//	@Override
//	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
//		return true;
//	}
//
//	@Override
//	public Item getItemDropped(int meta, Random rand, int j) {
//		return BlockBed.isBlockHeadOfBed(meta) ? Item.getItemById(0) : ModItems.item_cot;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public Item getItem(World world, int x, int y, int z) {
//		return ModItems.item_cot;
//	}
//
//	/**
//	 * Returns whether this bed is currently in use
//	 */
//	private static boolean isBedInUse(int meta) {
//		return (meta & 4) != 0;
//	}
//
//	private static void setBedInUse(World world, int x, int y, int z, EntityPlayer player) {
//		SleepingManager.addPlayer(player);
//		int meta = world.getBlockMetadata(x, y, z) | 4;
//		world.setBlockMetadataWithNotify(x, y, z, meta, 4);
//	}
//
//	/**
//	 * Gets the block's texture. Args: side, meta
//	 */
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(int side, int meta) {
//		if (side == 0) {
//			return Blocks.planks.getBlockTextureFromSide(side);
//		} else {
//			int direction = BlockDirectional.getDirection(meta);
//			int isHead = BlockBed.isBlockHeadOfBed(meta) ? 1 : 0;
//			if (side == 1) {
//				return this.iconTop[isHead];
//			} else if (side != Direction.directionToFacing[direction] && side != Direction.directionToFacing[Direction.rotateOpposite[direction]]) {
//				return this.iconSide[isHead];
//			} else {
//				return this.iconEnd[isHead];
//			}
//		}
//	}
//
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister reg) {
//		this.iconTop = new IIcon[]{reg.registerIcon(this.getTextureName() + "_feet_top"), reg.registerIcon(this.getTextureName() + "_head_top")};
//		this.iconEnd = new IIcon[]{reg.registerIcon(this.getTextureName() + "_feet_end"), reg.registerIcon(this.getTextureName() + "_head_end")};
//		this.iconSide = new IIcon[]{reg.registerIcon(this.getTextureName() + "_feet_side"), reg.registerIcon(this.getTextureName() + "_head_side")};
//	}
//
//	@Override
//	public int getRenderType() {
//		return 14;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
//		int metadata = world.getBlockMetadata(x, y, z);
//		int direction = BlockDirectional.getDirection(metadata);
//		//destroy other half
//		if (BlockBed.isBlockHeadOfBed(metadata)) {
//			if (world.getBlock(x - Direction.offsetX[direction], y, z - Direction.offsetZ[direction]) != this) {
//				world.setBlockToAir(x, y, z);
//			}
//		} else if (world.getBlock(x + Direction.offsetX[direction], y, z + Direction.offsetZ[direction]) != this) {
//			world.setBlockToAir(x, y, z);
//			//only feet part drops item
//			if (!world.isRemote) {
//				this.dropBlockAsItem(world, x, y, z, metadata, 0);
//			}
//		}
//	}
//
//	@Override
//	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
//		if (!BlockBed.isBlockHeadOfBed(meta)) {
//			//only feet part drops item
//			super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, 0);
//		}
//	}
//
//	@Override
//	public int getMobilityFlag() {
//		//drop when moved
//		return 1;
//	}
//
//	@Override
//	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
//		//make sure not to drop anything in creative
//		if (player.capabilities.isCreativeMode && BlockBed.isBlockHeadOfBed(meta)) {
//			int direction = BlockDirectional.getDirection(meta);
//			x -= Direction.offsetX[direction];
//			z -= Direction.offsetZ[direction];
//
//			if (world.getBlock(x, y, z) == this) {
//				world.setBlockToAir(x, y, z);
//			}
//		}
//	}
//}
