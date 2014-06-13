package com.fravokados.techmobs.techdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.techdata.values.TDEntryTileEntity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * controls techdata scanning  
 * 
 * @author Nuklearwurst
 *
 */
public class TDScanManager {

	private final int[] scanningSteps;
	
	private static List<ChunkLocation> scanningTasks;
	

	
	public TDScanManager() {
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
			ChunkLocation task = scanningTasks.get(0);
			World world = DimensionManager.getWorld(task.dimension);
			if(world != null) {
				Chunk chunk = world.getChunkFromChunkCoords(task.x, task.z);
				if(chunk != null) {
					Collection<TileEntity> col = chunk.chunkTileEntityMap.values();
					//TODO scanning of blocks without interfering (probably not possible --> other effects?)
//					LogHelper.info("scanning");
					int value = 0; 
					for(TileEntity te : col) {
						//TODO check if asSubclass is necessary
//						LogHelper.info(te.getClass().asSubclass(TileEntity.class));
						TDEntryTileEntity entry = TDValues.tileEntityEntries.get(te.getClass().asSubclass(TileEntity.class));
						if(entry != null) {
							value += entry.getTechValueForTileEntity(te);
						}
					}
					TDManager.setTechLevel(task.dimension, task.getChunkCoordIntPair(), value);
				}
			}
			scanningTasks.remove(0);
		}
	}
	
	public static void scheduleChunkScan(ChunkLocation loc) {
		for(ChunkLocation l : scanningTasks) {
			if(l.equals(loc)) {
				return;
			}
		}
		scanningTasks.add(loc);
	}
	
	
	public static int getTasksInQuene() {
		return scanningTasks.size();
	}
}
