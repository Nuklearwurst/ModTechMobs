package com.fravokados.techmobs.common.handler;

import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.fravokados.techmobs.world.TechDataStorage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class Events {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load evt) {
		TechDataStorage.onWorldLoad(evt);
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save evt) {
		TechDataStorage.onWorldSave(evt);
	}
	
//	@SubscribeEvent
//	public void onChunkUnload(ChunkEvent.Unload evt) {
//		//not used we
//		//has to be tested what works best
//	}
	
	@SubscribeEvent
	public void onChunkDataSave(ChunkDataEvent.Save evt) {
		TechDataStorage.onChunkDataSave(evt);
	}
	
	@SubscribeEvent
	public void onChunkDataLoad(ChunkDataEvent.Load evt) {
		TechDataStorage.onChunkDataLoad(evt);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent evt) {
		TechDataStorage.onPlayerLogin(evt);
    }
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent evt) {
		TechDataStorage.onPlayerLogout(evt);
	}
}
