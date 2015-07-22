package com.fravokados.dangertech.api.upgrade;

/**
 * @author Nuklearwurst
 */
public class SimpleIntegerUpgrade implements IUpgradeDefinition {

	private final int amount;
	private final String id;

	public SimpleIntegerUpgrade(int count, int amountPerItem, String id) {
		this.amount = count * amountPerItem;
		this.id = id;
	}

	@Override
	public void applyTo(UpgradeStatCollection col) {
		col.addInt(id, amount);
	}
}
