package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Nuklearwurst
 */
public enum EnergyType {

	INVALID("invalid", 1), IC2("ic2", 1), VANILLA("vanilla", 1), RF("redstoneflux", 0.25F), TESLA("tesla", 0.25F), CREATIVE("creative", 1, TextFormatting.LIGHT_PURPLE);

	public static final String ENERGY_TYPE = "EnergyType";
	public static final String UNLOCALIZED_NAME_PREFIX = "tooltip.item.energyType.";
	public static final String UNLOCALIZED_NAME_SHORT_PREFIX = "tooltip.item.energyType.short.";

	private String name;
	private float conversionFromEU;
	private TextFormatting color;

	EnergyType(String name, float conversionFromEU) {
		this.name = name;
		this.conversionFromEU = conversionFromEU;
		color = null;
	}

	EnergyType(String name, float conversionFromEU, TextFormatting color) {
		this.name = name;
		this.conversionFromEU = conversionFromEU;
		this.color = color;
	}

	public int getId() {
		return ordinal();
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

	public float getConversionFromEU() {
		return conversionFromEU;
	}

	public TextFormatting getColor() {
		return color;
	}

	public boolean hasColor() {
		return color != null;
	}

	public String getColorString() {
		return color == null ? "" : color.toString();
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(ENERGY_TYPE, getId());
	}

	public static EnergyType readFromNBT(NBTTagCompound nbt) {
		int value = nbt.getInteger(ENERGY_TYPE);
		return getEnergyType(value);
	}

	public static EnergyType getEnergyType(int value) {
		if(value < 0 || value >= values().length) {
			return INVALID;
		}
		return values()[value];
	}

	public static String getNBTKey() {
		return ENERGY_TYPE;
	}
}
