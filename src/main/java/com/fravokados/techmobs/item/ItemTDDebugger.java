package com.fravokados.techmobs.item;

import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.values.TDValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemTDDebugger extends ItemTM {

	public ItemTDDebugger() {
		super(Strings.Item.TD_DEBUGGER);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
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
