package com.fravokados.mindim.block.tileentity;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent {
	void setPortalController(int xCoord, int yCoord, int zCoord);

	short getFacing();
}
