package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemTDDebugger extends ItemTM {

	public ItemTDDebugger() {
		super(Strings.Item.TD_DEBUGGER);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null) {
				player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.debuggerTileEntity, TDValues.getInstance().getTechDataForTileEntity(te)));
			} else {
				player.addChatMessage(new ChatComponentTranslation(Strings.Chat.debuggerTileEntityNotFound));
			}
		}
		return true;
	}


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.debuggerPlayerTechLevel, TDManager.getPlayerTechLevel(player)));
			player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.debuggerPlayerScouted, TDManager.getPlayerScoutedTechLevel(player)));
		}
		return stack;
	}
}
