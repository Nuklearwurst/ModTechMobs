package com.fravokados.dangertech.techmobs.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * tracks players sleeping in cots to restore their spawn points
 * @author Nuklearwurst
 */
public class SleepingManager {

	/**
	 * helper class to store player spawn and sleep info
	 */
	private static class PlayerInfo {
		public ChunkCoordinates loc;
		public boolean forced;
		public boolean wakeUp = false;

		public PlayerInfo(ChunkCoordinates loc, boolean b) {
			this.loc = loc;
			this.forced = b;
		}
	}

	/**
	 * tracked players list
	 */
	private static Map<EntityPlayer, PlayerInfo> sleepingPlayers = new HashMap<EntityPlayer, PlayerInfo>();

	/**
	 * removes a player from the tracked players list
	 */
	public static void removePlayer(EntityPlayer player) {
		sleepingPlayers.remove(player);
	}

	/**
	 * resets player spawn point and removes him from the tracked players list,
	 * only gets executed the first tick after wakeup
	 * @param player the player to reset
	 */
	public static void removeAndResetPlayerOnWakeup(EntityPlayer player) {
		if(isWakeUpPhase(player)) {
			removeAndResetPlayer(player);
		}
	}

	/**
	 * resets player spawn point and removes him from the tracked players list
	 * @param player the player to reset
	 */
	public static void removeAndResetPlayer(EntityPlayer player) {
		if(!player.worldObj.isRemote && sleepingPlayers.containsKey(player)) {
			player.setSpawnChunk(sleepingPlayers.get(player).loc, sleepingPlayers.get(player).forced);
			removePlayer(player);
		}
	}

	/**
	 * adds a player to the tracked players list (and save his spawn coordinates)
	 * @param player the player to save
	 */
	public static void addPlayer(EntityPlayer player) {
		if(!player.worldObj.isRemote) {
			sleepingPlayers.put(player, new PlayerInfo(player.getBedLocation(player.dimension), player.isSpawnForced(player.dimension)));
		}
	}

	/**
	 * checks whether we are in the wakeup tick
	 * @param player player to check
	 * @return true if the player just woke up
	 */
	public static boolean isWakeUpPhase(EntityPlayer player) {
		return sleepingPlayers.containsKey(player) && sleepingPlayers.get(player).wakeUp;
	}

	/**
	 * wakeup event
	 */
	public static void onPlayerWakeUp(EntityPlayer player) {
		if(sleepingPlayers.containsKey(player)) {
			sleepingPlayers.get(player).wakeUp = true;
		}
	}

	/**
	 * clears the tracked players list
	 */
	public static void clear() {
		sleepingPlayers.clear();
	}
}
