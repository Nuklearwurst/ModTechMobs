package com.fravokados.dangertech.techmobs.command;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import com.fravokados.dangertech.techmobs.common.EMPExplosion;
import com.fravokados.dangertech.techmobs.techdata.effects.TDEffects;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class CommandTechMobs extends CommandBase implements IModCommand {

	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>() {
		@Override
		public int compare(SubCommand o1, SubCommand o2) {
			return o1.compareTo(o2);
		}
	});

	private final List<String> aliases;

	public CommandTechMobs() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techmobs");
		this.aliases.add("tmobs");
		addChildCommand(new Effect());
		addChildCommand(new EMP());
	}

	public void addChildCommand(SubCommand child) {
		child.setParent(this);
		children.add(child);
	}

	@Override
	public String getCommandName() {
		return "techmobs";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}

	@Override
	public String getFullCommandString() {
		return getCommandName();
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public int getPermissionLevel() {
		return SubCommand.PermLevel.ADMIN.permLevel;
	}

	@Override
	public SortedSet<SubCommand> getChildren() {
		return children;
	}

	@Override
	public void printHelp(ICommandSender sender) {
		CommandHelpers.printHelp(sender, this);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!CommandHelpers.processStandardCommands(server, sender, this, args)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return CommandHelpers.getTabCompletionOptionsForSubCommands(server, this, sender, args, pos);
	}

	public static class Effect extends SubCommand {

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
					List<TDPlayerEffect> list = TDEffects.getInstance().getUsablePlayerEffects(effectStrength, sender.getName(), (EntityPlayer) sender);
					int index = GeneralUtils.random.nextInt(list.size());
					int result = list.get(index).applyEffect(effectStrength, sender.getName(), (EntityPlayer) sender);
					sender.addChatMessage(new TextComponentString("Needed TechValue: " + result));
				} else {
					CommandHelpers.throwWrongUsage(sender, this);
				}
			}
		}
	}

	private static class EMP extends SubCommand {

		public EMP() {
			super("emp");
		}

		@Override
		public void processSubCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			World world = sender.getEntityWorld();
			if(!(sender instanceof EntityPlayer)) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			EMPExplosion emp = new EMPExplosion(world, ((EntityPlayer)sender).posX, ((EntityPlayer) sender).posY + 1, ((EntityPlayer) sender).posZ, 1, 4);
			emp.doExplosionWithEffects();
//			ModNetworkManager.INSTANCE.sendToAll(new MessageEMP(emp));
		}
	}

}
