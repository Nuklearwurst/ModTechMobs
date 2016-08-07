package com.fravokados.dangertech.mindim.block;

import com.fravokados.dangertech.api.block.IBlockPlacedListener;
import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.IEnergyTypeAware;
import com.fravokados.dangertech.mindim.ModMiningDimension;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.mindim.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.mindim.block.types.PortalFrameState;
import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.item.ItemBlockPortalFrame;
import com.fravokados.dangertech.mindim.lib.GUIIDs;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.util.RotationUtils;
import com.fravokados.dangertech.mindim.plugin.PluginIC2;
import com.fravokados.dangertech.mindim.portal.PortalManager;
import com.fravokados.dangertech.techmobs.lib.util.PlayerUtils;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author Nuklearwurst
 */
public class BlockPortalFrame extends BlockMD implements ITileEntityProvider {

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
			return state.withProperty(STATE_PROPERTY, PortalFrameState.DISABLED).withProperty(FACING_PROPERTY, EnumFacing.UP);
		}
		return state.withProperty(STATE_PROPERTY, ((IPortalFrameWithState) te).getPortalFrameState()).withProperty(FACING_PROPERTY, ((IPortalFrameWithState) te).getFacing());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(PluginIC2.isItemWrench(PlayerUtils.getCurrentUsablePlayerItem(player, (ItemStack o) -> o.getItem() == Item.getItemFromBlock(this) && o.getItemDamage() == PortalFrameType.BASIC_FRAME.getID()))) {
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
				if (te instanceof IBlockPlacedListener) {
					((IBlockPlacedListener) te).onBlockPostPlaced(world, pos, state);
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		PortalFrameType type = state.getValue(TYPE_PROPERTY);
		if(type == PortalFrameType.BASIC_CONTROLLER) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof IEnergyTypeAware) {
				return EnergyManager.createItemStackWithEnergyType(this, 1, PortalFrameType.BASIC_CONTROLLER.getID(), ((IEnergyTypeAware) te).getEnergyType());
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
				}
				break;
			case BASIC_FRAME:
				if(te != null && te instanceof TileEntityPortalFrame) {
					((TileEntityPortalFrame) te).onDestroy();
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
}
