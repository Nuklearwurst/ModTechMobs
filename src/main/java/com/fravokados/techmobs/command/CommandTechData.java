package com.fravokados.techmobs.command;

import com.fravokados.techmobs.lib.util.world.ChunkLocation;
import com.fravokados.techmobs.lib.util.world.WorldHelper;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.TDTickManager;
import com.fravokados.techmobs.world.TechDataStorage;
import com.fravokados.techmobs.world.techdata.TDChunk;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CommandTechData extends CommandBase {
	
	private final List<String> aliases;
	
	private static final String[] commands1 = { "level", "scouted"};
	private static final String[] commands =  {"set", "add", "remove", "read", "scan", "info"};
	
	public CommandTechData() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techdata");
		this.aliases.add("td");
	}

	@Override
	public String getCommandName() {
		return "techdata";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "techdata <command> <params>";
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
			TDChunk chunk = getChunkData(sender);
			if(args[0].equals(commands[0])) { //set
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					chunk.techLevel = value;					
					sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					chunk.scoutedTechLevel = value;
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[1])) { //add
				int value = parseInt(sender, args[1]);
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					chunk.techLevel += value;	
					sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));				
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					chunk.scoutedTechLevel += value;
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[2])) { //remove
				if(args.length != 3 || args[2].equals(commands1[0])) { //default techlevel
					chunk.techLevel -= parseInt(sender, args[1]);		
					sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));			
				} else if(args[2].equals(commands1[1])) { //scouted techlevel
					chunk.scoutedTechLevel -= parseInt(sender, args[1]);
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
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
					sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));
					sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
				}
			} else if(args[0].equals(commands[4])) { //scan
				if(args.length > 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				if(args.length == 1 || args[1].equals(commands1[0])) {
					TDTickManager.scheduleChunkScan(new ChunkLocation(sender.getEntityWorld().provider.dimensionId, WorldHelper.convertToChunkCoord(sender.getPlayerCoordinates())));
					sender.addChatMessage(new ChatComponentText("Scanning..."));
					sender.addChatMessage(new ChatComponentText(TDTickManager.getTasksInQueue() + " scans in quene!"));
				} else if(args[1].equals(commands1[1])) {
					TDManager.updateScoutedTechLevel(sender.getEntityWorld().provider.dimensionId, WorldHelper.convertToChunkCoord(sender.getPlayerCoordinates()));
					sender.addChatMessage(new ChatComponentText("Updated Scouted TechLevel: " + TDManager.getScoutedTechLevel(sender.getEntityWorld().provider.dimensionId,  WorldHelper.convertToChunkCoord(sender.getPlayerCoordinates()))));
				} else {
					throw new WrongUsageException(getCommandUsage(sender));
				}
			} else if(args[0].equals(commands[5])) { //info
				sender.addChatMessage(new ChatComponentText(TDTickManager.getTasksInQueue() + " scans in quene!"));				
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
			}
		}		
	}

	private TDChunk getChunkData(ICommandSender sender) {
		World world = sender.getEntityWorld();
		ChunkCoordinates coords = sender.getPlayerCoordinates();
		ChunkCoordIntPair chunkCoords = new ChunkCoordIntPair(coords.posX >> 4, coords.posZ >> 4);
		return TechDataStorage.getInstance().getChunkData(chunkCoords, world.provider.dimensionId);
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
