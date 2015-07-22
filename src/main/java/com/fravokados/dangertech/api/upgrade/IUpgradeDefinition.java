package com.fravokados.dangertech.api.upgrade;

/**
 * @author Nuklearwurst
 */
public interface IUpgradeDefinition {

	void applyTo(UpgradeStatCollection col);
}
