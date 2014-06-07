package com.fravokados.techmobs.techdata.values;

import net.minecraft.tileentity.TileEntity;

/**
 * contains information how tileentity techdata is handled
 * @author Nuklearwurst
 *
 */
public abstract class TDEntryTileEntity {

	
	public abstract int getTechValueForTileEntity(TileEntity te);
}
