package com.fravokados.dangertech.api.portal;

import com.fravokados.dangertech.api.block.IFacingSix;
import net.minecraft.util.BlockPos;

/**
 * @author Nuklearwurst
 */
public interface IEntityPortalMandatoryComponent extends IEntityPortalComponent, IFacingSix {
	void setPortalController(BlockPos pos);
}
