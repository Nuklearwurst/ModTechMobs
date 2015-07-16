package com.fravokados.mindim.block;

import com.fravokados.mindim.ModMiningDimension;
import com.fravokados.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.mindim.lib.GUIIDs;
import com.fravokados.mindim.lib.Textures;
import com.fravokados.mindim.plugin.PluginIC2;
import com.fravokados.mindim.portal.PortalManager;
import com.fravokados.mindim.util.BlockUtils;
import com.fravokados.mindim.util.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * @author Nuklearwurst
 */
public class BlockPortalFrame extends BlockMD implements ITileEntityProvider {

	public static final int META_FRAME_ENTITY =         0;
	public static final int META_CONTROLLER_ENTITY =    1;
	public static final int META_ITEM_PORTAL =          2;
	public static final int META_FLUID_PORTAL =         3;
	public static final int META_ENERGY_PORTAL =        4;
	public static final int META_CONTROLLER_MINECART =  5;
	public static final int META_FRAME_MINECART =       6;


	private IIcon[] iconFrameSide;
	private IIcon[] iconFrameSideRotated;
	private IIcon[] iconFrameBack;
	private IIcon[] iconFrameFront;

	private IIcon iconController_online;
	private IIcon iconController_offline;
	private IIcon iconController_disabled;
	private IIcon iconController_front_online;
	private IIcon iconController_front_offline;
	private IIcon iconController_front_disabled;

