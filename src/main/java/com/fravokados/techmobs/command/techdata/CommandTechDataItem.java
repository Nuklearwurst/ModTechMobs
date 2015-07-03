package com.fravokados.techmobs.command.techdata;

import com.fravokados.techmobs.command.CommandHelpers;
import com.fravokados.techmobs.command.SubCommand;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.techdata.values.TDValues;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

/**
 * @author Nuklearwurst
 */
public class CommandTechDataItem extends SubCommand {

	public CommandTechDataItem() {
		super("item");
	}

	@Override
	public void processSubCommand(ICommandSender sender, String[] args) {
		if(sender instanceof EntityPlayer) {
			ItemStack stack = ((EntityPlayer) sender).getCurrentEquippedItem();
			if(stack != null) {
				sender.addChatMessage(new ChatComponentTranslation(Strings.Chat.commandAnalyzeItem, TDValues.getInstance().getTechDataForItem(stack)));
			} else {
				sender.addChatMessage(new ChatComponentTranslation(Strings.Chat.commandAnalyzeItemNoItem));
			}
		} else {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}
}
