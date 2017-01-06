package com.fravokados.dangertech.monsters.techdata;

import com.fravokados.dangertech.api.core.util.ChunkLocation;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.lib.util.PlayerUtils;
import com.fravokados.dangertech.monsters.techdata.values.TDValues;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import com.fravokados.dangertech.monsters.world.techdata.TDChunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;


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

	public static int getScoutedTechLevel(int dimension, ChunkPos coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}

	public static int getTechLevel(int dimension, ChunkPos coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}

	public static void setScoutedTechLevel(int dimension, ChunkPos coord, int value) {
		getChunkData(dimension, coord).scoutedTechLevel = value;
		TechDataStorage.getInstance().updateDangerousChunkList(new ChunkLocation(dimension, coord), value);
	}

	public static void setTechLevel(int dimension, ChunkPos coord, int value) {
		getChunkData(dimension, coord).techLevel = value;
	}

	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 */
	public static void updateScoutedTechLevel(int dimension, ChunkPos coord) {
		TDChunk data = getChunkData(dimension, coord);
		int step = 0;
		int dif = data.techLevel - data.scoutedTechLevel;
		if(dif < 0) {
			step = -(int) (data.scoutedTechLevel * Settings.TechScanning.SCOUTING_STEP_FACTOR_WORLD);
			if(dif > step) {
				step = dif;
			}
		} else {
			step = (int) (data.techLevel * Settings.TechScanning.SCOUTING_STEP_FACTOR_WORLD);
			if (dif < step) {
				step = dif;
			}
		}
		setScoutedTechLevel(dimension, coord, data.scoutedTechLevel + step);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
	 */
	public static void updateScoutedTechLevel(Chunk chunk) {
		updateScoutedTechLevel(chunk.getWorld().provider.getDimension(), chunk.getChunkCoordIntPair());
	}


	/**
	 * scans a players inventory and sets the techlevel
	 */
	public static void scanPlayer(EntityPlayer player) {
		int value = 0;
		for(ItemStack stack : player.inventory.armorInventory) {
			if(stack != null) {
				value += TDValues.getInstance().getTechDataForItem(stack);
			}
		}
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null) {
				value += TDValues.getInstance().getTechDataForItem(stack);
			}
		}
		TDManager.setPlayerTechLevel(player, value);
	}
	
	/**
	 * updates (increases) the scouted techlevel by a value set in the configs
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
		TechDataStorage.getInstance().updateDangerousPlayerList(player.getUniqueID(), scoutedLevel);
	}
	
	public static int getPlayerTechLevel(EntityPlayer player) {
		return getPlayerData(player).getInteger(NBT_PLAYER_TECHDATA);
	}
	
	public static int getPlayerScoutedTechLevel(EntityPlayer player) {
		return getPlayerData(player).getInteger(NBT_PLAYER_TECHDATA_SCOUTED);
	}



	/**
	 * access the chunk data
	 */
	private static TDChunk getChunkData(int dimension, ChunkPos coord) {
		return TechDataStorage.getInstance().getChunkData(coord, dimension);
	}

	/**
	 * gets or creates the player persistent nbt
	 * @param player the player to retrieve the nbt from
	 * @return NBT data of the given player
	 */
	public static NBTTagCompound getPlayerData(EntityPlayer player) {
		NBTTagCompound nbt = PlayerUtils.getPersistentNBT(player).getCompoundTag(NBT_PLAYER_SAVE);
		PlayerUtils.getPersistentNBT(player).setTag(NBT_PLAYER_SAVE, nbt);
		return nbt;
	}
}
