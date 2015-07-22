package com.fravokados.dangertech.techmobs.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.List;
import java.util.SortedSet;

/**
 * adapted from Railcraft <http://www.railcraft.info/>
 */
public interface IModCommand extends ICommand {
	String getFullCommandString();

	@Override
	List<String> getCommandAliases();

	int getPermissionLevel();

	SortedSet<SubCommand> getChildren();

	void printHelp(ICommandSender sender);
}
