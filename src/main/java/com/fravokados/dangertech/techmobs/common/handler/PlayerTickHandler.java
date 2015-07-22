package com.fravokados.dangertech.techmobs.common.handler;

import com.fravokados.dangertech.techmobs.common.SleepingManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * @author Nuklearwurst
 */
public class PlayerTickHandler {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
		if(evt.phase == TickEvent.Phase.END && !evt.player.worldObj.isRemote) {
			SleepingManager.removeAndResetPlayerOnWakeup(evt.player);
		}
	}
}
