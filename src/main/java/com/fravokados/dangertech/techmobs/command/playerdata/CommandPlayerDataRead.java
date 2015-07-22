package com.fravokados.dangertech.techmobs.command.playerdata;

import com.fravokados.dangertech.techmobs.command.CommandHelpers;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class CommandPlayerDataRead extends SubCommand {

	public CommandPlayerDataRead() {
		super("read");
	}

	@Override
	public void processSubCommand(ICommandSender sender, String[] args) {
		EntityPlayer player = null;
		if(args.length == 1) {
			player = CommandBase.getPlayer(sender, args[0]);
		} else if(args.length == 0 && sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;
		} else {
			CommandHelpers.throwWrongUsage(sender, this);
		}
		if(player == null) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
		int scoutedTechLevel = TDManager.getPlayerScoutedTechLevel(player);
		int techLevel = TDManager.getPlayerTechLevel(player);
		sender.addChatMessage(new ChatComponentText("TechLevel of this Player: " + techLevel));
		sender.addChatMessage(new ChatComponentText("Scouted TechLevel of this Player: " + scoutedTechLevel));
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}
}
