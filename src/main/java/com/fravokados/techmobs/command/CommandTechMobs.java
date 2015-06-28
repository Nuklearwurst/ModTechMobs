package com.fravokados.techmobs.command;

import com.fravokados.techmobs.lib.util.GeneralUtils;
import com.fravokados.techmobs.techdata.effects.TDEffects;
import com.fravokados.techmobs.api.techdata.effects.player.TDPlayerEffect;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
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
		return 0;
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
			if (args.length == 0) {
				CommandHelpers.throwWrongUsage(sender, this);
			} else {
				if (args[0].equals("effect")) {
					if (args.length < 3) {
						throw new WrongUsageException(getCommandUsage(sender));
					}
					if (args[1].equals("player") && sender instanceof EntityPlayer) {
						try {
							int i = Integer.parseInt(args[2]);
							List<TDPlayerEffect> list = TDEffects.getInstance().getUsablePlayerEffects(i, sender.getCommandSenderName(), (EntityPlayer) sender);
							int index = GeneralUtils.random.nextInt(list.size());
							int result = list.get(index).applyEffect(i, sender.getCommandSenderName(), (EntityPlayer) sender);
							sender.addChatMessage(new ChatComponentText("Needed TechValue: " + result));
						} catch (NumberFormatException e) {
							CommandHelpers.throwWrongUsage(sender, this);
						}
					} else {
						CommandHelpers.throwWrongUsage(sender, this);
					}
				} else {
					CommandHelpers.throwWrongUsage(sender, this);
				}
			}
		}
	}

	public class CommandFill extends SubCommand {

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
		public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] args) {
			if(args.length == 7) {
				return getListOfStringsFromIterableMatchingLastWord(args, Block.blockRegistry.getKeys());
			}
			return super.addTabCompletionOptions(p_71516_1_, args);
		}
	}

}
