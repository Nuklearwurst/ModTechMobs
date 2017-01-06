package com.fravokados.dangertech.core.upgrade;

import com.fravokados.dangertech.api.core.upgrade.IUpgrade;
import com.fravokados.dangertech.api.core.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.core.plugin.ic2.IC2UpgradeIntegration;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class UpgradeHelper {

	public static boolean isUpgrade(@Nullable ItemStack item) {
		return item != null && (item.getItem() instanceof IUpgrade || IC2UpgradeIntegration.isUpgrade(item));
	}

	@Nullable
	public static IUpgradeDefinition getUpgradeDefinition(@Nullable ItemStack item) {
		if(item != null) {
			if (item.getItem() instanceof IUpgrade) {
				return ((IUpgrade) item.getItem()).getUpgradeDefinition(item);
			} else if (IC2UpgradeIntegration.isUpgrade(item)) {
				return IC2UpgradeIntegration.getUpgrade(item);
			}
		}
		return null;
	}

	public static List<IUpgradeDefinition> getUpgradesFromItems(ItemStack[] items) {
		List<IUpgradeDefinition> out = new ArrayList<IUpgradeDefinition>();
		for(ItemStack stack : items) {
			IUpgradeDefinition def = getUpgradeDefinition(stack);
			if(def != null) {
				//always add the UpgradeDefinition
				//duplicates should stack accorddingly
				//we can't easily check for equality
				out.add(def);
			}
		}
		return out;
	}
}
