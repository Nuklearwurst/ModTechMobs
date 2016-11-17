package com.fravokados.dangertech.portals.block;

import com.fravokados.dangertech.api.block.IBlockPlacedListener;
import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.IEnergyTypeAware;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.portals.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.portals.block.types.PortalFrameState;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.item.ItemBlockPortalFrame;
import com.fravokados.dangertech.portals.lib.GUIIDs;
import com.fravokados.dangertech.portals.lib.NBTKeys;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.util.RotationUtils;
import com.fravokados.dangertech.portals.portal.PortalConstructor;
import com.fravokados.dangertech.portals.portal.PortalManager;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.fravokados.dangertech.portals.block.types.PortalFrameType.BASIC_CONTROLLER;
import static com.fravokados.dangertech.portals.block.types.PortalFrameType.BASIC_FRAME;

/**
 * @author Nuklearwurst
 */
@Optional.Interface(iface = "ic2.api.tile.IWrenchable", modid = PluginManager.IC2)
public class BlockPortalFrame extends BlockMD implements ITileEntityProvider, IWrenchable {

	public static final PropertyEnum<PortalFrameType> TYPE_PROPERTY = PropertyEnum.create("type", PortalFrameType.class);
	public static final PropertyEnum<PortalFrameState> STATE_PROPERTY = PropertyEnum.create("state", PortalFrameState.class);
	public static final PropertyDirection FACING_PROPERTY = PropertyDirection.create("facing");

