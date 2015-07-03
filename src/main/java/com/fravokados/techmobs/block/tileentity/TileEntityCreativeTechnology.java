package com.fravokados.techmobs.block.tileentity;

import com.fravokados.techmobs.api.techdata.values.world.ITechdataTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Nuklearwurst
 */
public class TileEntityCreativeTechnology extends TileEntity implements ITechdataTile {

	private int value = 0;

	public TileEntityCreativeTechnology() {
		super();
	}

	@Override
	public int getTechData() {
		return value;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		value = tag.getInteger("value");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("value", value);
	}

	public void setTechData(int value) {
		this.value = value;
	}
}
