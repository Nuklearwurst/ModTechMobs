package com.fravokados.dangertech.monsters.block.tileentity;

import com.fravokados.dangertech.api.techdata.values.world.ITechdataTile;
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
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("value", value);
		return tag;
	}

	public void setTechData(int value) {
		this.value = value;
	}
}
