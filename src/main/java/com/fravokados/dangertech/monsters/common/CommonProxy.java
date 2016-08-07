package com.fravokados.dangertech.monsters.common;

import com.fravokados.dangertech.monsters.common.handler.TMEventHandler;
import com.fravokados.dangertech.monsters.techdata.TDTickManager;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void registerEvents() {		
		//Forge Events
		MinecraftForge.EVENT_BUS.register(new TMEventHandler());
		
		//tickhandler
		MinecraftForge.EVENT_BUS.register(new TDTickManager());
	}

	public void registerRenderer() {}

}
