package com.fravokados.techmobs.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

/**
 * Utils for player
 * @author Nuklearwurst
 *
 */
public class PlayerUtils {

	public static EntityPlayer getPlayerFromName(String username) {
		return MinecraftServer.getServer().getConfigurationManager().func_152612_a(username);
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
}
