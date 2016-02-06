package com.fravokados.dangertech.techmobs.command;

import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.techmobs.command.techdata.*;
import com.fravokados.dangertech.techmobs.world.TechDataStorage;
import com.fravokados.dangertech.techmobs.world.techdata.TDChunk;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.*;

public class CommandTechData extends CommandBase implements IModCommand {

	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>() {
		@Override
		public int compare(SubCommand o1, SubCommand o2) {
			return o1.compareTo(o2);
		}
	});

	private final List<String> aliases;


	public CommandTechData() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("techdata");
		this.aliases.add("td");
		addChildCommand(new CommandTechDataItem());
		addChildCommand(new CommandTechDataSet());
		addChildCommand(new CommandTechDataAdd());
		addChildCommand(new CommandTechDataRead());
		addChildCommand(new CommandTechDataScan());
		addChildCommand(new CommandTechDataInfo());
	}


	public void addChildCommand(SubCommand child) {
		child.setParent(this);
		children.add(child);
	}

	@Override
	public String getCommandName() {
		return "techdata";
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
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (!CommandHelpers.processStandardCommands(sender, this, args)) {
			CommandHelpers.throwWrongUsage(sender, this);
		}
	}

	public static TDChunk getChunkData(ICommandSender sender) {
		World world = sender.getEntityWorld();
		BlockPos coords = sender.getPosition();
		ChunkCoordIntPair chunkCoords = WorldUtils.convertToChunkCoord(coords);
		return TechDataStorage.getInstance().getChunkData(chunkCoords, world.provider.getDimensionId());
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return CommandHelpers.addTabCompletionOptionsForSubCommands(this, sender, args, pos);
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int index) {
		return false;
	}
}
