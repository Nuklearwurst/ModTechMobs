package com.fravokados.techmobs.techdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.PlayerUtils;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.effects.TDEffectHandler;
import com.fravokados.techmobs.techdata.effects.TDEffects;
import com.fravokados.techmobs.techdata.effects.world.TDWorldEffect;
import com.fravokados.techmobs.techdata.values.TDValues;
import com.fravokados.techmobs.techdata.values.world.TDEntryTileEntity;
import com.fravokados.techmobs.world.TechDataStorage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * controls techdata scanning  
 * 
 * @author Nuklearwurst
 *
 */
public class TDTickManager {

	private final int[] scanningSteps;

	private static List<ChunkLocation> scanningTasks;

	private static Random random = new Random();

	private int tick = 100;


	public TDTickManager() {
		scanningSteps = new int[Settings.TechScanning.SPLIT_STEPS_KEY.length];
		if(Settings.TechScanning.SPLIT_SCANS) {
			for(int i = 0; i < Settings.TechScanning.SPLIT_STEPS_KEY.length; i++) {
				//compute scanning values 
				//(scans per tick at low values)
				//TODO tweaking
				scanningSteps[i] = (int) Math.ceil(Settings.TechScanning.SPLIT_STEPS_KEY[i] * Settings.TechScanning.MAX_SCANS_PER_TICK);
			}
		}
		scanningTasks = new ArrayList<ChunkLocation>();
	}

	private int getScansToPerform() {
		if(scanningTasks.isEmpty()) {
			return 0;
		}
		if(Settings.TechScanning.MAX_SCANS_PER_TICK <= 0) {
			return scanningTasks.size();
		}
		int size = scanningTasks.size();
		for(int i = 0; i < Settings.TechScanning.SPLIT_STEPS_VALUE.length; i++) {
			if(size <= scanningSteps[i]) {
				return (int) Math.ceil(Settings.TechScanning.SPLIT_STEPS_VALUE[i] * Settings.TechScanning.MAX_SCANS_PER_TICK);
			}
		}
		return Settings.TechScanning.MAX_SCANS_PER_TICK;
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent evt) {
		//tick once
		if(evt.phase != TickEvent.Phase.END) return;

		//start scanning
		for(int i = 0; i < getScansToPerform(); i++) {
//			LogHelper.info("################ Scanning start ################");
//			long time = System.nanoTime();
			ChunkLocation task = scanningTasks.get(0);
			World world = DimensionManager.getWorld(task.dimension);
			if(world != null) {
				Chunk chunk = world.getChunkFromChunkCoords(task.x, task.z);
				if(chunk != null) {
					Collection<TileEntity> col = chunk.chunkTileEntityMap.values();
					//TODO scanning of blocks without interfering (probably not possible --> other effects?)
					int value = 0; 
					for(TileEntity te : col) {
						//TODO check if asSubclass is necessary
						TDEntryTileEntity entry = TDValues.tileEntityEntries.get(te.getClass().asSubclass(TileEntity.class));
						if(entry != null) {
							value += entry.getTechValueForTileEntity(te);
						}
					}
					TDManager.setTechLevel(task.dimension, task.getChunkCoordIntPair(), value);
				}
			}
			scanningTasks.remove(0);
//			LogHelper.info("################# Scanning end #################");
//			LogHelper.info("Time in ns: " + (System.nanoTime() - time));
		}
		
		//start special effects
		if(tick == 0) { //player
			tick = Settings.TechData.TD_RANDOM_TICKS;
					
			if(Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE > 0 && random.nextInt(Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE) == 0) {
				String username = TechDataStorage.getRandomDangerousPlayer(random);
				if(username != null) {
					EntityPlayer entity = PlayerUtils.getPlayerFromName(username);
					TDEffectHandler.applyRandomEffectOnPlayer(entity, username, random);
				}
			}
		} else if(tick == (int)Settings.TechData.TD_RANDOM_TICKS / 2) { //chunk
			if(Settings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE > 0 && random.nextInt(Settings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE) == 0) {
				ChunkLocation chunk = TechDataStorage.getRandomDangerousChunk(random);
				if(chunk != null) {
					int level = TDManager.getScoutedTechLevel(chunk.dimension, chunk.getChunkCoordIntPair());
					int i = 0;
					List<TDWorldEffect> effects = TDEffects.getUsableWorldEffects(level);
					while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_WORLD) {
						level -= effects.get(random.nextInt(effects.size())).applyEffect(level); //TODO args
						i++;
						TDEffects.getUsableWorldEffects(level);
					}
					TDManager.setScoutedTechLevel(chunk.dimension, chunk.getChunkCoordIntPair(), level);
				}
			}
		}
		tick--;
	}

	/**
	 * schedules a scan for a chunk
	 * @param loc
	 */
	public static void scheduleChunkScan(ChunkLocation loc) {
		for(ChunkLocation l : scanningTasks) {
			if(l.equals(loc)) {
				return;
			}
		}
		scanningTasks.add(loc);
	}

	/**
	 * schedules a scan for a chunk
	 * @param loc
	 */
	public static void scheduleChunkScan(Chunk chunk) {
		scheduleChunkScan(new ChunkLocation(chunk));
	}

	/**
	 * 
	 * @return scans that are queued
	 */
	public static int getTasksInQueue() {
		return scanningTasks.size();
	}
}
