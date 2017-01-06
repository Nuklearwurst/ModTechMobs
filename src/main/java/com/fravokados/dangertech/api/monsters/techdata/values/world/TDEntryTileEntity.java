package com.fravokados.dangertech.api.monsters.techdata.values.world;

import com.fravokados.dangertech.api.monsters.techdata.values.IValueRegistry;
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
	 * @see {@link IValueRegistry#registerTileEntityEntry(Class, TDEntryTileEntity)}
	 * @param te this TileEntity in the world 
	 * @return techValue for this TileEntity
	 */
	public abstract int getTechValueForTileEntity(TileEntity te);
}
