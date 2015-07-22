package com.fravokados.dangertech.techmobs.world;

import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.techmobs.configuration.Settings;
import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.lib.util.PlayerUtils;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.world.techdata.TDChunk;
import com.fravokados.dangertech.techmobs.world.techdata.TDWorld;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.util.*;

/**
 * stores Techdata of all worlds and players
 * internal
 *
 * @author Nuklearwurst
 */
public class TechDataStorage extends WorldSavedData {

	private static TechDataStorage instance;

	private static final String SAVE_DATA_NAME = "DangerousTechnologyStorage";

	/**
	 * the world techdata
	 */
	private final Map<Integer, TDWorld> worldData = new HashMap<Integer, TDWorld>();

	/**
	 * chunks that have a high techvalue
	 */
	private final List<ChunkLocation> techChunks = new ArrayList<ChunkLocation>();

	/**
	 * techvalue of the highest chunk (not exact)
	 */
	private int highestChunkValue = 0;

	/**
	 * players with high techvalue
	 */
	private final List<String> techPlayers = new ArrayList<String>();

	/**
	 * techvalue of the best player (not exact)
	 */
	private int highestPlayerValue = 0;

	//SAVING
	public TechDataStorage(String name) {
		super(name);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		//saving highest techValues not really needed
		//--> chunks/players get removed from list on unload
		//and readded un load
		//function kept for future uses
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}
	//END SAVING


	/**
	 * returns the world data
	 *
	 */
	private TDWorld getWorldData(int dimensionId) {
		if (!worldData.containsKey(dimensionId)) {
			worldData.put(dimensionId, new TDWorld());
		}
		return worldData.get(dimensionId);
	}

	/**
	 * internal use only
	 *
	 */
	public TDChunk getChunkData(ChunkCoordIntPair coords, int dimensionId) {
		return getWorldData(dimensionId).getChunk(coords);
	}

	public TDChunk getChunkData(ChunkLocation loc) {
		return getChunkData(loc.getChunkCoordIntPair(), loc.dimension);
	}

	public int getDangerousChunkLevel() {
		return (int) (highestChunkValue * Settings.TechScanning.DANGER_CHUNK_PERCENTAGE + Settings.TechScanning.DANGER_CHUNK_FLAT);
	}

	public int getDangerousPlayerLevel() {
		return (int) (highestPlayerValue * Settings.TechScanning.DANGER_PLAYER_PERCENTAGE + Settings.TechScanning.DANGER_PLAYER_FLAT);
	}

	public void addDangerousChunk(ChunkLocation chunk, int level) {
		if (!techChunks.contains(chunk)) {
			techChunks.add(chunk);
			if (level > highestChunkValue) {
				highestChunkValue = level;
			}
		}
	}

	public void addDangerousPlayer(String player, int level) {
		if (!techPlayers.contains(player)) {
			techPlayers.add(player);
			if (level > highestChunkValue) {
				highestChunkValue = level;
			}
		}
	}

