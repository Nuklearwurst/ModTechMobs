package com.fravokados.techmobs.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class PlayerUtils {

	public static EntityPlayer getPlayerFromName(String username) {
		return MinecraftServer.getServer().getConfigurationManager().func_152612_a(username);
	}
}
