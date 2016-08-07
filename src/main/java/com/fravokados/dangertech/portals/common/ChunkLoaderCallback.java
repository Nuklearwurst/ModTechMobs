package com.fravokados.dangertech.portals.common;

import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ChunkLoaderCallback implements ForgeChunkManager.LoadingCallback {
	@Override
	public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
		LogHelperMD.logDev("Loaded Tickets: " + tickets);
	}
}
