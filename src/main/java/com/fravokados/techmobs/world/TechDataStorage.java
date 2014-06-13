package com.fravokados.techmobs.world;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.world.techdata.TDChunk;
import com.fravokados.techmobs.world.techdata.TDPlayer;
import com.fravokados.techmobs.world.techdata.TDWorld;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

/**
 * stores Techdata of all worlds and players
 * internal
 * 
 * @author Nuklearwurst
 *
 */
public class TechDataStorage {

	/** the folder, inside the dim folder, where our information gets saved to */
	private static final String LOCATION = "";

	/** the name of our save file */
	private static final String FILENAME = "techdata.dat";
	
	private static final String NBT_CHUNK_VALUE = "td_highest_chunk_value";
	private static final String NBT_PLAYER_VALUE = "td_highest_player_value";

	/**
	 * the world techdata
	 */
	private static Map<Integer, TDWorld> worldData = new HashMap<Integer, TDWorld>();
	
	/**
	 * the player data WIP
	 */
	private static Map<String, TDPlayer> playerData = new HashMap<String, TDPlayer>();
	
	/**
	 * chunks that have a high techvalue
	 */
	private static List<ChunkLocation> techChunks = new ArrayList<ChunkLocation>();
	
	/**
	 * techvalue of the highest chunk
	 */
	private static int highestChunkValue = 0;
	
	/**
	 * players with high techvalue
	 */
	private static List<String> techPlayers = new ArrayList<String>();
	
	/**
	 * techvalue of the best player
	 */
	private static int highestPlayerValue = 0;
	
	/** 
	 * save data
	 */ 
	private static NBTTagCompound saveData;
	
	/**
	 * returns the world data
	 * @param dimensionId
	 * @return
	 */
	private static TDWorld getWorldData(int dimensionId)  {
		if(!worldData.containsKey(dimensionId)) {
			worldData.put(dimensionId, new TDWorld());
		}
		return worldData.get(dimensionId);
	}
	
	/**
	 * internal use only
	 * 
	 * @param coords
	 * @param dimensionId
	 * @return
	 */
	public static TDChunk getChunkData(ChunkCoordIntPair coords, int dimensionId) {
		return getWorldData(dimensionId).getChunk(coords);
	}
	
	public static int getDangerousChunkLevel() {
		return (int) (highestChunkValue * Settings.TechScanning.DANGER_CHUNK_PERCENTAGE + Settings.TechScanning.DANGER_CHUNK_FLAT);
	}
	
	public static int getDangerousPlayerLevel() {
		return 0; //TODO
	}
	
	public static void addDangerousChunk(ChunkLocation chunk) {
		if(!techChunks.contains(chunk)) {
			techChunks.add(chunk);
		}
	}
	
	public static void addDangerousPlayer(String player) {
		if(!techPlayers.contains(player)) {
			techPlayers.add(player);
		}
	}

	/**
	 * loads player techData from disk
	 * @param evt
	 */
	public static void onWorldLoad(WorldEvent.Load evt) {
		//only on server side (--> intergrated server) && only on dim = 0 as it stores player data
		if(FMLCommonHandler.instance().getSide().isServer() && evt.world.provider.dimensionId == 0) {
			WorldServer world = (WorldServer) evt.world;
			try
			{
				//create the file
				File file = new File(world.getChunkSaveLocation(), LOCATION + FILENAME);
				if(!file.exists())
				{
					//file is missing
					//init default values
					saveData = new NBTTagCompound();
					if(world.getWorldInfo().getWorldTotalTime() > 0)
					{
						LogHelper.warn("Save file is missing!");
					}
				}
				else
				{
					//read data
					saveData = CompressedStreamTools.readCompressed(new FileInputStream(file));
					
					highestChunkValue = saveData.getInteger(NBT_CHUNK_VALUE);
					highestPlayerValue = saveData.getInteger(NBT_PLAYER_VALUE);
					//TODO finish saving and loading of player techdata
					//TODO player connect and disconnect saveing?
				}
			}
			catch(EOFException e)
			{
				LogHelper.error("Mod-Savefile is corrupted!");
				//we don't have backups
				saveData = new NBTTagCompound();
			}
			catch(IOException e)
			{
				LogHelper.error("Error reading Mod-Savefile!");
				//init default values
				saveData = new NBTTagCompound();
			}
		}
	}


