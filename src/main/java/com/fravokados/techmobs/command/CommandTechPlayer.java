package com.fravokados.techmobs.command;

import com.fravokados.techmobs.command.playerdata.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class CommandTechPlayer extends CommandBase implements IModCommand {
	
	private final List<String> aliases;
	
	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>() {
		@Override
		public int compare(SubCommand o1, SubCommand o2) {
			return o1.compareTo(o2);
		}
	});

	public CommandTechPlayer() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techplayer");
		this.aliases.add("tdp");
		addChildCommand(new CommandPlayerDataRead());
		addChildCommand(new CommandPlayerDataSet());
		addChildCommand(new CommandPlayerDataRemove());
		addChildCommand(new CommandPlayerDataAdd());
		addChildCommand(new CommandPlayerDataScan());
	}

	public void addChildCommand(SubCommand child) {
		child.setParent(this);
		children.add(child);
	}

	@Override
	public String getCommandName() {
		return "techplayer";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}

	@Override
	public String getFullCommandString() {
		return getCommandName();
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public int getPermissionLevel() {
		return SubCommand.PermLevel.ADMIN.permLevel;
	}

	@Override
	public SortedSet<SubCommand> getChildren() {
		return children;
	}

	@Override
	public void printHelp(ICommandSender sender) {
		CommandHelpers.printHelp(sender, this);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!CommandHelpers.processStandardCommands(sender, this, args)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}
	
	private EntityPlayer getPlayer(ICommandSender sender) {
		if(sender instanceof EntityPlayer) {
			return (EntityPlayer) sender;
		}
		return null;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args) {
		return CommandHelpers.addTabCompletionOptionsForSubCommands(this, sender, args);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		if(index == 0) {
			return false;
		}
		for (SubCommand sub : children) {
			if(sub.getCommandName().toLowerCase().equals(args[0].toLowerCase())) {
				return sub.isUsernameIndex(Arrays.copyOfRange(args, 1, args.length), index - 1);
			}
		}
		return false;
	}

}
