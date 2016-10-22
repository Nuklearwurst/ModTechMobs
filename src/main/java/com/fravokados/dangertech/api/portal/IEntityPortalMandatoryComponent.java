package com.fravokados.dangertech.api.portal;

import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.util.math.BlockPos;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent, IFacingSix {
	void setPortalController(BlockPos pos, PortalMetrics metrics);

	void disconnectController();
}
