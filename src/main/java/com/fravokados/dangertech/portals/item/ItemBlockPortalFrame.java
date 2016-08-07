package com.fravokados.dangertech.portals.item;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.core.plugin.energy.IEnergyTypeAware;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.lib.NBTKeys;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ItemBlockPortalFrame extends ItemMDBlockMultiType {

	public ItemBlockPortalFrame(BlockNW block) {
		super(block);
	}

	@Override
	public String getUnlocalizedNameForItem(ItemStack stack) {
		int meta = stack.getItemDamage();
		if(meta > PortalFrameType.values().length) {
			meta = 0;
		}
		switch (PortalFrameType.values()[meta]) {
			case BASIC_CONTROLLER:
				return Strings.Block.portalMachineController;
			case BASIC_FRAME:
				return Strings.Block.portalMachineFrame;
		}
		return Strings.Block.portalMachineBase;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
		if(nbt.hasKey(EnergyType.getNBTKey())) {
			EnergyType types = EnergyType.readFromNBT(nbt);
			//TODO translation
			list.add(Strings.translate(types.getUnlocalizedName()));
		}
		if(nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_NAME)) {
			list.add(Strings.translate(Strings.Tooltip.NAME) + " " + nbt.getString(NBTKeys.DESTINATION_CARD_PORTAL_NAME));
		} else if(nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID)) {
			list.add(Strings.translate(Strings.Tooltip.NAME + " " + Strings.Gui.CONTROLLER_NAME_UNNAMED));
		}
		if(nbt.hasKey("Upgrades")) {
			NBTTagList nbttaglist = nbt.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
			List<ItemStack> inv = new ArrayList<ItemStack>(nbttaglist.tagCount());
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
				byte slot = tag.getByte("Slot");
				if (slot >= 0) {
					inv.add(ItemStack.loadItemStackFromNBT(tag));
				}
			}
			int upgradeCount = 0;
			for(ItemStack item : inv) {
				upgradeCount += item.stackSize;
			}
			list.add(Strings.translateWithFormat(Strings.Tooltip.CONTROLLER_UPGRADES_INSTALLED, upgradeCount));
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if(super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null) {
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
			}
			return true;
		}
		return false;
	}

	@Override
	public void registerModels() {
		registerItemBlockModel(Textures.ITEM_BLOCK_PORTAL_FRAME, 0);
		registerItemBlockModel(Textures.ITEM_BLOCK_PORTAL_CONTROLLER, 1);
	}
}
