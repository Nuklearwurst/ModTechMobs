package com.fravokados.techmobs.api.upgrade;

/**
 * @author Nuklearwurst
 */
public class SimpleNonStackingUpgrade implements IUpgradeDefinition {

	private final String id;

	public SimpleNonStackingUpgrade(String id) {
		this.id = id;
	}

	public SimpleNonStackingUpgrade(UpgradeTypes type) {
		this(type.id);
	}

	@Override
	public void applyTo(UpgradeStatCollection col) {
		col.addUpgrade(id);
	}
}
