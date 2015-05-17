package com.fravokados.techmobs.command;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CommandTechMobs implements ICommand {
	
	private final List<String> aliases;
	
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
			throw new WrongUsageException(getCommandUsage(sender));
		} else {
			if(args[0].equals("fill")) {
				if(args.length < 8) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				int x1 = getCoordFromCommand(sender.getPlayerCoordinates().posX, args[1]);
				int y1 = getCoordFromCommand(sender.getPlayerCoordinates().posY, args[2]);
				int z1 = getCoordFromCommand(sender.getPlayerCoordinates().posZ, args[3]);
				
				int x2 = getCoordFromCommand(sender.getPlayerCoordinates().posX, args[4]);
				int y2 = getCoordFromCommand(sender.getPlayerCoordinates().posY, args[5]);
				int z2 = getCoordFromCommand(sender.getPlayerCoordinates().posZ, args[6]);
				
				Block block = Block.getBlockFromName(args[7]);
				
				boolean xn = x1 < x2;
				boolean yn = y1 < y2;
				boolean zn = z1 < z2;
				World world = sender.getEntityWorld();
				if(world != null) {	
					for(int x = 0; x <= getDifference(x1, x2); x++) {
						for(int y = 0; y <= getDifference(y1, y2); y++) {
							for(int z = 0; z <= getDifference(z1, z2); z++) {
								int xt = xn ? x1 + x : x1 - x;
								int yt = yn ? y1 + y : y1 - y;
								int zt = zn ? z1 + z: z1 - z;
								world.setBlock(xt, yt, zt, block);
							}
						}
					}
				}
			}
			//does not work
			sender.addChatMessage(new ChatComponentTranslation("WIP", new ChatComponentText("BLABLA")));
		}		
	}
	
	private int getDifference(int i1, int i2) {
		return Math.max(i1, i2) - Math.min(i1, i2);
	}
	
	private int getCoordFromCommand(int pos, String arg) {
		if(arg.startsWith("~")) {
			if(arg.length() == 1) {
				return pos;
			}
			return pos + Integer.parseInt(arg.substring(1));
		}
		return Integer.parseInt(arg);
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
