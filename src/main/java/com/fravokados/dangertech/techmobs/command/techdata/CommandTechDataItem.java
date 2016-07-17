package com.fravokados.dangertech.techmobs.command.techdata;

import com.fravokados.dangertech.techmobs.command.CommandHelpers;
import com.fravokados.dangertech.techmobs.command.SubCommand;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataItem extends SubCommand {

	public CommandTechDataItem() {
		super("item");
	}

	@Override
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayer) {
			ItemStack mainHand = ((EntityPlayer) sender).getHeldItemMainhand();
			if(mainHand != null) {
				sender.addChatMessage(new TextComponentTranslation(Strings.Chat.commandAnalyzeItem, TDValues.getInstance().getTechDataForItem(mainHand)));
			} else {
				sender.addChatMessage(new TextComponentTranslation(Strings.Chat.commandAnalyzeItemNoItem));
			}

			ItemStack offHand = ((EntityPlayer) sender).getHeldItemOffhand();
			if(offHand != null) {
				sender.addChatMessage(new TextComponentTranslation(Strings.Chat.commandAnalyzeItem, TDValues.getInstance().getTechDataForItem(offHand)));
			} else {
				sender.addChatMessage(new TextComponentTranslation(Strings.Chat.commandAnalyzeItemNoItem));
			}
		} else {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}
}
