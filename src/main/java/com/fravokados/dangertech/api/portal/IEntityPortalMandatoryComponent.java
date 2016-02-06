package com.fravokados.dangertech.api.portal;

import net.minecraft.util.BlockPos;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent {
	void setPortalController(BlockPos pos);

	short getFacing();
}