	/**
	 * saves player techData to disk
	 * @param evt
	 */
	public static void onWorldSave(WorldEvent.Save evt) {
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() && evt.world.provider.dimensionId == 0)
		{
			WorldServer world = (WorldServer)evt.world;

			//write data
			//init nbttagcompound
			//keep exsisting data
			if(saveData == null) {
				saveData = new NBTTagCompound();
			}
			
			for(Map.Entry<String, TDPlayer> entry : playerData.entrySet()) {
				NBTTagCompound tag1 = new NBTTagCompound();
				//save the player techdata to a new nbttag
            	if(entry.getValue().save(tag1)) {
	            	//save the nbttag
            		saveData.setTag(entry.getKey() + "_techdata", tag1);
            	}
			}
			saveData.setInteger(NBT_CHUNK_VALUE, highestChunkValue);
			saveData.setInteger(NBT_PLAYER_VALUE, highestPlayerValue);

			try
			{
				if(world.getChunkSaveLocation().exists())
				{
					File file = new File(world.getChunkSaveLocation(), LOCATION + FILENAME);
					//we don't create backups TODO backup file
					CompressedStreamTools.writeCompressed(saveData, new FileOutputStream(file));
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException("Failed to save techdata");
			}
		}
	}

	/**
	 * saves chunk techdata to disk
	 * @param evt
	 */
	public static void onChunkDataSave(ChunkDataEvent.Save evt) {
		int dimId = evt.world.provider.dimensionId;
		if(worldData.containsKey(dimId)) {
			worldData.get(dimId).saveChunkData(evt); //save here
		}
	}

	/**
	 * loads chunk techdata from disk
	 * @param evt
	 */
	public static void onChunkDataLoad(ChunkDataEvent.Load evt) {
		int dimId = evt.world.provider.dimensionId;
		if(worldData.containsKey(dimId)) {
			//load from disk and add to already loaded world
			worldData.get(dimId).loadChunkData(evt); // load data here
		} else {
			//try to load from disk and add the new World
			TDWorld world = new TDWorld();
			if(world.loadChunkData(evt)) {
				//when loading is successful add world data to the list
				worldData.put(dimId, world);
			}
		}
	}

	/**
	 * removes chunk from storage (--> it got unloaded and is not needed anymore)
	 * TODO check if data is also saved when the chunk didn't change, but the techdata did
	 * 
	 * @param evt
	 */
	public static void onChunkUnload(ChunkEvent.Unload evt) {
		//unload chunk if data exists for the given world
		int dimId = evt.world.provider.dimensionId;
		if(worldData.containsKey(dimId)) {
			TDWorld world = worldData.get(dimId);
			world.unloadChunk(evt);
			//remove world if it contains no information
			if(!world.hasData()) {
				worldData.remove(dimId);
			}
		}
	}

	/**
	 * unused
	 * @param evt
	 */
	public static void onChunkLoad(ChunkEvent.Load evt) {

	}


	/**
	 * loads the player techdata
	 * @param evt
	 */
	public static void onPlayerLogin(PlayerLoggedInEvent evt) {
		//load data
		String username = evt.player.getCommandSenderName();
		if(saveData.hasKey(username + "_techdata")) {
			if(playerData.containsKey(username + "_techdata")) {
				//this shouldn't happen
				LogHelper.error("Player Data already loaded! Conflict! Overwriting!");
			}
			TDPlayer player = new TDPlayer();
			if(player.load(saveData.getCompoundTag(username + "_techdata"))) {
				//successfully loaded data
				playerData.put(username, player);				
			}
			//TODO mostwanted list
		}
	}


	/**
	 * unloads and saves the player techdata
	 * @param evt
	 */
	public static void onPlayerLogout(PlayerLoggedOutEvent evt) {
		//unload data
		String username = evt.player.getCommandSenderName();
		if(playerData.containsKey(username)) {
			//tag to save data
			NBTTagCompound tag1 = new NBTTagCompound();
			//get the player data
			TDPlayer player = playerData.get(username);
			//unload the player
			playerData.remove(username); //maybe not necessary
			//save the player data
			if(player.save(tag1)) {
				saveData.setTag(username + "_techdata", tag1);
			}
			//TODO mostwanted list unload
		}
	}

}
