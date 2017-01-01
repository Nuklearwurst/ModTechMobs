package com.fravokados.dangertech.monsters.command;

import com.fravokados.dangertech.monsters.command.techmobs.EMP;
import com.fravokados.dangertech.monsters.command.techmobs.Effect;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;

public class CommandTechMobs extends CommandBase implements IModCommand {

	private final SortedSet<SubCommand> children = new TreeSet<>(SubCommand::compareTo);

	private final List<String> aliases;

	public CommandTechMobs() {
		this.aliases = new ArrayList<>();
		this.aliases.add("techmobs");
		this.aliases.add("tmobs");
		addChildCommand(new Effect());
		addChildCommand(new EMP());
	}

	public void addChildCommand(SubCommand child) {
		child.setParent(this);
		children.add(child);
	}

	@Override
	public String getName() {
		return "techmobs";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + this.getName() + " help";
	}

	@Override
	public String getFullCommandString() {
		return getName();
	}

	@Override
	public List<String> getAliases() {
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!CommandHelpers.processStandardCommands(server, sender, this, args)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return CommandHelpers.getTabCompletionOptionsForSubCommands(server, this, sender, args, pos);
	}

}
