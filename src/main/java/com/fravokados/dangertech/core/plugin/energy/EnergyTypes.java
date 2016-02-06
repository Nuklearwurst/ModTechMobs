package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public enum EnergyTypes {
	IC2, VANILLA, INVALID;

	public static final EnergyTypes[] validTypes = {IC2, VANILLA};

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
