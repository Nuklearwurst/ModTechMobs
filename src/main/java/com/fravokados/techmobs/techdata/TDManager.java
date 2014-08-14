package com.fravokados.techmobs.techdata;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.PlayerUtils;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.values.TDValues;
import com.fravokados.techmobs.techdata.values.player.TDEntryItem;
import com.fravokados.techmobs.world.TechDataStorage;
import com.fravokados.techmobs.world.techdata.TDChunk;


/**
 * This is used to modify the TechData
 * TDManager provides functions to easily manipulate TData in different ways
 * 
 * @author Nuklearwurst
 *
 */
public class TDManager {
	
	//Player NBT tags
	private static final String NBT_PLAYER_SAVE = "techmobs.save";
	private static final String NBT_PLAYER_TECHDATA= "techdata";
	private static final String NBT_PLAYER_TECHDATA_SCOUTED = "scouted.techdata";

	public static int getScoutedTechLevel(int dimension, ChunkCoordIntPair coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}

	public static int getTechLevel(int dimension, ChunkCoordIntPair coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}

	public static void setScoutedTechLevel(int dimension, ChunkCoordIntPair coord, int value) {
		getChunkData(dimension, coord).scoutedTechLevel = value;
		TechDataStorage.updateDangerousChunkList(new ChunkLocation(dimension, coord), value);
	}

	public static void setTechLevel(int dimension, ChunkCoordIntPair coord, int value) {
		getChunkData(dimension, coord).techLevel = value;
	}

	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 * @param dimension
	 * @param coord
	 */
	public static void updateScoutedTechLevel(int dimension, ChunkCoordIntPair coord) {
		TDChunk data = getChunkData(dimension, coord);
		if(data.scoutedTechLevel > data.techLevel) {
			data.scoutedTechLevel = data.techLevel;
			return;
		}
		int step =  (int) (data.techLevel * Settings.TechScanning.SCOUTING_STEP_FACTOR_WORLD);
		int dif = data.techLevel - data.scoutedTechLevel;
		if(dif < step) {
			step = dif;
		}
		setScoutedTechLevel(dimension, coord, data.scoutedTechLevel + step);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 * @param chunk
	 */
	public static void updateScoutedTechLevel(Chunk chunk) {
		updateScoutedTechLevel(chunk.worldObj.provider.dimensionId, chunk.getChunkCoordIntPair());
	}


	/**
	 * scans a players inventory and sets the techlevel
	 * @param player
	 */
	public static void scanPlayer(EntityPlayer player) {
		int value = 0;
		for(ItemStack stack : player.inventory.armorInventory) {
			if(stack != null) {
				TDEntryItem item = TDValues.itemEntries.get(stack.getItem());
				if(item != null) {
					value += item.getTechLevelForItem(stack);
				}
			}
		}
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null) {
				TDEntryItem item = TDValues.itemEntries.get(stack.getItem());
				if(item != null) {
					value += item.getTechLevelForItem(stack);
				}
			}
		}
		TDManager.setPlayerTechLevel(player, value);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 * @param player
	 * @param data
	 */
	public static void updatePlayerScoutedTechLevel(EntityPlayer player) {
		int scouted = getPlayerScoutedTechLevel(player);
		int level = getPlayerTechLevel(player);
		//maximum increase
		int step =  (int) (level * Settings.TechScanning.SCOUTING_STEP_FACTOR_PLAYER);
		//note: if scouted techlevel is greater than the real level it gets decreased
		int dif = level - scouted;
		if(dif < step) {
			step = dif;
		}
		setPlayerScoutedTechLevel(player, scouted + step);
	}
	
	/**
	 * updates player scouted techdata and scans if necessary
	 * also adds player to the techplayers list if necessary
	 * @param username
	 */
	public static void scanAndUpdatePlayerTD(EntityPlayer player) {
		if(getPlayerScoutedTechLevel(player) >= getPlayerTechLevel(player)) {
			scanPlayer(player);
		}
		updatePlayerScoutedTechLevel(player);
	}

	public static void setPlayerTechLevel(EntityPlayer player, int level) {
		getPlayerData(player).setInteger(NBT_PLAYER_TECHDATA, level);
	}
	
	public static void setPlayerScoutedTechLevel(EntityPlayer player, int scoutedLevel) {
		getPlayerData(player).setInteger(NBT_PLAYER_TECHDATA_SCOUTED, scoutedLevel);
		TechDataStorage.updateDangerousPlayerList(player.getCommandSenderName(), scoutedLevel);
	}
	
	public static int getPlayerTechLevel(EntityPlayer player) {
		return getPlayerData(player).getInteger(NBT_PLAYER_TECHDATA);
	}
	
	public static int getPlayerScoutedTechLevel(EntityPlayer player) {
		return getPlayerData(player).getInteger(NBT_PLAYER_TECHDATA_SCOUTED);
	}



	/**
	 * access the chunk data
	 * @param dimension
	 * @param coord
	 * @return
	 */
	@Deprecated
	public static TDChunk getChunkData(int dimension, ChunkCoordIntPair coord) {
		return TechDataStorage.getChunkData(coord, dimension);
	}

	/**
	 * @param player
	 * @return
	 */
	public static NBTTagCompound getPlayerData(EntityPlayer player) {
		NBTTagCompound nbt = PlayerUtils.getPersistentNBT(player).getCompoundTag(NBT_PLAYER_SAVE);
		PlayerUtils.getPersistentNBT(player).setTag(NBT_PLAYER_SAVE, nbt);
		return nbt;
	}
}
