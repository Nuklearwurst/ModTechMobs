package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.techmobs.command.CommandTechData;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.world.techdata.TDChunk;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataRead extends SubCommand {

	public CommandTechDataRead() {
		super("read");
	}

	@Override
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		TDChunk chunk = CommandTechData.getChunkData(sender);
		sender.addChatMessage(new TextComponentString("TechLevel in this Chunk: " + chunk.techLevel));
		sender.addChatMessage(new TextComponentString("Scouted TechLevel in this Chunk: " + chunk.scoutedTechLevel));
	}
}
