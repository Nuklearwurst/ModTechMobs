package com.fravokados.mindim.portal;

import com.fravokados.mindim.block.tileentity.IEntityPortalComponent;
import com.fravokados.mindim.block.tileentity.IEntityPortalMandatoryComponent;
import com.fravokados.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.mindim.configuration.Settings;
import com.fravokados.mindim.util.LogHelper;
import com.fravokados.mindim.util.SimpleObjectReference;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

	public static Result createPortalMultiBlock(World world, int x, int y, int z) {
		List<IEntityPortalMandatoryComponent> frames = new ArrayList<IEntityPortalMandatoryComponent>();
		return createPortalMultiBlock(world, x, y, z, frames);
	}

	public static Result createPortalMultiBlock(World world, int x, int y, int z, List<IEntityPortalMandatoryComponent> frames) {
		LogHelper.logDev("Searching for Portal MultiBlock");
		//settings up
		SimpleObjectReference<TileEntityPortalControllerEntity> controller = new SimpleObjectReference<TileEntityPortalControllerEntity>();
		PortalMetrics metrics = new PortalMetrics();
		//Finding connected Blocks
		Result result = createPortalMultiBlock(world, x, y, z, frames, controller, metrics);
		//Checking structure
		if (result != Result.SUCCESS) {
			LogHelper.logDev("MultiBlock forming failed: " + result);
			return result;
		}
		if (controller.isNull()) {
			LogHelper.logDev("MultiBlock is missing a controller!");
			return Result.ERROR_MISSING_CONTOLLER;
		}
		if(!metrics.isFrameComplete(world)) {
			LogHelper.logDev("MultiBlock Frame incomplete!");
			return Result.ERROR_PORTAL_FRAME_INCOMPLETE;
		}
		if(!metrics.isFrameEmpty(world)) {
			LogHelper.logDev("MultiBlock Frame is not empty!");
			return Result.ERROR_PORTAL_NOT_EMPTY;
		}
		if (metrics.smallestDimension() <= Settings.MIN_PORTAL_SIZE) {
			LogHelper.logDev("MultiBlock to small!");
			return Result.ERROR_TO_SMALL;
		}
		//update frames
		for (IEntityPortalMandatoryComponent frame : frames) {
			frame.setPortalController(controller.get().xCoord, controller.get().yCoord, controller.get().zCoord);
		}
		//calculate Origin
		metrics.calculateOrigin(frames);
		//update controller
		controller.get().setMetrics(metrics);

		LogHelper.logDev("Successfully formed multiblock");
		return Result.SUCCESS;
	}

	/**
	 * finds all connected PortalFrameBlocsk (and controller)
	 * @param world The World
	 * @param x starting position X
	 * @param y starting position Y
	 * @param z starting position Z
	 * @param frames List to store found Frames
	 * @param controller Controller reference to update
	 * @param metrics Metrics to update
	 * @return SUCCESS if no error was found, size and completion still has to be checked
	 */
	private static Result createPortalMultiBlock(World world, int x, int y, int z, List<IEntityPortalMandatoryComponent> frames, SimpleObjectReference<TileEntityPortalControllerEntity> controller, PortalMetrics metrics) {
		Stack<TileEntity> blocksToScan = new Stack<TileEntity>();
		TileEntity te = world.getTileEntity(x, y, z);
		if(te == null || !(te instanceof IEntityPortalComponent)) {
			return Result.ERROR_NO_PORTAL_BLOCK;
		}
		blocksToScan.push(te);
		while (!blocksToScan.empty()) {
			te = blocksToScan.pop();
			if(te instanceof IEntityPortalMandatoryComponent) {
				metrics.addCoord(te);
				frames.add((IEntityPortalMandatoryComponent) te);
			} else if(te instanceof TileEntityPortalControllerEntity) {
				if(controller.isNull()) {
					controller.set((TileEntityPortalControllerEntity) te);
				} else {
					return Result.ERROR_MULTIPLE_CONTROLLERS;
				}
			} else {
				return Result.ERROR_UNKNOWN;
			}
			//Check max size
			if(metrics.biggestDimension() > Settings.MAX_PORTAL_SIZE) {
				return Result.ERROR_TO_BIG;
			}
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				TileEntity te2 = world.getTileEntity(te.xCoord + dir.offsetX, te.yCoord + dir.offsetY, te.zCoord + dir.offsetZ);
				//make sure we found a new one
				if(te2 != null &&
						(!blocksToScan.contains(te2)) &&
						((te2 instanceof IEntityPortalMandatoryComponent && !frames.contains(te2)) ||
						(te2 instanceof TileEntityPortalControllerEntity && controller.get() != te2))) {
					blocksToScan.push(te2);
				}
			}
		}
		return Result.SUCCESS;
	}

	/**
	 * creates a Portal
	 * @param w The world to place the Portal in
	 * @param m The Metrics of the portal
	 * @param clearInside whether inside should be cleared (WIP unused/unnecessary)
	 * @return success
	 */
	public static boolean createPortalFromMetrics(World w, PortalMetrics m, boolean clearInside, int yOffset) {
		return m.placePortalFrame(w, (int) m.originX, ((int) m.originY) + yOffset, (int) m.originZ, clearInside);
	}
}