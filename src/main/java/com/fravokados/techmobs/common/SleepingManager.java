package com.fravokados.techmobs.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * tracks players sleeping in cots to restore their spawn points
 * @author Nuklearwurst
 */
public class SleepingManager {

	private static class PlayerInfo {
		public ChunkCoordinates loc;
		public boolean forced;
		public boolean wakeUp = false;

		public PlayerInfo(ChunkCoordinates loc, boolean b) {
			this.loc = loc;
			this.forced = b;
		}
	}

	private static Map<EntityPlayer, PlayerInfo> sleepingPlayers = new HashMap<EntityPlayer, PlayerInfo>();

	public static void removePlayer(EntityPlayer player) {
		sleepingPlayers.remove(player);
	}

	public static void removeAndResetPlayerOnWakeup(EntityPlayer player) {
		if(isWakeUpPhase(player)) {
			removeAndResetPlayer(player);
		}
	}

	public static void removeAndResetPlayer(EntityPlayer player) {
		if(!player.worldObj.isRemote && sleepingPlayers.containsKey(player)) {
			player.setSpawnChunk(sleepingPlayers.get(player).loc, sleepingPlayers.get(player).forced);
			removePlayer(player);
		}
	}

	public static void addPlayer(EntityPlayer player) {
		if(!player.worldObj.isRemote) {
			sleepingPlayers.put(player, new PlayerInfo(player.getBedLocation(player.dimension), player.isSpawnForced(player.dimension)));
		}
	}

	public static boolean isWakeUpPhase(EntityPlayer player) {
		return sleepingPlayers.containsKey(player) && sleepingPlayers.get(player).wakeUp;
	}

	public static void onPlayerWakeUp(EntityPlayer player) {
		if(sleepingPlayers.containsKey(player)) {
			sleepingPlayers.get(player).wakeUp = true;
		}
	}

	public static void clear() {
		sleepingPlayers.clear();
	}
}
