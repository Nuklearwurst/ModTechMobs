package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.techmobs.command.CommandTechData;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.world.techdata.TDChunk;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataRead extends SubCommand {

	public CommandTechDataRead() {
		super("read");
	}

	@Override
	public void processSubCommand(ICommandSender sender, String[] args) {
		TDChunk chunk = CommandTechData.getChunkData(sender);
		sender.addChatMessage(new ChatComponentText("TechLevel in this Chunk: " + chunk.techLevel));
		sender.addChatMessage(new ChatComponentText("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
	}
}
