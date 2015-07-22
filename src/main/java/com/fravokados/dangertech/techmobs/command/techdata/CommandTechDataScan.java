package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.techmobs.command.CommandHelpers;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.lib.util.WorldUtils;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
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
				TDManager.updateScoutedTechLevel(sender.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(sender.getPlayerCoordinates()));
				sender.addChatMessage(new ChatComponentText("Updated Scouted TechLevel: " + TDManager.getScoutedTechLevel(sender.getEntityWorld().provider.dimensionId, WorldUtils.convertToChunkCoord(sender.getPlayerCoordinates()))));
			}
		}
	}
}
