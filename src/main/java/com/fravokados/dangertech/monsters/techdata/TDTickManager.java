package com.fravokados.dangertech.monsters.techdata;

import com.fravokados.dangertech.api.techdata.effects.chunk.TDChunkEffect;
import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.lib.util.PlayerUtils;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffectHandler;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffects;
import com.fravokados.dangertech.monsters.techdata.values.TDValues;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

/**
 * controls techdata scanning
 *
 * @author Nuklearwurst
 */
@Mod.EventBusSubscriber
public class TDTickManager {

	/**
	 * hold at which steps the amount of scanned chunks per tick increases
	 */
	private static int[] scanningSteps;

	private static Queue<ChunkLocation> scanningTasks;

	private static final Random random = new Random();

	private static int tick = 100;

	/**
	 * recalculate at which thresholds scanning frequency increases
	 */
	public static void calculateScanningSteps() {
		scanningSteps = new int[Settings.TechScanning.SPLIT_STEPS_KEY.length];
		if (Settings.TechScanning.SPLIT_SCANS) {
			for (int i = 0; i < Settings.TechScanning.SPLIT_STEPS_KEY.length; i++) {
				//compute scanning values
				//(scans per tick at low values)
				scanningSteps[i] = (int) Math.ceil(Settings.TechScanning.SPLIT_STEPS_KEY[i] * Settings.TechScanning.MAX_SCANS_PER_TICK);
			}
		}
		scanningTasks = new LinkedList<>();
	}

	/**
	 * Calculates how many chunks should get scanned<br></br>
	 * depends on the size of the current scanning queue
	 * @return the amount of scans that are to be done in the next tick
	 */
	private static int getScansToPerform() {
		if (scanningTasks.isEmpty()) {
			return 0;
		}
		if (Settings.TechScanning.MAX_SCANS_PER_TICK <= 0) {
			return scanningTasks.size();
		}
		int size = scanningTasks.size();
		for (int i = 0; i < Settings.TechScanning.SPLIT_STEPS_VALUE.length; i++) {
			if (size <= scanningSteps[i]) {
				return (int) Math.ceil(Settings.TechScanning.SPLIT_STEPS_VALUE[i] * Settings.TechScanning.MAX_SCANS_PER_TICK);
			}
		}
		return Settings.TechScanning.MAX_SCANS_PER_TICK;
	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent evt) {
		//tick once
		if (evt.phase != TickEvent.Phase.END) return;

		//start scanning
		for (int i = 0; i < getScansToPerform(); i++) {
			ChunkLocation task = scanningTasks.poll();
			World world = DimensionManager.getWorld(task.dimension);
			if (world != null) {
				Chunk chunk = world.getChunkFromChunkCoords(task.x, task.z);
				Collection<TileEntity> col = chunk.getTileEntityMap().values();
				int value = 0;
				for (TileEntity te : col) {
					value += TDValues.getInstance().getTechDataForTileEntity(te);
				}
				TDManager.setTechLevel(task.dimension, task.getChunkCoordIntPair(), value);
			}
		}

		//start special effects
		if (tick == 0) { //player
			tick = Settings.TechData.TD_RANDOM_TICKS;
			applyRandomPlayerEffect();

		} else if (tick == Settings.TechData.TD_RANDOM_TICKS / 2) { //chunk
			applyRandomChunkEffect();
		}
		tick--;
	}

	private static void applyRandomPlayerEffect() {
		if (Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE > 0 && (Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE == 1 || random.nextInt(Settings.TechData.TD_RANDOM_PLAYER_EVENT_CHANCE) == 0)) {
			UUID uuid = TechDataStorage.getInstance().getRandomDangerousPlayer(random);
			if (uuid != null) {
				EntityPlayer entity = PlayerUtils.getPlayerFromUUID(uuid);
				if(entity != null) {
					TDEffectHandler.applyRandomEffectOnPlayer(entity, uuid, random);
				}
			}
		}
	}

	private static void applyRandomChunkEffect() {
		if (Settings.TechData.TD_RANDOM_CHUNK_EVENT_CHANCE > 0 && (Settings.TechData.TD_RANDOM_CHUNK_EVENT_CHANCE == 1 || random.nextInt(Settings.TechData.TD_RANDOM_CHUNK_EVENT_CHANCE) == 0)) {
			ChunkLocation chunk = TechDataStorage.getInstance().getRandomDangerousChunk(random);
			if (chunk != null) {
				int level = TDManager.getScoutedTechLevel(chunk.dimension, chunk.getChunkCoordIntPair());
				World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(chunk.dimension);
				int i = 0;
				List<TDChunkEffect> effects = TDEffects.getInstance().getUsableWorldEffects(level, world);
				while (!effects.isEmpty() && i < Settings.TechData.MAX_EFFECTS_CHUNK) {
					level -= effects.get(random.nextInt(effects.size())).applyEffect(level, chunk, world);
					i++;
					TDEffects.getInstance().getUsableWorldEffects(level, world);
				}
				TDManager.setScoutedTechLevel(chunk.dimension, chunk.getChunkCoordIntPair(), level);
			}
		}
	}

	/**
	 * schedules a scan for a chunk
	 */
	public static void scheduleChunkScan(ChunkLocation loc) {
		for (ChunkLocation l : scanningTasks) {
			if (l.equals(loc)) {
				return;
			}
		}
		scanningTasks.add(loc);
	}

	/**
	 * schedules a scan for a chunk
	 */
	public static void scheduleChunkScan(Chunk chunk) {
		scheduleChunkScan(new ChunkLocation(chunk));
	}

	/**
	 * @return scans that are queued
	 */
	public static int getTasksInQueue() {
		return scanningTasks.size();
	}
}
