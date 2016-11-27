package com.fravokados.dangertech.monsters.command.techdata;

import com.fravokados.dangertech.monsters.command.CommandTechData;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.world.techdata.TDChunk;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

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
		sender.sendMessage(new TextComponentTranslation("chat.command.techdata.level", chunk.techLevel));
		sender.sendMessage(new TextComponentTranslation("chat.command.techdata.scouted", chunk.scoutedTechLevel));
	}
}
