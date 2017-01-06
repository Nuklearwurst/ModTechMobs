package com.fravokados.dangertech.monsters.command.techmobs;

import com.fravokados.dangertech.api.monsters.techdata.effects.player.TDPlayerEffect;
import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffects;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class Effect extends SubCommand {

	public Effect() {
		super("effect");
		addChildCommand(new Player());
	}

	public static class Player extends SubCommand {

		public Player() {
			super("player");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			if (sender instanceof EntityPlayer && args.length == 1) {
				int effectStrength = CommandBase.parseInt(args[0]);
				List<TDPlayerEffect> list = TDEffects.getInstance().getUsablePlayerEffects(effectStrength, ((EntityPlayer) sender).getUniqueID(), (EntityPlayer) sender);
				int index = GeneralUtils.random.nextInt(list.size());
				int result = list.get(index).applyEffect(effectStrength, ((EntityPlayer) sender).getUniqueID(), (EntityPlayer) sender);
				sender.sendMessage(new TextComponentString("Needed TechValue: " + result));
			} else {
				CommandHelpers.throwWrongUsage(sender, this);
			}
		}
	}
}