	public BlockPortalFrame() {
		super(Material.iron);
		setHardness(2.0F);
		setResistance(4.0F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float j, float k, float l) {
		if(PluginIC2.isItemWrench(player.getCurrentEquippedItem())) {
			return true;
		}
		switch (world.getBlockMetadata(x, y, z)) {
			case META_CONTROLLER_ENTITY:
				player.openGui(ModMiningDimension.instance, GUIIDs.ENTITY_PORTAL_CONTROLLER, world, x, y, z);
				return true;
		}
		return false;
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		super.onBlockPreDestroy(world, x, y, z, meta);
		TileEntity te = world.getTileEntity(x, y, z);

		if(meta == META_CONTROLLER_ENTITY) {
			if (te != null && te instanceof TileEntityPortalControllerEntity) {
				PortalManager.getInstance().removeEntityPortal(((TileEntityPortalControllerEntity) te).getId());
			}
		} else if(meta == META_FRAME_ENTITY) {
			if(te != null && te instanceof TileEntityPortalFrame) {
				((TileEntityPortalFrame) te).onDestroy();
			}
		}
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null) {
				if (te instanceof IBlockPlacedListener) {
					((IBlockPlacedListener) te).onBlockPostPlaced(world, x, y, z, meta);
				}
			}
		}
		super.onPostBlockPlaced(world, x, y, z, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
			case META_CONTROLLER_ENTITY:
				return new TileEntityPortalControllerEntity();
			case META_FRAME_ENTITY:
				return new TileEntityPortalFrame();
		}
		return null;
	}

	@SuppressWarnings(value = {"unchecked"})
	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		//Controller Entity
		iconController_online = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_ONLINE);
		iconController_offline = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_OFFLINE);
		iconController_disabled = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_DISABLED);
		iconController_front_online = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_ONLINE_FRONT);
		iconController_front_offline = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_OFFLINE_FRONT);
		iconController_front_disabled = reg.registerIcon(Textures.BLOCK_PORTAL_CONTROLLER_DISABLED_FRONT);
		//Frame Entity
		iconFrameBack = new IIcon[3];
		iconFrameFront = new IIcon[3];
		iconFrameSide = new IIcon[3];
		iconFrameSideRotated = new IIcon[3];
		for(int i = 0; i < 3; i++) {
			iconFrameBack[i] = reg.registerIcon(Textures.BLOCK_PORTAL_FRAME_BACK[i]);
			iconFrameFront[i] = reg.registerIcon(Textures.BLOCK_PORTAL_FRAME_FRONT[i]);
			iconFrameSide[i] = reg.registerIcon(Textures.BLOCK_PORTAL_FRAME_SIDE[i]);
			iconFrameSideRotated[i] = reg.registerIcon(Textures.BLOCK_PORTAL_FRAME_SIDE_ROTATED[i]);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta) {
			case META_FRAME_ENTITY:
				return side == 3 ? iconFrameFront[2] : iconFrameBack[2];
			case META_CONTROLLER_ENTITY:
				return side == 3 ? iconController_front_online : iconController_online;
		}
		return iconFrameFront[2];
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null) {
			if(te instanceof TileEntityPortalControllerEntity) {
				short facing = ((TileEntityPortalControllerEntity) te).getFacing();
				TileEntityPortalControllerEntity.State state = ((TileEntityPortalControllerEntity) te).getState();
				if(side == facing) {
					if(state == TileEntityPortalControllerEntity.State.NO_MULTIBLOCK || state == TileEntityPortalControllerEntity.State.READY) {
						return iconController_front_disabled;
					} else if(state == TileEntityPortalControllerEntity.State.INCOMING_PORTAL || state == TileEntityPortalControllerEntity.State.OUTGOING_PORTAL) {
						return iconController_front_online;
					} else {
						return iconController_front_offline;
					}
				} else {
					if(state == TileEntityPortalControllerEntity.State.NO_MULTIBLOCK || state == TileEntityPortalControllerEntity.State.READY) {
						return iconController_disabled;
					} else if(state == TileEntityPortalControllerEntity.State.INCOMING_PORTAL || state == TileEntityPortalControllerEntity.State.OUTGOING_PORTAL) {
						return iconController_online;
					} else {
						return iconController_offline;
					}
				}
			} else if(te instanceof TileEntityPortalFrame) {
				short facing = ((TileEntityPortalFrame) te).getFacing();
				TileEntityPortalControllerEntity.State state = ((TileEntityPortalFrame) te).getControllerState();
				byte index = 1;
				if(state == TileEntityPortalControllerEntity.State.NO_MULTIBLOCK || state == TileEntityPortalControllerEntity.State.READY) {
					index = 0;
				} else if(state == TileEntityPortalControllerEntity.State.INCOMING_PORTAL || state == TileEntityPortalControllerEntity.State.OUTGOING_PORTAL) {
					index = 2;
				}
				if(state == TileEntityPortalControllerEntity.State.INCOMING_CONNECTION || state == TileEntityPortalControllerEntity.State.INCOMING_PORTAL || state == TileEntityPortalControllerEntity.State.OUTGOING_PORTAL || state == TileEntityPortalControllerEntity.State.CONNECTING) {
					if(((TileEntityPortalFrame) te).getClientRenderInfo() != null) {
						facing = (short) ((TileEntityPortalFrame) te).getClientRenderInfo().originDirection.ordinal();
					}
				}
				if(facing == side) {
					return iconFrameFront[index];
				}
				boolean flagY = side > 1 && (world.getBlock(x , y + 1, z) == this || world.getBlock(x, y - 1, z) == this);
				boolean flagZ = (side != 2 && side != 3) && (world.getBlock(x , y, z + 1) == this || world.getBlock(x, y, z - 1) == this);
				boolean flagX = (side != 4 && side != 5) && (world.getBlock(x + 1 , y, z) == this || world.getBlock(x - 1, y, z) == this);
				if(flagX == flagY ? flagX : flagZ) {
					return iconFrameBack[index];
				} else {
					if(flagY || side < 2 && flagZ) {
						return iconFrameSide[index];
					} else {
						//top and bottom sides need to be rotated on x axis
						return iconFrameSideRotated[index];
					}
				}
			}
		}
		return super.getIcon(world, x, y, z, side);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entityLivingBase, stack);
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IFacingSix) {
			RotationUtils.updateFacing((IFacingSix) te, entityLivingBase, x, y, z);
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockUtils.dropInventory(world, x, y, z);
		BlockUtils.dropUpgrades(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
