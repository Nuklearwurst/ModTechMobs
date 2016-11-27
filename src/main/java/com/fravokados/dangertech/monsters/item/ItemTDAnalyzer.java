package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.api.techdata.values.player.ITechdataItem;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.lib.util.EntityUtils;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.fravokados.dangertech.monsters.techdata.values.TDValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
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
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (getItemMode(player.getHeldItem(hand)) == Mode.BLOCK) {
			if (!world.isRemote) {
				TileEntity te = world.getTileEntity(pos);
				if (te != null) {
					player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerTileEntity, TDValues.getInstance().getTechDataForTileEntity(te)));
				} else {
					player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerTileEntityNotFound));
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && player.isSneaking()) {
			int mode = getItemModeInt(stack);
			mode = (mode + 1) % Mode.values().length;
			setMode(stack, mode);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		} else {
			if (!world.isRemote && getItemMode(stack) == Mode.CHUNK) {
				player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerChunk, TDManager.getScoutedTechLevel(player.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(player.getPosition()))));
			} else if (world.isRemote && getItemMode(stack) == Mode.ITEM) {
				Entity entity = EntityUtils.rayTraceEntity(player);
				if (entity != null && entity instanceof EntityItem) {
					ItemStack itemStack = ((EntityItem) entity).getEntityItem();
					player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerItem, TDValues.getInstance().getTechDataForItem(itemStack)));
				}
			} else if (!world.isRemote && getItemMode(stack) == Mode.PLAYER) {
				player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerPlayer, player.getName(), TDManager.getPlayerScoutedTechLevel(player)));
			} else {
				return new ActionResult<>(EnumActionResult.PASS, stack);
			}
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
		if (getItemMode(stack) == Mode.PLAYER) {
			if (!player.getEntityWorld().isRemote) {
				if (entity instanceof EntityPlayer) {
					player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerPlayer, entity.getName(), TDManager.getPlayerScoutedTechLevel((EntityPlayer) entity)));
				} else {
					player.sendMessage(new TextComponentTranslation(Strings.Chat.analyzerPlayerNoPlayer));
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
