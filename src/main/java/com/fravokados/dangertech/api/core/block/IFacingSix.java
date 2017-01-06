package com.fravokados.dangertech.api.core.block;

import net.minecraft.util.EnumFacing;

/**
 * helper interface for TileEntities that can face six directions
 *
 * @author Nuklearwurst
 */
public interface IFacingSix {
	void setFacing(EnumFacing f);
	EnumFacing getFacing();
}
