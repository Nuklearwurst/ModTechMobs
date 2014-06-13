package com.fravokados.techmobs.techdata;

import net.minecraft.world.ChunkCoordIntPair;

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
	
	
	
	
	
	/**
	 * access the chunk data
	 * @param dimension
	 * @param coord
	 * @return
	 */
	public static TDChunk getChunkData(int dimension, ChunkCoordIntPair coord) {
		return TechDataStorage.getChunkData(coord, dimension);
	}
	
}
