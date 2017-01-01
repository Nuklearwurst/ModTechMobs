package com.fravokados.dangertech.portals.block.tileentity;

import com.fravokados.dangertech.portals.block.BlockPortalMinDim;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

/**
 * helper tileentity that stores data used to determine the portals destination
 * will not save or load from disk
 *
 * @author Nuklearwurst
 */
public class TileEntityPortal extends TileEntity {

	private boolean validPortal = false;

	private BlockPos controllerPos;

	public void setPortalController(BlockPos controller) {
		this.controllerPos = controller;
		this.validPortal = true;
	}

	public void onEntityEnterPortal(Entity entity) {
		if(!validPortal) {
			this.getWorld().setBlockToAir(getPos());
			return;
		}
		TileEntityPortalControllerEntity controller = getController();
		if(controller != null) {
			controller.teleportEntity(entity);
		}
	}

	@Nullable
	public TileEntityPortalControllerEntity getController() {
		TileEntity controller = this.getWorld().getTileEntity(this.controllerPos);
		if(controller == null || !(controller instanceof TileEntityPortalControllerEntity)) {
			LogHelperMD.warn("Invalid Controller Found! portal: ["
					+ getPos().getX() + "; " + getPos().getY() + "; " + getPos().getZ() + "], controller: ["
					+ controllerPos.getX() + "; " + controllerPos.getY() + "; " + controllerPos.getZ() + "]");
			((BlockPortalMinDim)blockType).removePortalAndSurroundingPortals(getWorld(), getPos());
			return null;
		}
		return (TileEntityPortalControllerEntity) controller;
	}
}
