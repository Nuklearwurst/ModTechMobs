package com.fravokados.dangertech.api.upgrade;

/**
 * @author Nuklearwurst
 */
public enum UpgradeTypes {
	ENERGY_STORAGE("EnergyStorage"), ENERGY_TIER("EnergyTier"), OVERCLOCKER("Overclocker"), DISCONNECT_INCOMING("DisconnectIncoming"),
	REVERSE_DIRECTION("ReverseDirection"), PRODUCTION_SPEED("ProductionSpeed"), ENERGY_USAGE("EnergieUsage");

	public final String id;

	UpgradeTypes(String id) {
		this.id = id;
	}
}
