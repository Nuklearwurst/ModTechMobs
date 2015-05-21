package com.fravokados.techmobs.upgrade;

/**
 * @author Nuklearwurst
 */
public enum UpgradeTypes {
	ENERGY_STORAGE("EnergyStorage"), ENERGY_TIER("EnergyTier"), OVERCLOCKER("Overclocker");

	public final String id;

	UpgradeTypes(String id) {
		this.id = id;
	}
}
