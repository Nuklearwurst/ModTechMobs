package com.fravokados.techmobs.upgrade;

/**
 * @author Nuklearwurst
 */
public class SimpleNonStackingUpgrade implements IUpgradeDefinition {

	private final String id;

	public SimpleNonStackingUpgrade(String id) {
		this.id = id;
	}

	@Override
	public void applyTo(UpgradeStatCollection col) {
		col.setInt(id, 1); //Using int for now
	}
}
