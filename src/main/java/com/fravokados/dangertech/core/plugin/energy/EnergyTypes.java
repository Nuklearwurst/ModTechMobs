package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public enum EnergyTypes {
	IC2(0, "ic2"), VANILLA(1, "vanilla"), INVALID(2, "invalid");

	public static final EnergyTypes[] validTypes = {IC2, VANILLA};
	public static final String ENERGY_TYPE = "EnergyType";
	public static final String UNLOCALIZED_NAME_PREFIX = "tooltip.item.energyType.";

	private int id;
	private String name;

	EnergyTypes(int id, String name) {
		this.id = id;
		this.name = name;
	}

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
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUnlocalizedName() {
		 return UNLOCALIZED_NAME_PREFIX + name;
	}

	public static String getNBTKey() {
		return ENERGY_TYPE;
	}
}