	public BlockPortalFrame() {
		super(Material.IRON, Strings.Block.portalMachineBase);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE_PROPERTY, PortalFrameType.BASIC_FRAME));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te == null || !(te instanceof IPortalFrameWithState)) {
			return state.withProperty(STATE_PROPERTY, PortalFrameState.DISCONNECTED).withProperty(FACING_PROPERTY, EnumFacing.UP);
		}
		return state.withProperty(STATE_PROPERTY, ((IPortalFrameWithState) te).getPortalFrameState()).withProperty(FACING_PROPERTY, ((IPortalFrameWithState) te).getFacing());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && PluginManager.isItemWrench(heldItem)) {
			return true;
		}

		switch (state.getValue(TYPE_PROPERTY)) {
			case BASIC_CONTROLLER:
				player.openGui(ModMiningDimension.instance, GUIIDs.ENTITY_PORTAL_CONTROLLER, world, pos.getX(), pos.getY(), pos.getZ());
				return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta > PortalFrameType.values().length) {
			meta = 0;
		}
		switch (PortalFrameType.values()[meta]) {
			case BASIC_CONTROLLER:
				return new TileEntityPortalControllerEntity();
			case BASIC_FRAME:
				return new TileEntityPortalFrame();
		}
		return null;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta > PortalFrameType.values().length) {
			meta = 0;
		}
		return getDefaultState().withProperty(TYPE_PROPERTY, PortalFrameType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE_PROPERTY).ordinal();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		EnergyManager.createItemVariantsForEnergyTypes(list, item, 1, EnergyManager.getAvailableEnergyTypes());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE_PROPERTY, STATE_PROPERTY, FACING_PROPERTY);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		TileEntity te = world.getTileEntity(pos);
		if(te != null) {
			if(te instanceof IFacingSix) {
				((IFacingSix) te).setFacing(RotationUtils.getFacingFromEntity(pos, placer));
			}
			if(!world.isRemote) {
				if(te instanceof IUpgradable) {
					ItemUtils.readUpgradesFromItemStack(((IUpgradable) te).getUpgradeInventory(), stack);
				}
				if(te instanceof TileEntityPortalControllerEntity) {
					NBTTagCompound tag = ItemUtils.getNBTTagCompound(stack);
					if(tag.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID)) {
						((TileEntityPortalControllerEntity) te).setId(tag.getInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID));
					}
					if(tag.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_NAME)) {
						((TileEntityPortalControllerEntity) te).setName(tag.getString(NBTKeys.DESTINATION_CARD_PORTAL_NAME));
					}
					EnergyManager.writeEnergyTypeToEnergyTypeAware((IEnergyTypeAware) te, stack);
				}
				if (te instanceof IBlockPlacedListener) {
					((IBlockPlacedListener) te).onBlockPostPlaced(world, pos, state);
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		PortalFrameType type = state.getValue(TYPE_PROPERTY);
		if(type == BASIC_CONTROLLER) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof IEnergyTypeAware) {
				return EnergyManager.createItemStackWithEnergyType(this, 1, BASIC_CONTROLLER.getID(), ((IEnergyTypeAware) te).getEnergyType());
			}
		}
		return new ItemStack(this, 1, type.ordinal());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE_PROPERTY).ordinal();
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		switch (state.getValue(TYPE_PROPERTY)) {
			case BASIC_CONTROLLER:
				if (te != null && te instanceof TileEntityPortalControllerEntity) {
					PortalManager.getInstance().removeEntityPortal(((TileEntityPortalControllerEntity) te).getId());
					if(((TileEntityPortalControllerEntity) te).getPortalFrameState() == PortalFrameState.DISCONNECTED) {
						PortalConstructor.createPortalsAround(world, pos);
					}
				}
				break;
			case BASIC_FRAME:
				if(te != null && te instanceof TileEntityPortalFrame) {
					if(((TileEntityPortalFrame) te).getPortalFrameState() == PortalFrameState.DISCONNECTED) {
						PortalConstructor.createPortalsAround(world, pos);
					} else {
						((TileEntityPortalFrame) te).onDestroy();
					}
				}
				break;
		}
		BlockUtils.dropInventory(world, pos);
		BlockUtils.dropUpgrades(world, pos);

		super.breakBlock(world, pos, state);
	}

	@Override
	public Item createItemBlock() {
		return new ItemBlockPortalFrame(this);
	}


	@Deprecated
	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te, EntityPlayer player, int fortune) {
		//noinspection ConstantConditions
		ItemStack out = new ItemStack(ModBlocks.blockPortalFrame, 1, state.getValue(TYPE_PROPERTY).ordinal());
		if(te instanceof TileEntityPortalControllerEntity) {
			((TileEntityPortalControllerEntity) te).writeDataToStack(out);
		}
		return Collections.singletonList(out);
	}

	@Deprecated
	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public EnumFacing getFacing(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof IFacingSix) {
			return ((IFacingSix) te).getFacing();
		}
		return EnumFacing.DOWN;
	}

	@Deprecated
	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof IFacingSix) {
			((IFacingSix) te).setFacing(newDirection);
			return true;
		}
		return false;
	}

	@Deprecated
	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
		return true;
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if(state.getValue(TYPE_PROPERTY) == BASIC_FRAME && state.getActualState(world, pos).getValue(STATE_PROPERTY) == PortalFrameState.ACTIVE) {
			Random random = world.rand;


			for(EnumFacing facing : EnumFacing.VALUES) {
				double x = (double)((float)pos.getX() + random.nextFloat());
				double y = (double)((float)pos.getY() + random.nextFloat());
				double z = (double)((float)pos.getZ() + random.nextFloat());

				if(!world.getBlockState(pos.offset(facing)).isOpaqueCube()) {
					switch (facing) {
						case DOWN:
							y = (double)pos.getY() - 0.0625D - 1;
							break;
						case UP:
							y = (double)pos.getY() + 0.0625D;
							break;
						case NORTH:
							z = (double)pos.getZ() - 0.0625D;
							break;
						case SOUTH:
							z = (double)pos.getZ() + 0.0625D + 1.0D;
							break;
						case WEST:
							x = (double)pos.getX() - 0.0625D;
							break;
						case EAST:
							x = (double)pos.getX() + 0.0625D + 1.0D;
							break;
					}
					if (x < (double)pos.getX() || x > (double)(pos.getX() + 1) || y < (double) pos.getY() || y > (double)(pos.getY()) || z < (double)pos.getZ() || z > (double)(pos.getZ() + 1))
					{
						world.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
	}
}
