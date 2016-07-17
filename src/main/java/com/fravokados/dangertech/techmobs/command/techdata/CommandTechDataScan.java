package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.techmobs.command.CommandHelpers;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!CommandHelpers.processDefaultStandardCommands(server, sender, this, args, "level")) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	private static class TechLevel extends SubCommand {

		public TechLevel() {
			super("level");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			if (args.length != 1) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				TDTickManager.scheduleChunkScan(new ChunkLocation(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition())));
				sender.addChatMessage(new TextComponentString("Scanning..."));
				sender.addChatMessage(new TextComponentString(TDTickManager.getTasksInQueue() + " scans in quene!"));
			}
		}
	}

	private static class ScoutedLevel extends SubCommand {

		public ScoutedLevel() {
			super("scouted");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			if (args.length != 1) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				TDManager.updateScoutedTechLevel(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition()));
				sender.addChatMessage(new TextComponentString("Updated Scouted TechLevel: " + TDManager.getScoutedTechLevel(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition()))));
			}
		}
	}
}
