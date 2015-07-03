package com.fravokados.techmobs.command.techdata;

import com.fravokados.techmobs.api.util.ChunkLocation;
import com.fravokados.techmobs.command.CommandHelpers;
import com.fravokados.techmobs.command.CommandTechData;
import com.fravokados.techmobs.command.SubCommand;
import com.fravokados.techmobs.lib.util.WorldUtils;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.TDTickManager;
import com.fravokados.techmobs.world.techdata.TDChunk;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataScan extends SubCommand {

	public CommandTechDataScan() {
		super("read");
		addChildCommand(new TechLevel());
		addChildCommand(new ScoutedLevel());
	}

	@Override
	public void processSubCommand(ICommandSender sender, String[] args) {
		if(!CommandHelpers.processDefaultStandartCommands(sender, this, args, "level")) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	private static class TechLevel extends SubCommand {

		public TechLevel() {
			super("level");
		}

		@Override
		public void processSubCommand(ICommandSender sender, String[] args) {
			if (args.length != 1) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				TDChunk chunk = CommandTechData.getChunkData(sender);
				TDTickManager.scheduleChunkScan(new ChunkLocation(sender.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(sender.getPlayerCoordinates())));
				sender.addChatMessage(new ChatComponentText("Scanning..."));
				sender.addChatMessage(new ChatComponentText(TDTickManager.getTasksInQueue() + " scans in quene!"));
			}
		}
	}

	private static class ScoutedLevel extends SubCommand {

		public ScoutedLevel() {
			super("scouted");
		}

		@Override
		public void processSubCommand(ICommandSender sender, String[] args) {
			if (args.length != 1) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				TDChunk chunk = CommandTechData.getChunkData(sender);
				TDManager.updateScoutedTechLevel(sender.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(sender.getPlayerCoordinates()));
				sender.addChatMessage(new ChatComponentText("Updated Scouted TechLevel: " + TDManager.getScoutedTechLevel(sender.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(sender.getPlayerCoordinates()))));
			}
		}
	}
}
