package com.fravokados.dangertech.monsters.command;

import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * adapted from Railcraft <http://www.railcraft.info/>
 */
public class CommandHelpers {

	public static World getWorld(MinecraftServer server, ICommandSender sender, IModCommand command, String[] args, int worldArgIndex) throws CommandException {
		// Handle passed in world argument
		if (worldArgIndex < args.length)
			try {
				int dim = Integer.parseInt(args[worldArgIndex]);
				World world = server.worldServerForDimension(dim);
				//noinspection ConstantConditions
				if (world != null)
					return world;
			} catch (Exception ex) {
				throwWrongUsage(sender, command);
			}
		return getWorld(server, sender, command);
	}

	public static World getWorld(MinecraftServer server, ICommandSender sender, IModCommand command) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			return player.worldObj;
		}
		return server.worldServerForDimension(0);
	}

	public static String[] getPlayersUserNames(MinecraftServer server) {
		return server.getAllUsernames();
	}

	public static void sendLocalizedChatMessage(ICommandSender sender, String locTag, Object... args) {
		sender.addChatMessage(new TextComponentTranslation(locTag, args));
	}

	public static void sendLocalizedChatMessage(ICommandSender sender, Style chatStyle, String locTag, Object... args) {
		TextComponentTranslation chat = new TextComponentTranslation(locTag, args);
		chat.setStyle(chatStyle);
		sender.addChatMessage(chat);
	}

	public static void throwWrongUsage(ICommandSender sender, IModCommand command) throws WrongUsageException {
		throw new WrongUsageException((GeneralUtils.translateWithFormat("command.dangertechmobs.help", command.getCommandUsage(sender))));
	}

	public static void processChildCommand(MinecraftServer server, ICommandSender sender, SubCommand child, String[] args) throws CommandException {
		if (!sender.canCommandSenderUseCommand(child.getPermissionLevel(), child.getFullCommandString()))
			throw new WrongUsageException(GeneralUtils.translate("command.dangertechmobs.noperms"));
		String[] newargs = new String[args.length - 1];
		System.arraycopy(args, 1, newargs, 0, newargs.length);
		child.execute(server, sender, newargs);
	}

	public static void printHelp(ICommandSender sender, IModCommand command) {
		Style header = new Style();
		header.setColor(TextFormatting.BLUE);
		sendLocalizedChatMessage(sender, header, "command.dangertechmobs." + command.getFullCommandString().replace(" ", ".") + ".format", command.getFullCommandString());
		Style body = new Style();
		body.setColor(TextFormatting.GRAY);
		sendLocalizedChatMessage(sender, body, "command.dangertechmobs.aliases", command.getCommandAliases().toString().replace("[", "").replace("]", ""));
		sendLocalizedChatMessage(sender, body, "command.dangertechmobs.permlevel", command.getPermissionLevel());
		sendLocalizedChatMessage(sender, body, "command.dangertechmobs." + command.getFullCommandString().replace(" ", ".") + ".help");
		if (!command.getChildren().isEmpty()) {
			sendLocalizedChatMessage(sender, "command.dangertechmobs.list");
			for (SubCommand child : command.getChildren()) {
				sendLocalizedChatMessage(sender, "command.dangertechmobs." + child.getFullCommandString().replace(" ", ".") + ".desc", child.getCommandName());
			}
		}
	}

	public static boolean processDefaultStandardCommands(MinecraftServer server, ICommandSender sender, IModCommand command, String[] oldArgs, String newArg) throws CommandException {
		String[] newargs = new String[oldArgs.length + 1];
		newargs[0] = newArg;
		System.arraycopy(oldArgs, 0, newargs, 1, oldArgs.length);
		return CommandHelpers.processStandardCommands(server, sender, command, newargs);
	}

	public static boolean processStandardCommands(MinecraftServer server, ICommandSender sender, IModCommand command, String[] args) throws CommandException {
		if (args.length >= 1) {
			if (args[0].equals("help")) {
				command.printHelp(sender);
				return true;
			}
			for (SubCommand child : command.getChildren()) {
				if (matches(args[0], child)) {
					processChildCommand(server, sender, child, args);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean matches(String commandName, IModCommand command) {
		if (commandName.equals(command.getCommandName())) {
			return true;
		} else {
			for (String alias : command.getCommandAliases()) {
				if (commandName.equals(alias)) {
					return true;
				}
			}
		}
		return false;
	}

	public static int getCoordFromCommand(int pos, String arg) {
		if(arg.startsWith("~")) {
			if(arg.length() == 1) {
				return pos;
			}
			return pos + Integer.parseInt(arg.substring(1));
		}
		return Integer.parseInt(arg);
	}

	public static List<String> getTabCompletionOptionsForSubCommands(MinecraftServer server, IModCommand command, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if(args.length == 1) {
			List<String> list = new ArrayList<>();
			for (SubCommand sub : command.getChildren()) {
				if(sub.getCommandName().toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(sub.getCommandName());
				}
			}
			return list;
		} else {
			for (SubCommand child : command.getChildren()) {
				if (matches(args[0], child)) {
					return child.getTabCompletionOptions(server, sender, Arrays.copyOfRange(args, 1, args.length), pos);
				}
			}
		}
		return new ArrayList<>();
	}
}