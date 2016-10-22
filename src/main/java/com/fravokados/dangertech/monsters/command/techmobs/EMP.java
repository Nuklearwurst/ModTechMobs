package com.fravokados.dangertech.monsters.command.techmobs;

import com.fravokados.dangertech.monsters.command.CommandHelpers;
import com.fravokados.dangertech.monsters.command.SubCommand;
import com.fravokados.dangertech.monsters.common.EMPExplosion;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class EMP extends SubCommand {

	public EMP() {
		super("emp");
	}

	@Override
	public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();
		if (!(sender instanceof EntityPlayer)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
		EMPExplosion emp = new EMPExplosion(world, ((EntityPlayer) sender).posX, ((EntityPlayer) sender).posY + 1, ((EntityPlayer) sender).posZ, 1, 4);
		emp.doExplosionWithEffects();
//			ModNetworkManager.INSTANCE.sendToAll(new MessageEMP(emp));
	}
}
