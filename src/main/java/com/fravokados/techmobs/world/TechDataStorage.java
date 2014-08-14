package com.fravokados.techmobs.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.TDManager;
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
public class TechDataStorage extends WorldSavedData {

	/** the folder, inside the dim folder, where our information gets saved to */
	private static final String LOCATION = "";

	/** the name of our save file */
	private static final String FILENAME = "techdata.dat";
	
	// nbt keys
	private static final String NBT_CHUNK_VALUE = "td_highest_chunk_value";
	private static final String NBT_PLAYER_VALUE = "td_highest_player_value";
	
	private static final String SAVE_DATA_NAME = "TechmobsStorage";

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
	 * techvalue of the highest chunk (not exact)
	 */
	private static int highestChunkValue = 0;
	
	/**
	 * players with high techvalue
	 */
	private static List<String> techPlayers = new ArrayList<String>();
	
	/**
	 * techvalue of the best player (not exact)
	 */
	private static int highestPlayerValue = 0;
	
	/** 
	 * save data
	 */ 
	private static NBTTagCompound saveData;
	
	//SAVING
	public TechDataStorage(String name) {
		super(name);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(NBT_CHUNK_VALUE, highestChunkValue);
		nbt.setInteger(NBT_PLAYER_VALUE, highestPlayerValue);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		highestChunkValue = nbt.getInteger(NBT_CHUNK_VALUE);
		highestPlayerValue = nbt.getInteger(NBT_PLAYER_VALUE);
	}
	//END SAVING
	
	
	
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
	
	public static TDChunk getChunkData(ChunkLocation loc) {
		return getChunkData(loc.getChunkCoordIntPair(), loc.dimension);
	}
	
	public static TDPlayer getPlayerData(String username) {
		if(!playerData.containsKey(username)) {
			if(saveData != null && saveData.hasKey(username)) {
				LogHelper.warn("Somthing went wrong when loading player data, but we found our save!");
				TDPlayer data = new TDPlayer();
				data.load(saveData.getCompoundTag(username));
				playerData.put(username, data);
			} else {
				playerData.put(username, new TDPlayer());
			}
		}
		return playerData.get(username);
	}
	
	public static int getDangerousChunkLevel() {
		return (int) (highestChunkValue * Settings.TechScanning.DANGER_CHUNK_PERCENTAGE + Settings.TechScanning.DANGER_CHUNK_FLAT);
	}
	
	public static int getDangerousPlayerLevel() {
		return (int) (highestPlayerValue * Settings.TechScanning.DANGER_PLAYER_PERCENTAGE + Settings.TechScanning.DANGER_PLAYER_FLAT);
	}
	
	public static void addDangerousChunk(ChunkLocation chunk, int level) {
		if(!techChunks.contains(chunk)) {
			techChunks.add(chunk);
			if(level > highestChunkValue) {
				highestChunkValue = level;
			}
		}		
	}
	
	public static void addDangerousPlayer(String player, int level) {
		if(!techPlayers.contains(player)) {
			techPlayers.add(player);
			if(level > highestChunkValue) {
				highestChunkValue = level;
			}
		}		
	}
	
	public static void removeDangerousChunk(ChunkLocation loc) {
		int index = techChunks.indexOf(loc);
		if(index != -1) {
			//if this was the last element of this list (--> the newest)
			//it should have a very high techdata value
			//therefore the highestChunkValue gets updated (--> the updated number might not represent reality)
			if(techChunks.size() == index + 1) {
				if(index > 0) {
					ChunkLocation chunk = techChunks.get(index - 1);
					highestChunkValue = TDManager.getScoutedTechLevel(chunk.dimension, chunk.getChunkCoordIntPair());
				} else {
					highestChunkValue = 0;
				}
			}
			//remove the chunk
			techChunks.remove(index);
		}
	}
	
	public static void removeDangerousPlayer(String player) {
		int index = techPlayers.indexOf(player);
		if(index != -1) {
			//if this was the last element of this list (--> the newest)
			//it should have a very high techdata value
			//therefore the highestPlayerValue gets updated (--> the updated number might not represent reality)
			if(techPlayers.size() == index + 1) {
				if(index > 0) {
					//insert the player techvalue of the most recent player added to the dangerousPlayer list
					String name = techPlayers.get(index - 1);
					highestPlayerValue = TDManager.getPlayerTechLevel(name);
				} else {
					highestPlayerValue = 0;
				}
			}
			//remove the chunk
			techPlayers.remove(index);
		}
	}
	
	public static void updateDangerousChunkList(ChunkLocation loc, int level) {
		if(techChunks.contains(loc)) {
			if(level >= getDangerousChunkLevel()) {
				return;
			} else {
				removeDangerousChunk(loc);
				addDangerousChunkIfNeeded(loc, level);
			}
		} else {
			addDangerousChunkIfNeeded(loc, level);
		}
	}
	
	public static void updateDangerousPlayerList(String name, int level) {
		if(techPlayers.contains(name)) {
			if(level >= getDangerousPlayerLevel()) {
				return;
			} else {
				removeDangerousPlayer(name);
				addDangerousPlayerIfNeeded(name, level);
			}
		} else {
			addDangerousPlayerIfNeeded(name, level);
		}
	}
	
