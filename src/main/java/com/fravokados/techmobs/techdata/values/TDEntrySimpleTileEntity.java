package com.fravokados.techmobs.techdata.values;

import net.minecraft.tileentity.TileEntity;

public class TDEntrySimpleTileEntity extends TDEntryTileEntity {
	
	private int techValue;
	
	public TDEntrySimpleTileEntity(int techValue) {
		this.techValue = techValue;
	}

	@Override
	public int getTechValueForTileEntity(TileEntity te) {
		return techValue;
	}
	
}
