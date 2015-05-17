package com.fravokados.techmobs.command;

import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.world.TechDataStorage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandTechPlayer extends CommandBase {
	
	private final List<String> aliases;
	
	private static final String[] commands1 = { "level", "scouted"};
	private static final String[] commands =  {"set", "add", "remove", "read", "scan", "info", "rnddp"};
	
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
		return "techplayer <command> <params> [player]";
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 1) {
			throw new WrongUsageException(getCommandUsage(sender));
		} else {
			if(args.length > 3) {
				throw new WrongUsageException(getCommandUsage(sender));
			}
			EntityPlayer entityPlayer = getPlayer(sender);
			int scoutedTechLevel = TDManager.getPlayerScoutedTechLevel(entityPlayer);
			int techLevel = TDManager.getPlayerTechLevel(entityPlayer);
			if(args[0].equals(commands[0])) { //set
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(entityPlayer, value);					
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(entityPlayer, value);
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[1])) { //add
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(entityPlayer, techLevel + value);
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));				
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(entityPlayer, scoutedTechLevel + value);
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[2])) { //remove
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					TDManager.setPlayerTechLevel(entityPlayer, techLevel - parseInt(sender, args[1]));		
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));			
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					TDManager.setPlayerScoutedTechLevel(entityPlayer, scoutedTechLevel - parseInt(sender, args[1]));
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[3])) { //read
				if(args.length > 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				if(args.length == 2) {
					
				} else {
					//TODO better chat messages
					sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
				}
			} else if(args[0].equals(commands[4])) { //scan
				if(args.length > 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				if(args.length == 1 || args[1].equals(commands1[0])) {
					TDManager.scanPlayer(getPlayer(sender));
					sender.addChatMessage(new ChatComponentText("Scanning..."));
					sender.addChatMessage(new ChatComponentText("Updated TechLevel: " + TDManager.getPlayerTechLevel(entityPlayer)));
				} else if(args[1].equals(commands1[1])) {
					sender.addChatMessage(new ChatComponentText("Updated Scouted TechLevel: " + TDManager.getPlayerScoutedTechLevel(entityPlayer)));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[5])) { //info
				sender.addChatMessage(new ChatComponentText("WIP"));				
			} else if(args[0].equals(commands[6])) { //random tech player
				sender.addChatMessage(new ChatComponentText("Random Tech Player: " + TechDataStorage.getRandomDangerousPlayer(entityPlayer.getRNG())));
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
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
