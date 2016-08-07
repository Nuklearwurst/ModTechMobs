package com.fravokados.dangertech.portals.command;

import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.lib.util.TeleportUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayerMP) {
			if(((EntityPlayerMP) sender).dimension == Settings.dimensionId) {
				TeleportUtils.transferPlayerToDimension((EntityPlayerMP) sender, 0);
			} else {
				TeleportUtils.transferPlayerToDimension((EntityPlayerMP) sender, Settings.dimensionId);
			}
		} else {
			throw new WrongUsageException("This command can only be used as a player!");
		}
	}
}
