package com.fravokados.dangertech.monsters.lib.util;

import com.fravokados.dangertech.core.item.IItemValidator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Utils for player
 *
 * @author Nuklearwurst
 */
public class PlayerUtils {

	/**
	 * Gets online player by username from given server
	 *
	 * @param username username
	 * @return Player entity
	 * @deprecated use {@link #getPlayerFromUUID(MinecraftServer, UUID)} instead.
	 */
	@Nullable
	@Deprecated
	public static EntityPlayer getPlayerFromName(String username) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
	}

	/**
	 * Gets online player by username from given server
	 *
	 * @param server   server
	 * @param username username
	 * @return Player entity
	 * @deprecated use {@link #getPlayerFromUUID(MinecraftServer, UUID)} instead.
	 */
	@Nullable
	@Deprecated
	public static EntityPlayer getPlayerFromName(MinecraftServer server, String username) {
		return server.getPlayerList().getPlayerByUsername(username);
	}


	/**
	 * Gets online player by username from given server
	 *
	 * @param server server
	 * @param id     player uuid
	 * @return Player entity
	 */
	@Nullable
	public static EntityPlayer getPlayerFromUUID(MinecraftServer server, UUID id) {
		return server.getPlayerList().getPlayerByUUID(id);
	}


	/**
	 * Gets online player by username from given server
	 *
	 * @param id player uuid
	 * @return Player entity
	 * @deprecated use server aware version instead: {@link #getPlayerFromUUID(MinecraftServer, UUID)}
	 */
	@Deprecated
	@Nullable
	public static EntityPlayer getPlayerFromUUID(UUID id) {
		return getPlayerFromUUID(FMLCommonHandler.instance().getMinecraftServerInstance(), id);
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
		if (!out.isEmpty()&& validator.isSearchedItem(out)) {
			return out;
		}
		out = player.getHeldItemOffhand();
		if (!out.isEmpty() && validator.isSearchedItem(out)) {
			return out;
		}
		return ItemStack.EMPTY;
	}
}