	public void removeDangerousChunk(ChunkLocation loc) {
		int index = techChunks.indexOf(loc);
		if (index != -1) {
			//if this was the last element of this list (--> the newest)
			//it should have a very high techdata value
			//therefore the highestChunkValue gets updated (--> the updated number might not represent reality)
			if (techChunks.size() == index + 1) {
				if (index > 0) {
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

	public void removeDangerousPlayer(String player) {
		int index = techPlayers.indexOf(player);
		if (index != -1) {
			//if this was the last element of this list (--> the newest)
			//it should have a very high techdata value
			//therefore the highestPlayerValue gets updated (--> the updated number might not represent reality)
			if (techPlayers.size() <= 1) {
				highestPlayerValue = 0;
			} else if (techPlayers.size() == index + 1) {
				//insert the player techvalue of the most recent player added to the dangerousPlayer list
				String name = techPlayers.get(index - 1);
				highestPlayerValue = TDManager.getPlayerTechLevel(PlayerUtils.getPlayerFromName(name));
			}
			//remove the chunk
			techPlayers.remove(index);
		}
	}

	public void updateDangerousChunkList(ChunkLocation loc, int level) {
		if (techChunks.contains(loc)) {
			if (level < getDangerousChunkLevel()) {
				removeDangerousChunk(loc);
				addDangerousChunkIfNeeded(loc, level);
			}
		} else {
			addDangerousChunkIfNeeded(loc, level);
		}
	}

	public void updateDangerousPlayerList(String name, int level) {
		if (techPlayers.contains(name)) {
			if (level < getDangerousPlayerLevel()) {
				removeDangerousPlayer(name);
				addDangerousPlayerIfNeeded(name, level);
			}
		} else {
			addDangerousPlayerIfNeeded(name, level);
		}
	}

	public void addDangerousChunkIfNeeded(ChunkLocation chunk, int level) {
		if (level > getDangerousChunkLevel()) {
			addDangerousChunk(chunk, level);
		}
	}

	public void addDangerousPlayerIfNeeded(String player, int level) {
		if (level > getDangerousPlayerLevel()) {
			addDangerousPlayer(player, level);
		}
	}

	public String getRandomDangerousPlayer(Random rand) {
		if (techPlayers.isEmpty()) {
			return null;
		}
		return techPlayers.get(rand.nextInt(techPlayers.size()));
	}

	public ChunkLocation getRandomDangerousChunk(Random rand) {
		if (techChunks.isEmpty()) {
			return null;
		}
		return techChunks.get(rand.nextInt(techChunks.size()));
	}

	public boolean isDangerousChunk(ChunkLocation chunk) {
		return techChunks.contains(chunk);
	}

	public boolean isDangerousPlayer(String username) {
		return techPlayers.contains(username);
	}

	/**
	 * loads player techData from disk
	 */
	public static void onWorldLoad(WorldEvent.Load evt) {
		//only on server side (--> intergrated server) && only on dim = 0 as it stores player data
		if (!evt.world.isRemote && evt.world.provider.dimensionId == 0) {
			WorldServer world = (WorldServer) evt.world;
			TechDataStorage data = (TechDataStorage) world.perWorldStorage.loadData(TechDataStorage.class, SAVE_DATA_NAME);

			if (data == null) {
				data = new TechDataStorage(SAVE_DATA_NAME);
				world.perWorldStorage.setData(SAVE_DATA_NAME, data);
			}

			if (instance != null) {
				throw new IllegalStateException("TechDataStorage already loaded! (This is a programming Error)");
			}
			instance = data;
		}
	}


	public static void onWorldUnload(WorldEvent.Unload evt) {
		if (!evt.world.isRemote && evt.world.provider.dimensionId == 0) {
			LogHelperTM.info("Unloading TechDataStorage...");
			instance = null;
		}
	}


	/**
	 * saves chunk techdata to disk
	 */
	public static void onChunkDataSave(ChunkDataEvent.Save evt) {
		if(!evt.world.isRemote) {
			int dimId = evt.world.provider.dimensionId;
			if (getInstance().worldData.containsKey(dimId)) {
				getInstance().worldData.get(dimId).saveChunkData(evt); //save here
			}
		}
	}

	/**
	 * loads chunk techdata from disk
	 */
	public static void onChunkDataLoad(ChunkDataEvent.Load evt) {
		if(!evt.world.isRemote) {
			int dimId = evt.world.provider.dimensionId;
			if (getInstance().worldData.containsKey(dimId)) {
				//load from disk and add to already loaded world
				getInstance().worldData.get(dimId).loadChunkData(evt); // load data here
			} else {
				//try to load from disk and add the new World
				TDWorld world = new TDWorld();
				if (world.loadChunkData(evt)) {
					//when loading is successful add world data to the list
					getInstance().worldData.put(dimId, world);
				}
			}
		}
	}

	/**
	 * removes chunk from storage (--> it got unloaded and is not needed anymore)
	 */
	public static void onChunkUnload(ChunkEvent.Unload evt) {
		if(!evt.world.isRemote) {
			//unload chunk if data exists for the given world
			int dimId = evt.world.provider.dimensionId;
			if (getInstance().worldData.containsKey(dimId)) {
				TDWorld world = getInstance().worldData.get(dimId);
				world.unloadChunk(evt);
				//remove world if it contains no information
				if (!world.hasData()) {
					getInstance().worldData.remove(dimId);
				}
			}
		}
	}


	/**
	 * loads the player techdata
	 */
	public static void onPlayerLogin(PlayerLoggedInEvent evt) {
		//add player to techPlayersList if necessary
		if(!evt.player.worldObj.isRemote) {
			getInstance().addDangerousPlayerIfNeeded(evt.player.getCommandSenderName(), TDManager.getPlayerScoutedTechLevel(evt.player));
		}
	}


	/**
	 * unloads and saves the player techdata
	 */
	public static void onPlayerLogout(PlayerLoggedOutEvent evt) {
		//remove player from techPlayers list
		if(!evt.player.worldObj.isRemote) {
			getInstance().removeDangerousPlayer(evt.player.getCommandSenderName());
		}
	}

	public static TechDataStorage getInstance() {
		return instance;
	}
}
