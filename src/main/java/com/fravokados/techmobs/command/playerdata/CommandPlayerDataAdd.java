package com.fravokados.techmobs.command.playerdata;

import com.fravokados.techmobs.command.CommandHelpers;
import com.fravokados.techmobs.command.SubCommand;
import com.fravokados.techmobs.techdata.TDManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class CommandPlayerDataAdd extends SubCommand {

	public CommandPlayerDataAdd() {
		super("add");
		addChildCommand(new TechLevel());
		addChildCommand(new Scouted());
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
			int value = 0;
			EntityPlayer player = null;
			if(args.length == 2) {
				player = CommandBase.getPlayer(sender, args[0]);
				value = CommandBase.parseInt(sender, args[1]);
			} else if(args.length == 1 && sender instanceof EntityPlayer) {
				player = CommandBase.getCommandSenderAsPlayer(sender);
				value = CommandBase.parseInt(sender, args[0]);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			int techLevel = TDManager.getPlayerTechLevel(player);
			TDManager.setPlayerTechLevel(player, techLevel + value);
			sender.addChatMessage(new ChatComponentText("TechLevel of " + player.getCommandSenderName() + ": " + (techLevel + value)));
		}

		@Override
		public boolean isUsernameIndex(String[] args, int index) {
			return index == 0;
		}

		@Override
		public List addTabCompletionOptions(ICommandSender sender, String[] args) {
			if(args.length == 1) {
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			}
			return null;
		}
	}

	private static class Scouted extends SubCommand {

		public Scouted() {
			super("scouted");
		}

		@Override
		public void processSubCommand(ICommandSender sender, String[] args) {
			int value = 0;
			EntityPlayer player = null;
			if(args.length == 2) {
				value = CommandBase.parseInt(sender, args[1]);
				player = CommandBase.getPlayer(sender, args[0]);
			} else if(args.length == 1 && sender instanceof EntityPlayer) {
				value = CommandBase.parseInt(sender, args[0]);
				player = CommandBase.getCommandSenderAsPlayer(sender);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			int scoutedTechLevel = TDManager.getPlayerScoutedTechLevel(player);
			TDManager.setPlayerScoutedTechLevel(player, scoutedTechLevel + value);
			sender.addChatMessage(new ChatComponentText("Scouted TechLevel of " + player.getCommandSenderName() + ": " + (scoutedTechLevel + value)));
		}

		@Override
		public boolean isUsernameIndex(String[] args, int index) {
			return index == 0;
		}

		@Override
		public List addTabCompletionOptions(ICommandSender sender, String[] args) {
			if(args.length == 1) {
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			}
			return null;
		}
	}
}
