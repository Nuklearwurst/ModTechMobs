package com.fravokados.dangertech.techmobs.command;

import com.fravokados.dangertech.api.techdata.effects.player.TDPlayerEffect;
import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import com.fravokados.dangertech.techmobs.common.EMPExplosion;
import com.fravokados.dangertech.techmobs.techdata.effects.TDEffects;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

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
		addChildCommand(new CommandFill());
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
	public void processCommand(ICommandSender sender, String[] args) {
		if (!CommandHelpers.processStandardCommands(sender, this, args)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return CommandHelpers.addTabCompletionOptionsForSubCommands(this, sender, args);
	}

	public static class CommandFill extends SubCommand {

		public CommandFill() {
			super("fill");
		}

		@Override
		public void processSubCommand(ICommandSender sender, String[] args) {
			if (args.length < 7) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				int x1 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posX, args[0]);
				int y1 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posY, args[1]);
				int z1 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posZ, args[2]);

				int x2 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posX, args[3]);
				int y2 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posY, args[4]);
				int z2 = CommandHelpers.getCoordFromCommand(sender.getPlayerCoordinates().posZ, args[5]);

				Block block = Block.getBlockFromName(args[6]);

				boolean xn = x1 < x2;
				boolean yn = y1 < y2;
				boolean zn = z1 < z2;
				World world = sender.getEntityWorld();
				if (world != null) {
					for (int x = 0; x <= GeneralUtils.getDifference(x1, x2); x++) {
						for (int y = 0; y <= GeneralUtils.getDifference(y1, y2); y++) {
							for (int z = 0; z <= GeneralUtils.getDifference(z1, z2); z++) {
								int xt = xn ? x1 + x : x1 - x;
								int yt = yn ? y1 + y : y1 - y;
								int zt = zn ? z1 + z : z1 - z;
								world.setBlock(xt, yt, zt, block);
							}
						}
					}
				}
			}
		}

		@Override
		public List addTabCompletionOptions(ICommandSender sender, String[] args) {
			if (args.length == 7) {
				return getListOfStringsFromIterableMatchingLastWord(args, Block.blockRegistry.getKeys());
			}
			return null;
		}
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
			public void processSubCommand(ICommandSender sender, String[] args) {
				if (sender instanceof EntityPlayer && args.length == 1) {
					int effectStrength = CommandBase.parseInt(sender, args[0]);
					List<TDPlayerEffect> list = TDEffects.getInstance().getUsablePlayerEffects(effectStrength, sender.getCommandSenderName(), (EntityPlayer) sender);
					int index = GeneralUtils.random.nextInt(list.size());
					int result = list.get(index).applyEffect(effectStrength, sender.getCommandSenderName(), (EntityPlayer) sender);
					sender.addChatMessage(new ChatComponentText("Needed TechValue: " + result));
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
		public void processSubCommand(ICommandSender sender, String[] args) {
			World world = sender.getEntityWorld();
			if((world == null) || (!(sender instanceof EntityPlayer))) {
				CommandHelpers.throwWrongUsage(sender, this);
			}
			EMPExplosion emp = new EMPExplosion(world, ((EntityPlayer)sender).posX, ((EntityPlayer) sender).posY + 1, ((EntityPlayer) sender).posZ, 1, 4);
			emp.doExplosionWithEffects();
//			ModNetworkManager.INSTANCE.sendToAll(new MessageEMP(emp));
		}
	}

}
