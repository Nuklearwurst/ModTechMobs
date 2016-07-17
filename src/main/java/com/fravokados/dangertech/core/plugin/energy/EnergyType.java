package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public enum EnergyType {
	IC2(0, "ic2", 1), VANILLA(1, "vanilla", 1), INVALID(2, "invalid", 1), RF(3, "redstoneflux", 0.25F), TESLA(4, "tesla", 0.25F);

	public static final EnergyType[] validTypes = {IC2, VANILLA, RF};
	public static final String ENERGY_TYPE = "EnergyType";
	public static final String UNLOCALIZED_NAME_PREFIX = "tooltip.item.energyType.";
	public static final String UNLOCALIZED_NAME_SHORT_PREFIX = "tooltip.item.energyType.short.";

	private int id;
	private String name;
	private float conversionFromEU;

	EnergyType(int id, String name, float conversionFromEU) {
		this.id = id;
		this.name = name;
		this.conversionFromEU = conversionFromEU;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(ENERGY_TYPE, ordinal());
	}

	public static EnergyType readFromNBT(NBTTagCompound nbt) {
		int value = nbt.getInteger(ENERGY_TYPE);
		return getEnergyType(value);
	}

	public static EnergyType getEnergyType(int value) {
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

	public String getUnlocalizedNameShort() {
		 return UNLOCALIZED_NAME_SHORT_PREFIX + name;
	}

	public static String getNBTKey() {
		return ENERGY_TYPE;
	}

	public float getConversionFromEU() {
		return conversionFromEU;
	}
}
