package com.fravokados.dangertech.core.plugin.ic2;

import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.UpgradeStatCollection;
import com.fravokados.dangertech.api.upgrade.UpgradeTypes;

/**
 * @author Nuklearwurst
 */
public class UpgradeOverclocker implements IUpgradeDefinition {

	private final int count;

	public UpgradeOverclocker(int count) {
		this.count = count;
	}

	@Override
	public void applyTo(UpgradeStatCollection col) {
		col.multiplyFloat(UpgradeTypes.ENERGY_USAGE.id, (float) Math.pow(1.6, count));
		col.multiplyFloat(UpgradeTypes.PRODUCTION_SPEED.id, (float) Math.pow(0.7, count));
	}
}
