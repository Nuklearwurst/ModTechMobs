package com.fravokados.dangertech.api.portals.component;

import com.fravokados.dangertech.api.core.block.IFacingSix;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.util.math.BlockPos;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent, IFacingSix {
	void setPortalController(BlockPos pos, PortalMetrics metrics);

	void disconnectController();
}
