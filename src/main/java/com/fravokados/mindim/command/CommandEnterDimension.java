package com.fravokados.mindim.command;

import com.fravokados.mindim.configuration.Settings;
import com.fravokados.mindim.util.TeleportUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

/**
 * teleports player to the mining dimension (same coordinates)
 * @author Nuklearwurst
 */
public class CommandEnterDimension extends CommandBase {
    @Override
    public String getCommandName() {
        return "tpw";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "tpw";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] params) {
        if(sender != null) {
	        if(sender instanceof EntityPlayerMP) {
		        if(((EntityPlayerMP) sender).dimension == Settings.dimensionId) {
			        TeleportUtils.transferPlayerToDimension((EntityPlayerMP) sender, 0);
		        } else {
			        TeleportUtils.transferPlayerToDimension((EntityPlayerMP) sender, Settings.dimensionId);
		        }
	        } else {
		        sender.addChatMessage(new ChatComponentText("This command can only be used as a player!"));
	        }
        } else {
            throw new WrongUsageException("This command can only be used as a player!");
        }
    }
}
