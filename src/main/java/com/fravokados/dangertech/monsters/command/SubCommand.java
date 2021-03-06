package com.fravokados.dangertech.monsters.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;

/**
 * adapted from Railcraft <http://www.railcraft.info/>
 */
public abstract class SubCommand implements IModCommand {
	public enum PermLevel {

		EVERYONE(0), ADMIN(2);
		int permLevel;

		PermLevel(int permLevel) {
			this.permLevel = permLevel;
		}

	}

	private final String name;
	private final List<String> aliases = new ArrayList<String>();
	private PermLevel permLevel = PermLevel.EVERYONE;
	private IModCommand parent;
	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>() {

		@Override
		public int compare(SubCommand o1, SubCommand o2) {
			return o1.compareTo(o2);
		}
	});

	public SubCommand(String name) {
		this.name = name;
	}

	@Override
	public final String getName() {
		return name;
	}

	public SubCommand addChildCommand(SubCommand child) {
		child.setParent(this);
		children.add(child);
		return this;
	}

	void setParent(IModCommand parent) {
		this.parent = parent;
	}

	@Override
	public SortedSet<SubCommand> getChildren() {
		return children;
	}

	public void addAlias(String alias) {
		aliases.add(alias);
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return CommandHelpers.getTabCompletionOptionsForSubCommands(server, this, sender, args, pos);
	}

	@Override
	public final void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!CommandHelpers.processStandardCommands(server, sender, this, args))
			processSubCommand(server, sender, args);
	}

	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		CommandHelpers.throwWrongUsage(sender, this);
	}

	public SubCommand setPermLevel(PermLevel permLevel) {
		this.permLevel = permLevel;
		return this;
	}

	@Override
	public final int getPermissionLevel() {
		return permLevel.permLevel;
	}



	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(getPermissionLevel(), getName());
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		if(index == 0) {
			return false;
		}
		for (SubCommand sub : children) {
			if(sub.getName().toLowerCase().equals(args[0].toLowerCase())) {
				return sub.isUsernameIndex(Arrays.copyOfRange(args, 1, args.length), index - 1);
			}
		}
		return false;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + getFullCommandString() + " help";
	}

	@Override
	public void printHelp(ICommandSender sender) {
		CommandHelpers.printHelp(sender, this);
	}

	@Override
	public String getFullCommandString() {
		return parent.getFullCommandString() + " " + getName();
	}

	@Override
	public int compareTo(ICommand command) {
		return this.getName().compareTo(command.getName());
	}
}
