package com.fravokados.techmobs.upgrade;

/**
 * @author Nuklearwurst
 */
public enum UpgradeTypes {
	ENERGY_STORAGE("EnergyStorage"), ENERGY_TIER("EnergyTier"), OVERCLOCKER("Overclocker"), DISCONNECT_INCOMING("DisconnectIncoming"),
	REVERSE_DIRECTION("ReverseDirection");

	public final String id;

	UpgradeTypes(String id) {
		this.id = id;
	}
}
