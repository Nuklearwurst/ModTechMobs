package com.fravokados.dangertech.monsters.command.playerdata;

import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class CommandPlayerDataRead extends SubCommand {

	public CommandPlayerDataRead() {
		super("read");
	}

	@Override
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = null;
		if(args.length == 1) {
			player = CommandBase.getPlayer(server, sender, args[0]);
		} else if(args.length == 0 && sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;
		} else {
			CommandHelpers.throwWrongUsage(sender, this);
		}
		int scoutedTechLevel = TDManager.getPlayerScoutedTechLevel(player);
		int techLevel = TDManager.getPlayerTechLevel(player);
		sender.sendMessage(new TextComponentString("TechLevel of this Player: " + techLevel));
		sender.sendMessage(new TextComponentString("Scouted TechLevel of this Player: " + scoutedTechLevel));
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if(args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		return new ArrayList<>();
	}
}
