package com.fravokados.techmobs.techdata.values.world;

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
	
	@Override
	public String toString() {
		return "Simple TileEntityTDEntry:" + techValue;
	}
}
