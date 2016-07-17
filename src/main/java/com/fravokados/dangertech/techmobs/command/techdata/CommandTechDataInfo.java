package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataInfo extends SubCommand {

	public CommandTechDataInfo() {
		super("info");
	}

	@Override
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		sender.addChatMessage(new TextComponentString(TDTickManager.getTasksInQueue() + " scans in quene!"));
	}
}
