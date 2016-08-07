package com.fravokados.dangertech.monsters.command.techdata;

import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.CommandTechData;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.world.techdata.TDChunk;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataSet extends SubCommand {

	public CommandTechDataSet() {
		super("set");
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
				TDChunk chunk = CommandTechData.getChunkData(sender);
				chunk.techLevel = CommandBase.parseInt(args[1]);
				sender.addChatMessage(new TextComponentString("TechLevel in this Chunk: " + chunk.techLevel));
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
				TDChunk chunk = CommandTechData.getChunkData(sender);
				chunk.scoutedTechLevel = CommandBase.parseInt(args[1]);
				sender.addChatMessage(new TextComponentString("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
			}
		}
	}
}
