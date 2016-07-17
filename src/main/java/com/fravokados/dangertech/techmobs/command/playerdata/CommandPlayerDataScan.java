package com.fravokados.dangertech.techmobs.command.playerdata;

import com.fravokados.dangertech.techmobs.command.CommandHelpers;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class CommandPlayerDataScan extends SubCommand {

	public CommandPlayerDataScan() {
		super("read");
		addChildCommand(new TechLevel());
		addChildCommand(new Scouted());
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
			EntityPlayer player = null;
			if(args.length == 1) {
				player = CommandBase.getPlayer(server, sender, args[0]);
			} else if(args.length == 0 && sender instanceof EntityPlayer) {
				player = CommandBase.getCommandSenderAsPlayer(sender);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			TDManager.scanPlayer(player);
			sender.addChatMessage(new TextComponentString("Scanning..."));
			sender.addChatMessage(new TextComponentString("Updated TechLevel: " + TDManager.getPlayerTechLevel(player)));
		}

		@Override
		public boolean isUsernameIndex(String[] args, int index) {
			return index == 0;
		}

		@Override
		public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
			if(args.length == 1) {
				return CommandBase.getListOfStringsMatchingLastWord(args, server.getAllUsernames());
			}
			return new ArrayList<>();
		}
	}

	private static class Scouted extends SubCommand {

		public Scouted() {
			super("scouted");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			EntityPlayer player = null;
			if(args.length == 1) {
				player = CommandBase.getPlayer(server, sender, args[0]);
			} else if(args.length == 0 && sender instanceof EntityPlayer) {
				player = CommandBase.getCommandSenderAsPlayer(sender);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			TDManager.updatePlayerScoutedTechLevel(player);
			sender.addChatMessage(new TextComponentString("Updated Scouted TechLevel: " + TDManager.getPlayerScoutedTechLevel(player)));
		}

		@Override
		public boolean isUsernameIndex(String[] args, int index) {
			return index == 0;
		}

		@Override
		public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
			if(args.length == 1) {
				return CommandBase.getListOfStringsMatchingLastWord(args, server.getAllUsernames());
			}
			return new ArrayList<>();
		}
	}
}
