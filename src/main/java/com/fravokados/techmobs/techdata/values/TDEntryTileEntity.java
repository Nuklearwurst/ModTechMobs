package com.fravokados.techmobs.techdata.values;

import net.minecraft.tileentity.TileEntity;

/**
 * contains information how tileentity techdata is handled
 * @author Nuklearwurst
 *
 */
public abstract class TDEntryTileEntity {

	/**
	 * return how much this tileEnity is worth<br>
	 * the given TileEntity is of the same class this Entry got registered with
	 * @see {@link TDValues#registerTileEntityEntry(Class, TDEntryTileEntity)} 
	 * @param te this TileEntity in the world 
	 * @return techValue for this TileEntity
	 */
	public abstract int getTechValueForTileEntity(TileEntity te);
}
