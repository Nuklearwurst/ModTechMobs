package com.fravokados.dangertech.techmobs.lib.util;

import com.fravokados.dangertech.core.item.IItemValidator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

/**
 * Utils for player
 * @author Nuklearwurst
 *
 */
public class PlayerUtils {

	@Nullable
	@Deprecated
	public static EntityPlayer getPlayerFromName(String username) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
	}

	@Nullable
	public static EntityPlayer getPlayerFromName(MinecraftServer server, String username) {
		return server.getPlayerList().getPlayerByUsername(username);
	}

	/**
	 * adapted from IChunUtils <br>
	 * <a href="https://github.com/iChun/iChunUtil/blob/master/src/main/java/ichun/common/core/EntityHelperBase.java">github:EntityHelperBase.java</a>
	 */
	public static NBTTagCompound getPersistentNBT(EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, nbt);
		return nbt;
	}

	public static ItemStack getCurrentUsablePlayerItem(EntityPlayer player, IItemValidator validator) {
		ItemStack out = player.getHeldItemMainhand();
		if(out != null && validator.isSearchedItem(out)) {
			return out;
		}
		out = player.getHeldItemOffhand();
		if(out != null && validator.isSearchedItem(out)) {
			return out;
		}
		return null;
	}
}
