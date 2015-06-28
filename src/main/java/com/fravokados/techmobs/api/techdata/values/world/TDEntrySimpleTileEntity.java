package com.fravokados.techmobs.api.techdata.values.world;

import net.minecraft.tileentity.TileEntity;

public class TDEntrySimpleTileEntity extends TDEntryTileEntity {
	
	private final int techValue;
	
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
