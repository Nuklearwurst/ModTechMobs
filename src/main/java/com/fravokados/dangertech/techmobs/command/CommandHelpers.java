package com.fravokados.dangertech.techmobs.command;

import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * adapted from Railcraft <http://www.railcraft.info/>
 */
public class CommandHelpers {

	public static World getWorld(ICommandSender sender, IModCommand command, String[] args, int worldArgIndex) {
		// Handle passed in world argument
		if (worldArgIndex < args.length)
			try {
				int dim = Integer.parseInt(args[worldArgIndex]);
				World world = MinecraftServer.getServer().worldServerForDimension(dim);
				if (world != null)
					return world;
			} catch (Exception ex) {
				throwWrongUsage(sender, command);
			}
		return getWorld(sender, command);
	}

	public static World getWorld(ICommandSender sender, IModCommand command) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			return player.worldObj;
		}
		return MinecraftServer.getServer().worldServerForDimension(0);
	}

	public static String[] getPlayers() {
		return MinecraftServer.getServer().getAllUsernames();
	}

	public static void sendLocalizedChatMessage(ICommandSender sender, String locTag, Object... args) {
		sender.addChatMessage(new ChatComponentTranslation(locTag, args));
	}

	public static void sendLocalizedChatMessage(ICommandSender sender, ChatStyle chatStyle, String locTag, Object... args) {
		ChatComponentTranslation chat = new ChatComponentTranslation(locTag, args);
		chat.setChatStyle(chatStyle);
		sender.addChatMessage(chat);
	}

	/**
	 * Avoid using this function if at all possible. Commands are processed on the server,
	 * which has no localization information.
	 * StringUtil.localize() is NOT a valid alternative for sendLocalizedChatMessage().
	 * Messages will not be localized properly if you use StringUtil.localize().
	 */
	public static void sendChatMessage(ICommandSender sender, String message) {
		sender.addChatMessage(new ChatComponentText(message));
	}

	public static void throwWrongUsage(ICommandSender sender, IModCommand command) throws WrongUsageException {
		throw new WrongUsageException((GeneralUtils.translateWithFormat("command.techmobs.help", command.getCommandUsage(sender))));
	}

	public static void processChildCommand(ICommandSender sender, SubCommand child, String[] args) {
		if (!sender.canCommandSenderUseCommand(child.getPermissionLevel(), child.getFullCommandString()))
			throw new WrongUsageException(GeneralUtils.translate("command.techmobs.noperms"));
		String[] newargs = new String[args.length - 1];
		System.arraycopy(args, 1, newargs, 0, newargs.length);
		child.processCommand(sender, newargs);
	}

	public static void printHelp(ICommandSender sender, IModCommand command) {
		ChatStyle header = new ChatStyle();
		header.setColor(EnumChatFormatting.BLUE);
		sendLocalizedChatMessage(sender, header, "command.techmobs." + command.getFullCommandString().replace(" ", ".") + ".format", command.getFullCommandString());
		ChatStyle body = new ChatStyle();
		body.setColor(EnumChatFormatting.GRAY);
		sendLocalizedChatMessage(sender, body, "command.techmobs.aliases", command.getCommandAliases().toString().replace("[", "").replace("]", ""));
		sendLocalizedChatMessage(sender, body, "command.techmobs.permlevel", command.getPermissionLevel());
		sendLocalizedChatMessage(sender, body, "command.techmobs." + command.getFullCommandString().replace(" ", ".") + ".help");
		if (!command.getChildren().isEmpty()) {
			sendLocalizedChatMessage(sender, "command.techmobs.list");
			for (SubCommand child : command.getChildren()) {
				sendLocalizedChatMessage(sender, "command.techmobs." + child.getFullCommandString().replace(" ", ".") + ".desc", child.getCommandName());
			}
		}
	}

	public static boolean processDefaultStandartCommands(ICommandSender sender, IModCommand command, String[] oldArgs, String newArg) {
		String[] newargs = new String[oldArgs.length + 1];
		newargs[0] = newArg;
		System.arraycopy(oldArgs, 0, newargs, 1, oldArgs.length);
		return CommandHelpers.processStandardCommands(sender, command, newargs);
	}

	public static boolean processStandardCommands(ICommandSender sender, IModCommand command, String[] args) {
		if (args.length >= 1) {
			if (args[0].equals("help")) {
				command.printHelp(sender);
				return true;
			}
			for (SubCommand child : command.getChildren()) {
				if (matches(args[0], child)) {
					processChildCommand(sender, child, args);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean matches(String commandName, IModCommand command) {
		if (commandName.equals(command.getCommandName()))
			return true;
		else if (command.getCommandAliases() != null)
			for (String alias : command.getCommandAliases()) {
				if (commandName.equals(alias))
					return true;
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

	public static List<?> addTabCompletionOptionsForSubCommands(IModCommand command, ICommandSender sender, String[] args) {
		if(args.length == 1) {
			List<String> list = new ArrayList<String>();
			for (SubCommand sub : command.getChildren()) {
				if(sub.getCommandName().toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(sub.getCommandName());
				}
			}
			return list.isEmpty() ? null : list;
		} else {
			for (SubCommand child : command.getChildren()) {
				if (matches(args[0], child)) {
					return child.addTabCompletionOptions(sender, Arrays.copyOfRange(args, 1, args.length));
				}
			}
		}
		return null;
	}
}