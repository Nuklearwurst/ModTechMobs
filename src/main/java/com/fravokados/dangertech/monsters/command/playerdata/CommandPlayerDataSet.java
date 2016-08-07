package com.fravokados.dangertech.monsters.command.playerdata;

import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.techdata.TDManager;
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
public class CommandPlayerDataSet extends SubCommand {

	public CommandPlayerDataSet() {
		super("set");
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
			int value = 0;
			EntityPlayer player = null;
			if(args.length == 2) {
				player = CommandBase.getPlayer(server, sender, args[0]);
				value = CommandBase.parseInt(args[1]);
			} else if(args.length == 1 && sender instanceof EntityPlayer) {
				player = CommandBase.getCommandSenderAsPlayer(sender);
				value = CommandBase.parseInt(args[0]);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			TDManager.setPlayerTechLevel(player, value);
			sender.addChatMessage(new TextComponentString("TechLevel of " + player.getDisplayNameString() + ": " + TDManager.getPlayerTechLevel(player)));
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
			int value = 0;
			EntityPlayer player = null;
			if(args.length == 2) {
				value = CommandBase.parseInt(args[1]);
				player = CommandBase.getPlayer(server, sender, args[0]);
			} else if(args.length == 1 && sender instanceof EntityPlayer) {
				value = CommandBase.parseInt(args[0]);
				player = CommandBase.getCommandSenderAsPlayer(sender);
			}
			if(player == null) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			TDManager.setPlayerScoutedTechLevel(player, value);
			sender.addChatMessage(new TextComponentString("Scouted TechLevel of " + player.getDisplayNameString() + ": " + TDManager.getPlayerScoutedTechLevel(player)));
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
