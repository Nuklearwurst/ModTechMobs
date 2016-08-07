package com.fravokados.dangertech.monsters.world.techdata;

import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.ChunkDataEvent;

/**
 * contains TechData of a chunk<br>
 * 
 * the way these values are stored might change for more in depth information<br><br>
 * 
 * @author Nuklearwurst
 *
 */
public class TDChunk {
	
	/**
	 * NBT prefix for saving
	 */
	private static final String NBT_KEY = "techmobs.world.";

	/**
	 * how much technology is used in this sector <br>	 * 
	 * this is accurate every time it gets refreshed
	 */
	public int techLevel = 0;
	/**
	 * how much technology has been spotted by mobs <br>
	 * this will slowly catch up to the real tech level but won't be higher than it<br>
	 * 
	 */
	public int scoutedTechLevel = 0;
	
	/**
	 * save techdata for this chunk to disk
	 */
	public boolean save(ChunkDataEvent.Save evt) {
		//if its empty we don't need to save
		if(!hasData())
			return false;
		NBTTagCompound data = evt.getData();
		data.setInteger(NBT_KEY + "techLevel", techLevel);
		data.setInteger(NBT_KEY + "scoutedTechLevel", scoutedTechLevel);
		return true;
	}
	
	/**
	 * loads techdata for this chunk from disk
	 */
	public boolean load(ChunkDataEvent.Load evt) {
		NBTTagCompound data = evt.getData();
		//did we save data in this chunk?
		if(data.hasKey(NBT_KEY + "techLevel")) {
			techLevel = data.getInteger(NBT_KEY + "techLevel");
			scoutedTechLevel = data.getInteger(NBT_KEY + "scoutedTechLevel");
			if(scoutedTechLevel > TechDataStorage.getInstance().getDangerousChunkLevel()) {
				ChunkLocation chunk = new ChunkLocation(evt.getWorld(), evt.getChunk().getChunkCoordIntPair());
				TechDataStorage.getInstance().addDangerousChunk(chunk, techLevel);
			}
			return true;
		}		
		return false;
	}
	
	/**
	 * 
	 * @return true if this chunk contains techdata
	 */
	public boolean hasData() {
		return techLevel != 0;
	}
}
