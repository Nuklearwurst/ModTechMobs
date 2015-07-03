package com.fravokados.techmobs.command.techdata;

import com.fravokados.techmobs.command.CommandHelpers;
import com.fravokados.techmobs.command.CommandTechData;
import com.fravokados.techmobs.command.SubCommand;
import com.fravokados.techmobs.world.techdata.TDChunk;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

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
				chunk.techLevel = CommandBase.parseInt(sender, args[1]);
				sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));
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
				chunk.scoutedTechLevel = CommandBase.parseInt(sender, args[1]);
				sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
			}
		}
	}
}
