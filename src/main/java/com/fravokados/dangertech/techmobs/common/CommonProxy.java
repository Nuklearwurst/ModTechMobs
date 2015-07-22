package com.fravokados.dangertech.techmobs.common;

import com.fravokados.dangertech.techmobs.common.handler.PlayerTickHandler;
import com.fravokados.dangertech.techmobs.common.handler.TMEventHandler;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void registerEvents() {		
		//Forge Events
		MinecraftForge.EVENT_BUS.register(new TMEventHandler());
		
		//tickhandler
		FMLCommonHandler.instance().bus().register(new TDTickManager());

		//Player Tick Handler
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
	}

	public void registerRenderer() {}

}
