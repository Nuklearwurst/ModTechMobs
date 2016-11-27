package com.fravokados.dangertech.monsters.command.techdata;

import com.fravokados.dangertech.api.util.ChunkLocation;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.fravokados.dangertech.monsters.techdata.TDTickManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataScan extends SubCommand {

	public CommandTechDataScan() {
		super("scan");
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
			TDTickManager.scheduleChunkScan(new ChunkLocation(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition())));
			sender.sendMessage(new TextComponentTranslation("chat.command.techdata.scanning"));
			sender.sendMessage(new TextComponentTranslation("chat.command.techdata.queue", TDTickManager.getTasksInQueue()));
		}
	}

	private static class ScoutedLevel extends SubCommand {

		public ScoutedLevel() {
			super("scouted");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			TDManager.updateScoutedTechLevel(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition()));
			sender.sendMessage(new TextComponentTranslation("chat.command.techdata.scouted", TDManager.getScoutedTechLevel(sender.getEntityWorld().provider.getDimension(), WorldUtils.convertToChunkCoord(sender.getPosition()))));
		}
	}
}
