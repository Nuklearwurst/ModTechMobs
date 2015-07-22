package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataInfo extends SubCommand {

	public CommandTechDataInfo() {
		super("info");
	}

	@Override
	public void processSubCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(new ChatComponentText(TDTickManager.getTasksInQueue() + " scans in quene!"));
	}
}
