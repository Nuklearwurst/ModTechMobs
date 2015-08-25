package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.api.techdata.values.player.ITechdataItem;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.lib.util.EntityUtils;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemTDAnalyzer extends ItemTM implements ITechdataItem {

	private enum Mode {
		BLOCK, ITEM, PLAYER, CHUNK
	}

	public ItemTDAnalyzer() {
		super(Strings.Item.TD_ANALYZER);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (getItemMode(stack) == Mode.BLOCK) {
			if (!world.isRemote) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null) {
					player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerTileEntity, TDValues.getInstance().getTechDataForTileEntity(te)));
				} else {
					player.addChatMessage(new ChatComponentTranslation(Strings.Chat.analyzerTileEntityNotFound));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			int mode = getItemModeInt(stack);
			mode = (mode + 1) % Mode.values().length;
			setMode(stack, mode);
		} else {
			if (!world.isRemote && getItemMode(stack) == Mode.CHUNK) {
				player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerChunk, TDManager.getScoutedTechLevel(player.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(player.getPlayerCoordinates()))));
			} else if (world.isRemote && getItemMode(stack) == Mode.ITEM) {
				Entity entity = EntityUtils.rayTraceEntity(player);
				if (entity != null && entity instanceof EntityItem) {
					ItemStack itemStack = ((EntityItem) entity).getEntityItem();
					player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerItem, TDValues.getInstance().getTechDataForItem(itemStack)));
				}
			} else if (!world.isRemote && getItemMode(stack) == Mode.PLAYER) {
				player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerPlayer, player.getCommandSenderName(), TDManager.getPlayerScoutedTechLevel(player)));
			}
		}
		return stack;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if (getItemMode(stack) == Mode.PLAYER) {
			if (!player.getEntityWorld().isRemote) {
				if (entity instanceof EntityPlayer) {
					player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerPlayer, entity.getCommandSenderName(), TDManager.getPlayerScoutedTechLevel((EntityPlayer) entity)));
				} else {
					player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.analyzerPlayerNoPlayer));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack) + " <" + Strings.translate(Strings.Item.TD_ANALYZER_MODE_PREFIX + Strings.Item.TD_ANALYZER_MODES[getItemModeInt(stack)]) + ">";
	}

	public Mode getItemMode(ItemStack stack) {
		int i = getItemModeInt(stack);
		if (i > 0 && i < Mode.values().length) {
			return Mode.values()[i];
		} else {
			return Mode.BLOCK;
		}
	}

	public void setMode(ItemStack item, int mode) {
		ItemUtils.getNBTTagCompound(item).setByte("td_mode", (byte) mode);
	}

	public int getItemModeInt(ItemStack stack) {
		return ItemUtils.getNBTTagCompound(stack).getByte("td_mode");
	}

	@Override
	public int getTechValue(ItemStack stack) {
		return 10;
	}
}
