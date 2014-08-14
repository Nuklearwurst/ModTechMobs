package com.fravokados.techmobs.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.fravokados.techmobs.techdata.TDManager;

public class CommandTechPlayer extends CommandBase {
	
	private List<String> aliases;
	
	private static final String[] commands1 = { "level", "scouted"};
	private static final String[] commands =  {"set", "add", "remove", "read", "scan", "info"};
	
	public CommandTechPlayer() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techplayer");
		this.aliases.add("tdp");
	}

	@Override
	public String getCommandName() {
		return "techplayer";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "techplayer <command> <params>";
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 1) {
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		} else {
			if(args.length > 3) {
				throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
			}
//			TDPlayer player = getPlayerData(sender);
			String username = sender.getCommandSenderName();
			int scoutedTechLevel = TDManager.getPlayerScoutedTechLevel(username);
			int techLevel = TDManager.getPlayerTechLevel(username);
			if(args[0].equals(commands[0])) { //set
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(username, value);					
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(username, value);
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
			} else if(args[0].equals(commands[1])) { //add
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(username, techLevel + value);
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));				
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(username, scoutedTechLevel + value);
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
			} else if(args[0].equals(commands[2])) { //remove
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(username, techLevel - parseInt(sender, args[1]));		
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));			
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(username, scoutedTechLevel - parseInt(sender, args[1]));
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
			} else if(args[0].equals(commands[3])) { //read
				if(args.length > 2) {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
				if(args.length == 2) {
					
				} else {
					//TODO better chat messages
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				}
			} else if(args[0].equals(commands[4])) { //scan
				if(args.length > 2) {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
				if(args.length == 1 || args[1].equals(commands1[0])) {
					TDManager.scanPlayer(getPlayer(sender));
					sender.addChatMessage(new ChatComponentText("Scanning..."));
					sender.addChatMessage(new ChatComponentText("Updated TechLevel: " + TDManager.getPlayerTechLevel(sender.getCommandSenderName())));
				} else if(args[1].equals(commands1[1])) {
					sender.addChatMessage(new ChatComponentText("Updated Scouted TechLevel: " + TDManager.getPlayerScoutedTechLevel(sender.getCommandSenderName())));
				} else {
					throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
				}
			} else if(args[0].equals(commands[5])) { //info
				sender.addChatMessage(new ChatComponentText("WIP"));				
			} else {
				throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
			}
		}		
	}
	
	private EntityPlayer getPlayer(ICommandSender sender) {
		if(sender instanceof EntityPlayer) {
			return (EntityPlayer) sender;
		}
		return null;
	}
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(args.length == 1) {
			return getListOfStringsMatchingLastWord(args, commands);
		} else if (args.length == 3 && (args[0].equals(commands[0]) || args[0].equals(commands[1]) || args[0].equals(commands[2]))) {
			return getListOfStringsMatchingLastWord(args, commands1);
		} else if (args.length == 2 && args[0].equals(commands[4])) {
			return getListOfStringsMatchingLastWord(args, commands1);
		}
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int index) {
		return false;
	}

}
