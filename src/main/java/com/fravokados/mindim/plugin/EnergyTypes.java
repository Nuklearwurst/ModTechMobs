package com.fravokados.mindim.plugin;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public enum EnergyTypes {
	IC2, INVALID;

	public static final EnergyTypes[] validTypes = {IC2};

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("EnergyType", ordinal());
	}

	public static EnergyTypes readFromNBT(NBTTagCompound nbt) {
		int value = nbt.getInteger("EnergyType");
		return getEnergyType(value);
	}

	public static EnergyTypes getEnergyType(int value) {
		if(value < 0 || value >= validTypes.length) {
			return INVALID;
		}
		return validTypes[value];
	}
}
