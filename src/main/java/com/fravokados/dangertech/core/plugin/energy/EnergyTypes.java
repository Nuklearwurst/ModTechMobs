package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public enum EnergyTypes {
	IC2, VANILLA, INVALID;

	public static final EnergyTypes[] validTypes = {IC2, VANILLA};
	public static final String ENERGY_TYPE = "EnergyType";

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(ENERGY_TYPE, ordinal());
	}

	public static EnergyTypes readFromNBT(NBTTagCompound nbt) {
		int value = nbt.getInteger(ENERGY_TYPE);
		return getEnergyType(value);
	}

	public static EnergyTypes getEnergyType(int value) {
		if(value < 0 || value >= validTypes.length) {
			return INVALID;
		}
		return validTypes[value];
	}

	public int getId() {
		return ordinal();
	}

	public static String getNBTKey() {
		return ENERGY_TYPE;
	}
}
