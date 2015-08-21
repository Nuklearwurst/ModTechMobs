package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.mindim.block.BlockPortalFrame;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.lib.NBTKeys;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.api.upgrade.IUpgradable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ItemBlockPortalFrame extends ItemMDBlockMultiType {

	public ItemBlockPortalFrame(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedNameForItem(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case BlockPortalFrame.META_CONTROLLER_ENTITY:
				return Strings.Block.portalMachineController;
			case BlockPortalFrame.META_FRAME_ENTITY:
				return Strings.Block.portalMachineFrame;
		}
		return Strings.Block.portalMachineBase;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
		if(nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_NAME)) {
			list.add(Strings.translate(Strings.Tooltip.NAME) + " " + nbt.getString(NBTKeys.DESTINATION_CARD_PORTAL_NAME));
		} else if(nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID)) {
			list.add(Strings.translate(Strings.Tooltip.NAME + " " + Strings.Gui.CONTROLLER_NAME_UNNAMED));
		}
		if(nbt.hasKey("Upgrades")) {
			NBTTagList nbttaglist = nbt.getTagList("Upgrades", 10); //10 is compound type
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
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
			TileEntity te = world.getTileEntity(x, y, z);
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
				}
			}
			return true;
		}
		return false;
	}
}
