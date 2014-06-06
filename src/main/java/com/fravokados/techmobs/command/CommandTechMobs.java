package com.fravokados.techmobs.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandTechMobs implements ICommand {
	
	private List<String> aliases;
	
	public CommandTechMobs() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techmobs");
		this.aliases.add("tmobs");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "techmobs";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "techmobs <command>";
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0) {
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		} else {
			//does not work
			sender.addChatMessage(new ChatComponentTranslation("WIP", new ChatComponentText("BLABLA")));
		}		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] var2) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int var2) {
		return false;
	}

}
