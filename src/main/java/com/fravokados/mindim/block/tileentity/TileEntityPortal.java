package com.fravokados.mindim.block.tileentity;

import com.fravokados.mindim.block.BlockPortalMinDim;
import com.fravokados.mindim.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

/**
 * helper tileentity that stores data used to determine the portals destination
 * will not save or load from disk
 *
 * @author Nuklearwurst
 */
public class TileEntityPortal extends TileEntity {

	private boolean validPortal = false;

	private int coreX;
	private int coreY;
	private int coreZ;

	public void setPortalController(int x, int y, int z) {
		this.coreX = x;
		this.coreY = y;
		this.coreZ = z;
		this.validPortal = true;
	}

	public void onEntityEnterPortal(Entity entity) {
		if(!validPortal) {
			this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return;
		}
		TileEntityPortalControllerEntity controller = getController();
		if(controller != null) {
			controller.teleportEntity(entity);
		}
	}

	public TileEntityPortalControllerEntity getController() {
		TileEntity controller = this.worldObj.getTileEntity(coreX, coreY, coreZ);
		if(controller == null || !(controller instanceof TileEntityPortalControllerEntity)) {
			LogHelper.warn("Invalid Controller Found! portal: [" + xCoord + "; " + yCoord + "; " + zCoord + "], controller: [" + coreX + "; " + coreY + "; " + coreZ + "]");
			((BlockPortalMinDim)blockType).removePortalAndSurroundingPortals(worldObj, xCoord, yCoord, zCoord);
			return null;
		}
		return (TileEntityPortalControllerEntity) controller;
	}
}
