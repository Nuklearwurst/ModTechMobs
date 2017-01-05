package com.fravokados.dangertech.monsters.block.tileentity;

import com.fravokados.dangertech.api.techdata.values.ITechdataCapability;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * @author Nuklearwurst
 */
public class TileEntityCreativeTechnology extends TileEntity implements ITechdataCapability {

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

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ITechdataCapability.TECHDATA) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ITechdataCapability.TECHDATA) {
			return ITechdataCapability.TECHDATA.cast(this);
		}
		return super.getCapability(capability, facing);
	}
}
