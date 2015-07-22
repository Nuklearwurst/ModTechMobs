package com.fravokados.dangertech.api.portal;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent {
	void setPortalController(int xCoord, int yCoord, int zCoord);

	short getFacing();
}
