package com.fravokados.dangertech.monsters.command;

import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.monsters.command.techdata.*;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import com.fravokados.dangertech.monsters.world.techdata.TDChunk;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class CommandTechData extends CommandBase implements IModCommand {

	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(SubCommand::compareTo);

	private final List<String> aliases;


	public CommandTechData() {
		this.aliases = new ArrayList<>();
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
	public String getName() {
		return "techdata";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + this.getName() + " help";
	}

	@Override
	public String getFullCommandString() {
		return getName();
	}

	@Override
	public List<String> getAliases() {
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

	public static TDChunk getChunkData(ICommandSender sender) {
		World world = sender.getEntityWorld();
		BlockPos coords = sender.getPosition();
		ChunkPos chunkCoords = WorldUtils.convertToChunkCoord(coords);
		return TechDataStorage.getInstance().getChunkData(chunkCoords, world.provider.getDimension());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return CommandHelpers.getTabCompletionOptionsForSubCommands(server, this, sender, args, pos);
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int index) {
		return false;
	}
}
