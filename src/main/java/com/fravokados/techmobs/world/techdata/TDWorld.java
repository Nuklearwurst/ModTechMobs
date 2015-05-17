package com.fravokados.techmobs.world.techdata;

import com.fravokados.techmobs.lib.util.LogHelper;
import net.minecraft.world.ChunkCoordIntPair;
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
	public final Map<ChunkCoordIntPair, TDChunk> chunkData;
	
	public TDWorld() {
		chunkData = new HashMap<ChunkCoordIntPair, TDChunk>();
	}
	
	/**
	 * saves chunkData to world
	 * @param evt
	 * @return
	 */
	public boolean saveChunkData(ChunkDataEvent.Save evt) {
		//save existing data
		if(chunkData.containsKey(evt.getChunk().getChunkCoordIntPair())) {
			return chunkData.get(evt.getChunk().getChunkCoordIntPair()).save(evt);
		}
		return false;
	}
	
	/**
	 * loads chunkData from disk
	 * @param evt
	 * @return
	 */
	public boolean loadChunkData(ChunkDataEvent.Load evt) {
		ChunkCoordIntPair pos = evt.getChunk().getChunkCoordIntPair();
		if(chunkData.containsKey(pos)) {
			LogHelper.warn("Something happened during chunk loading! This chunk was already loaded!");
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
	 * @param evt
	 */
	public void unloadChunk(ChunkEvent.Unload evt) {
		chunkData.remove(evt.getChunk().getChunkCoordIntPair());		
	}
	
	public TDChunk getChunk(ChunkCoordIntPair coords) {
		if(!chunkData.containsKey(coords)) {
			chunkData.put(coords, new TDChunk());
		}
		return chunkData.get(coords);
	}

}
