package com.fravokados.techmobs.techdata;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
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

	public static int getScoutedTechLevel(int dimension, ChunkCoordIntPair coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}
	
	public static int getTechLevel(int dimension, ChunkCoordIntPair coord) {
		return getChunkData(dimension, coord).scoutedTechLevel;
	}
	
	public static void setScoutedTechLevel(int dimension, ChunkCoordIntPair coord, int value) {
		getChunkData(dimension, coord).scoutedTechLevel = value;
	}
	
	public static void setTechLevel(int dimension, ChunkCoordIntPair coord, int value) {
		getChunkData(dimension, coord).techLevel = value;
		if(value > TechDataStorage.getDangerousChunkLevel()) {
			ChunkLocation chunk = new ChunkLocation(dimension, coord);
			TechDataStorage.addDangerousChunk(chunk);
		}
	}
	
	public static void updateScoutedTechLevel(int dimension, ChunkCoordIntPair coord) {
		TDChunk data = getChunkData(dimension, coord);
		if(data.scoutedTechLevel > data.techLevel) {
			data.scoutedTechLevel = data.techLevel;
			return;
		}
		int step =  (int) (data.techLevel * Settings.TechScanning.SCOUTING_STEP_FACTOR);
		int dif = data.techLevel - data.scoutedTechLevel;
		if(dif < step) {
			step = dif;
		}
		data.scoutedTechLevel += step;
//		LogHelper.info("Updated Scouted TechLevel by " + step + " to " + data.scoutedTechLevel);
	}
	
	
	
	
	
	/**
	 * access the chunk data
	 * @param dimension
	 * @param coord
	 * @return
	 */
	public static TDChunk getChunkData(int dimension, ChunkCoordIntPair coord) {
		return TechDataStorage.getChunkData(coord, dimension);
	}

	public static void updateScoutedTechLevel(Chunk chunk) {
		updateScoutedTechLevel(chunk.worldObj.provider.dimensionId, chunk.getChunkCoordIntPair());
	}
	
}