	public static void addDangerousChunkIfNeeded(ChunkLocation chunk, int level) {
		if(level > getDangerousChunkLevel()) {
			addDangerousChunk(chunk, level);
		}
	}
	
	public static void addDangerousPlayerIfNeeded(String player, int level) {
		if(level > getDangerousPlayerLevel()) {
			addDangerousPlayer(player, level);
		}
	}
	
	public static String getRandomDangerousPlayer(Random rand) {
		if(techPlayers.isEmpty()) {
			return null;
		}
		return techPlayers.get(rand.nextInt(techPlayers.size()));
	}
	
	public static ChunkLocation getRandomDangerousChunk(Random rand) {
		if(techChunks.isEmpty()) {
			return null;
		}
		return techChunks.get(rand.nextInt(techChunks.size()));
	}

	/**
	 * loads player techData from disk
	 * @param evt
	 */
	public static void onWorldLoad(WorldEvent.Load evt) {
		//only on server side (--> intergrated server) && only on dim = 0 as it stores player data
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() && evt.world.provider.dimensionId == 0) {
			WorldServer world = (WorldServer) evt.world;
			TechDataStorage data = (TechDataStorage) world.perWorldStorage.loadData(TechDataStorage.class, SAVE_DATA_NAME);
			
			if(data == null) {
				data = new TechDataStorage(SAVE_DATA_NAME);
				world.perWorldStorage.setData(SAVE_DATA_NAME, data);
			}
//			try
//			{
//				//create the file
//				File file = new File(world.getChunkSaveLocation(), LOCATION + FILENAME);
//				if(!file.exists())
//				{
//					//file is missing
//					//init default values
//					saveData = new NBTTagCompound();
//					if(world.getWorldInfo().getWorldTotalTime() > 0)
//					{
//						LogHelper.warn("Save file is missing!");
//					}
//				}
//				else
//				{
//					//read data
//					saveData = CompressedStreamTools.readCompressed(new FileInputStream(file));
//					
//					highestChunkValue = saveData.getInteger(NBT_CHUNK_VALUE);
//					highestPlayerValue = saveData.getInteger(NBT_PLAYER_VALUE);
//					//TODO finish saving and loading of player techdata
//					//TODO player connect and disconnect saveing?
//					for(EntityPlayer player : (List<EntityPlayer>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
//						String username = player.getCommandSenderName();
//						if(saveData.hasKey(username + "_techdata")) {
//							if(playerData.containsKey(username + "_techdata")) {
//								//this shouldn't happen
//								LogHelper.error("Player Data already loaded! Conflict! Overwriting!");
//							}
//							TDPlayer data = new TDPlayer();
//							if(data.load(saveData.getCompoundTag(username + "_techdata"))) {
//								//successfully loaded data
//								playerData.put(username, data);	
//								addDangerousPlayerIfNeeded(username, data.scoutedTechLevel);
//							} else {
//								LogHelper.error("Error loading player save!");
//							}
//						}
//					}
//				}
//			}
//			catch(EOFException e)
//			{
//				LogHelper.error("Mod-Savefile is corrupted!");
//				//we don't have backups
//				saveData = new NBTTagCompound();
//			}
//			catch(IOException e)
//			{
//				LogHelper.error("Error reading Mod-Savefile!");
//				//init default values
//				saveData = new NBTTagCompound();
//			}
		}
	}


	/**
	 * saves player techData to disk
	 * @param evt
	 */
	public static void onWorldSave(WorldEvent.Save evt) {
//		if(FMLCommonHandler.instance().getEffectiveSide().isServer() && evt.world.provider.dimensionId == 0)
//		{
//			WorldServer world = (WorldServer)evt.world;
//
//			//write data
//			//init nbttagcompound
//			//keep exsisting data
//			if(saveData == null) {
//				saveData = new NBTTagCompound();
//			}
//			
//			for(Map.Entry<String, TDPlayer> entry : playerData.entrySet()) {
//				NBTTagCompound tag1 = new NBTTagCompound();
//				//save the player techdata to a new nbttag
//            	if(entry.getValue().save(tag1)) {
//	            	//save the nbttag
//            		saveData.setTag(entry.getKey() + "_techdata", tag1);
//            	}
//			}
//			saveData.setInteger(NBT_CHUNK_VALUE, highestChunkValue);
//			saveData.setInteger(NBT_PLAYER_VALUE, highestPlayerValue);
//
//			try
//			{
//				if(world.getChunkSaveLocation().exists())
//				{
//					File file = new File(world.getChunkSaveLocation(), LOCATION + FILENAME);
//					//we don't create backups TODO backup file
//					CompressedStreamTools.writeCompressed(saveData, new FileOutputStream(file));
//					LogHelper.info("Saved data.");
//				}
//			}
//			catch(IOException e)
//			{
//				e.printStackTrace();
//				throw new RuntimeException("Failed to save techdata");
//			}
//		}
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
				addDangerousPlayerIfNeeded(username, player.scoutedTechLevel);
			} else {
				LogHelper.error("Error loading player save!");
			}
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
			removeDangerousPlayer(username);
			//save the player data
			if(player.save(tag1)) {
				saveData.setTag(username + "_techdata", tag1);
			}
			LogHelper.info("Saving player data.");
		}
	}

}
