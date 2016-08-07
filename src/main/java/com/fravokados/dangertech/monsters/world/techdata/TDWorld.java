package com.fravokados.dangertech.monsters.world.techdata;

import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * contains chunk techdata
 * 
 * @author Nuklearwurst
 *
 */
public class TDWorld {
	
	/**
	 * contains chunk techdata
	 */
	public final Map<ChunkPos, TDChunk> chunkData;
	
	public TDWorld() {
		chunkData = new HashMap<ChunkPos, TDChunk>();
	}
	
	/**
	 * saves chunkData to world
	 */
	public boolean saveChunkData(ChunkDataEvent.Save evt) {
		//save existing data
		return chunkData.containsKey(evt.getChunk().getChunkCoordIntPair()) && chunkData.get(evt.getChunk().getChunkCoordIntPair()).save(evt);
	}
	
	/**
	 * loads chunkData from disk
	 */
	public boolean loadChunkData(ChunkDataEvent.Load evt) {
		ChunkPos pos = evt.getChunk().getChunkCoordIntPair();
		if(chunkData.containsKey(pos)) {
			LogHelperTM.warn("Something happened during chunk loading! This chunk was already loaded!");
		}
		//create new data
		TDChunk chunk = new TDChunk();
		//load data
		if(chunk.load(evt)) {
			chunkData.put(pos, chunk);
			return true;
		}
		return false;
	}
	
	/**
	 * check wether this world has techdata
	 * @return true if some techdata is saved for this world
	 */
	public boolean hasData() {
		return !chunkData.isEmpty();
	}

	/**
	 * unloads a chunk techdata entry of this world
	 */
	public void unloadChunk(ChunkEvent.Unload evt) {
		chunkData.remove(evt.getChunk().getChunkCoordIntPair());		
	}
	
	public TDChunk getChunk(ChunkPos coords) {
		if(!chunkData.containsKey(coords)) {
			chunkData.put(coords, new TDChunk());
		}
		return chunkData.get(coords);
	}

}
