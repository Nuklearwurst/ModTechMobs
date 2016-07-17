package com.fravokados.dangertech.techmobs.common;

import com.fravokados.dangertech.techmobs.common.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;

import java.util.HashSet;

/**
 * tracks players sleeping in cots to restore their spawn points
 * @author Nuklearwurst
 */
public class SleepingManager {

	private static HashSet<EntityPlayer> sleepingPlayers = new HashSet<EntityPlayer>();

	/**
	 * removes a player from the tracked players list
	 */
	public static void removePlayer(EntityPlayer player) {
		sleepingPlayers.remove(player);
	}


	/**
	 * adds a player to the tracked players list (and save his spawn coordinates)
	 * @param player the player to save
	 */
	public static void addPlayer(EntityPlayer player) {
		sleepingPlayers.add(player);
	}

	/**
	 * wakeup event
	 */
	public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
		if(sleepingPlayers.contains(event.getEntityPlayer())) {
			if(!event.shouldSetSpawn()) {
				sleepingPlayers.remove(event.getEntityPlayer());
				return;
			}
			IBlockState iblockstate =  event.getEntityPlayer().worldObj.getBlockState(event.getEntityPlayer().playerLocation);
			//noinspection ConstantConditions
			if(iblockstate.getBlock() != ModBlocks.block_cot) {
				sleepingPlayers.remove(event.getEntityPlayer());
			}
		}
	}

	/**
	 * @return if setting spawnpoint should get cancelled
	 */
	public static boolean onPlayerSetSpawn(PlayerSetSpawnEvent event) {
		if(!event.isForced() && sleepingPlayers.contains(event.getEntityPlayer())) {
			removePlayer(event.getEntityPlayer());
			return true;
		}
		return false;
	}

	/**
	 * clears the tracked players list
	 */
	public static void clear() {
		sleepingPlayers.clear();
	}
}
