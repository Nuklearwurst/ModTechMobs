package com.fravokados.techmobs.techdata;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.values.TDValues;
import com.fravokados.techmobs.techdata.values.player.TDEntryItem;
import com.fravokados.techmobs.world.TechDataStorage;
import com.fravokados.techmobs.world.techdata.TDChunk;
import com.fravokados.techmobs.world.techdata.TDPlayer;


/**
 * This is used to modify the TechData
 * TDManager provides functions to easily manipulate TData in different ways
 * 
 * @author Nuklearwurst
 *
 */
public class TDManager {

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
		TDManager.setPlayerTechLevel(player.getCommandSenderName(), value);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 * @param username
	 * @param data
	 */
	public static void updatePlayerScoutedTechLevel(String username, TDPlayer data) {
		if(data.scoutedTechLevel > data.techLevel) {
			data.scoutedTechLevel = data.techLevel;
			return;
		}
		int step =  (int) (data.techLevel * Settings.TechScanning.SCOUTING_STEP_FACTOR_PLAYER);
		int dif = data.techLevel - data.scoutedTechLevel;
		if(dif < step) {
			step = dif;
		}
		setPlayerScoutedTechLevel(username, data.scoutedTechLevel + step);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 * @param username
	 */
	public static void updatePlayerScoutedTechLevel(String username) {
		updatePlayerScoutedTechLevel(username, getPlayerData(username));
	}
	
	/**
	 * updates player scouted techdata and scans if necessary
	 * also adds player to the techplayers list if necessary
	 * @param username
	 */
	public static void scanAndUpdatePlayerTD(EntityPlayer player) {
		TDPlayer data = getPlayerData(player.getCommandSenderName());
		if(data.scoutedTechLevel >= data.techLevel) {
			scanPlayer(player);
		}
		updatePlayerScoutedTechLevel(player.getCommandSenderName(), data);
		TechDataStorage.updateDangerousPlayerList(player.getCommandSenderName(), data.scoutedTechLevel);
	}

	public static void setPlayerTechLevel(String username, int level) {
		getPlayerData(username).techLevel = level;
	}
	
	public static void setPlayerScoutedTechLevel(String username, int scoutedLevel) {
		getPlayerData(username).scoutedTechLevel = scoutedLevel;
		TechDataStorage.updateDangerousPlayerList(username, scoutedLevel);
	}
	
	public static int getPlayerTechLevel(String username) {
		return getPlayerData(username).techLevel;
	}
	
	public static int getPlayerScoutedTechLevel(String username) {
		return getPlayerData(username).scoutedTechLevel;
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

	@Deprecated
	public static TDPlayer getPlayerData(String username) {
		return TechDataStorage.getPlayerData(username);
	}
}
