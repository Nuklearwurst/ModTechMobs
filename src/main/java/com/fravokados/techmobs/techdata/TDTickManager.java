package com.fravokados.techmobs.techdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.effects.TDEffects;
import com.fravokados.techmobs.techdata.effects.player.TDPlayerEffect;
import com.fravokados.techmobs.techdata.values.TDEntryTileEntity;
import com.fravokados.techmobs.techdata.values.TDValues;
import com.fravokados.techmobs.world.TechDataStorage;
import com.fravokados.techmobs.world.techdata.TDPlayer;

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
			LogHelper.info("################ Scanning start ################");
			long time = System.nanoTime();
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
			LogHelper.info("################# Scanning end #################");
			LogHelper.info("Time in ns: " + (System.nanoTime() - time));
		}
		//TODO random effects
		//start special effects
		if(random.nextInt(Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE) == 0) {
			String username = TechDataStorage.getRandomDangerousPlayer(random);
			if(username != null) {
				TDPlayer player = TDManager.getPlayerData(username); 
				int level = player.scoutedTechData;
				int i = 0;
				List<TDPlayerEffect> effects = TDEffects.getUsablePlayerEffects(level, username, player);
				while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS) {
					level -= effects.get(random.nextInt(effects.size())).applyEffect(level, username, player, MinecraftServer.getServer().getConfigurationManager().func_152612_a(username));
					//TODO coding
					i++;
					TDEffects.getUsablePlayerEffects(player.scoutedTechData, username, player);
				}
			}
		}
		if(random.nextInt(Settings.TechData.TD_RANDOM_WORLD_EVENT_CHANCE) == 0) {
			//			List<TDWorldEffect> effects = TDEffects.getUsableWorldEffects();
			//randomize effect order
			//			level -= effects.get(evt.world.rand.nextInt(effects.size())).applyEffect(level, evt.entityLiving);
		}
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
