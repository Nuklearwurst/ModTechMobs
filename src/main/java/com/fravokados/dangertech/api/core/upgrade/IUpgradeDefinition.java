package com.fravokados.dangertech.api.core.upgrade;

/**
 * @author Nuklearwurst
 */
public interface IUpgradeDefinition {

	void applyTo(UpgradeStatCollection col);
}
