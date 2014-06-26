package com.fravokados.techmobs.common;

import net.minecraftforge.common.MinecraftForge;

import com.fravokados.techmobs.common.handler.Events;
import com.fravokados.techmobs.techdata.TDTickManager;

import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy {
	
	public void registerEvents() {
		//Forge Events
		MinecraftForge.EVENT_BUS.register(new Events());
		
		//tickhandler
		FMLCommonHandler.instance().bus().register(new TDTickManager());
	}

}
