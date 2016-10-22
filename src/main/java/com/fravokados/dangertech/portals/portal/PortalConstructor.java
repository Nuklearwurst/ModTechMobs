package com.fravokados.dangertech.portals.portal;

import com.fravokados.dangertech.api.portal.IEntityPortalComponent;
import com.fravokados.dangertech.api.portal.IEntityPortalMandatoryComponent;
import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import com.fravokados.dangertech.portals.lib.util.SimpleObjectReference;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Nuklearwurst
 */
public class PortalConstructor {

	public enum Result {
		SUCCESS, ERROR_INVALID_STRUCTURE, ERROR_MULTIPLE_CONTROLLERS, ERROR_MISSING_CONTOLLER,
		ERROR_TO_BIG, ERROR_TO_SMALL, ERROR_OPEN_PORTAL, ERROR_NO_PORTAL_BLOCK, ERROR_UNKNOWN,
		ERROR_PORTAL_NOT_EMPTY, ERROR_PORTAL_FRAME_INCOMPLETE
	}

	/**
	 * tries to find portals around the given position (excluding it)
	 */
	public static void createPortalsAround(World world, BlockPos pos) {
		for(EnumFacing facing : EnumFacing.VALUES) {
			createPortalMultiBlock(world, pos.offset(facing), pos);
		}
	}

	public static Result createPortalMultiBlock(World world, BlockPos pos) {
		List<IEntityPortalMandatoryComponent> frames = new ArrayList<IEntityPortalMandatoryComponent>();
		return createPortalMultiBlock(world, pos, frames, null);
	}

	public static Result createPortalMultiBlock(World world, BlockPos pos, @Nullable BlockPos excluding) {
		List<IEntityPortalMandatoryComponent> frames = new ArrayList<IEntityPortalMandatoryComponent>();
		return createPortalMultiBlock(world, pos, frames, excluding);
	}

	public static Result createPortalMultiBlock(World world, BlockPos pos, List<IEntityPortalMandatoryComponent> frames) {
		return createPortalMultiBlock(world, pos, frames, null);
	}

	public static Result createPortalMultiBlock(World world, BlockPos pos, List<IEntityPortalMandatoryComponent> frames, @Nullable BlockPos excluding) {
		LogHelperMD.logDev("Searching for Portal MultiBlock");
		//settings up
		SimpleObjectReference<TileEntityPortalControllerEntity> controller = new SimpleObjectReference<TileEntityPortalControllerEntity>();
		PortalMetrics metrics = new PortalMetrics();
		//Finding connected Blocks
		Result result = createPortalMultiBlock(world, pos, frames, controller, metrics, excluding);
		//Checking structure
		if (result == Result.SUCCESS && controller.isNull()) {
			LogHelperMD.logDev("MultiBlock is missing a controller!");
			return Result.ERROR_MISSING_CONTOLLER;
		}
		if (result != Result.SUCCESS) {
			LogHelperMD.logDev("MultiBlock forming failed: " + result);
			return result;
		}
		if (metrics.isBlockPartOfFrame(excluding) || !metrics.isFrameComplete(world)) {
			LogHelperMD.logDev("MultiBlock Frame incomplete!");
			return Result.ERROR_PORTAL_FRAME_INCOMPLETE;
		}
		if (!metrics.isFrameEmpty(world)) {
			LogHelperMD.logDev("MultiBlock Frame is not empty!");
			return Result.ERROR_PORTAL_NOT_EMPTY;
		}
		if (metrics.smallestDimension() <= Settings.MIN_PORTAL_SIZE) {
			LogHelperMD.logDev("MultiBlock to small!");
			return Result.ERROR_TO_SMALL;
		}
		//calculate Origin
		metrics.calculateOrigin(frames);
		//update controller
		controller.get().setMetrics(metrics);

		//update frames
		for (IEntityPortalMandatoryComponent frame : frames) {
			frame.setPortalController(controller.get().getPos(), metrics);
		}
		LogHelperMD.logDev("Successfully formed multiblock");
		return Result.SUCCESS;
	}

	/**
	 * finds all connected PortalFrameBlocks (and controller)
	 *
	 * @param world      The World
	 * @param pos        starting position
	 * @param frames     List to store found Frames
	 * @param controller Controller reference to update
	 * @param metrics    Metrics to update
	 * @return SUCCESS if no error was found, size and completion still has to be checked
	 */
	private static Result createPortalMultiBlock(World world, BlockPos pos, List<IEntityPortalMandatoryComponent> frames, SimpleObjectReference<TileEntityPortalControllerEntity> controller, PortalMetrics metrics) {
		return createPortalMultiBlock(world, pos, frames, controller, metrics, null);
	}

	/**
	 * finds all connected PortalFrameBlocks (and controller)
	 *
	 * @param world      The World
	 * @param pos        starting position
	 * @param frames     List to store found Frames
	 * @param controller Controller reference to update
	 * @param metrics    Metrics to update
	 * @param excluding  this position will not get scanned (cannot be equals to {@code pos})
	 * @return SUCCESS if no error was found, size and completion still has to be checked
	 */
	private static Result createPortalMultiBlock(World world, BlockPos pos, List<IEntityPortalMandatoryComponent> frames, SimpleObjectReference<TileEntityPortalControllerEntity> controller, PortalMetrics metrics, @Nullable BlockPos excluding) {
		assert pos != excluding;

		Stack<TileEntity> blocksToScan = new Stack<TileEntity>();
		TileEntity te = world.getTileEntity(pos);
		if (te == null || !(te instanceof IEntityPortalComponent)) {
			return Result.ERROR_NO_PORTAL_BLOCK;
		}
		blocksToScan.push(te);
		while (!blocksToScan.empty()) {
			te = blocksToScan.pop();
			if (te instanceof IEntityPortalMandatoryComponent) {
				metrics.addCoord(te);
				frames.add((IEntityPortalMandatoryComponent) te);
			} else if (te instanceof TileEntityPortalControllerEntity) {
				((TileEntityPortalControllerEntity) te).setState(TileEntityPortalControllerEntity.State.NO_MULTIBLOCK);
				if (controller.isNull()) {
					controller.set((TileEntityPortalControllerEntity) te);
				} else {
					return Result.ERROR_MULTIPLE_CONTROLLERS;
				}
			} else {
				return Result.ERROR_UNKNOWN;
			}
			//Check max size
			if (metrics.biggestDimension() > Settings.MAX_PORTAL_SIZE) {
				return Result.ERROR_TO_BIG;
			}
			for (EnumFacing dir : EnumFacing.VALUES) {
				TileEntity te2 = world.getTileEntity(te.getPos().offset(dir));
				//make sure we found a new one
				if (te2 != null
						&& !te2.getPos().equals(excluding)
						&& !blocksToScan.contains(te2)
						&& ((te2 instanceof IEntityPortalMandatoryComponent && !frames.contains(te2))
										|| (te2 instanceof TileEntityPortalControllerEntity && controller.get() != te2))) {
					blocksToScan.push(te2);
				}
			}
		}
		return Result.SUCCESS;
	}

	/**
	 * creates a Portal
	 *
	 * @param w           The world to place the Portal in
	 * @param m           The Metrics of the portal
	 * @param clearInside whether inside should be cleared (WIP unused/unnecessary)
	 * @return success
	 */
	public static boolean createPortalFromMetrics(World w, PortalMetrics m, boolean clearInside, int yOffset) {
		return m.placePortalFrame(w, (int) m.originX, ((int) m.originY) + yOffset, (int) m.originZ, clearInside);
	}
}