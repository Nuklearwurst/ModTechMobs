package com.fravokados.dangertech.core.upgrade;

import com.fravokados.dangertech.api.upgrade.IUpgrade;
import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.core.plugin.ic2.IC2UpgradeIntegration;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nuklearwurst
 */
public class UpgradeHelper {

	public static boolean isUpgrade(@Nullable ItemStack item) {
		return item != null && (item.getItem() instanceof IUpgrade || IC2UpgradeIntegration.isUpgrade(item));
	}

	@Nullable
	public static IUpgradeDefinition getUpgradeDefinition(ItemStack item) {
		if (!item.isEmpty()) {
			if (item.getItem() instanceof IUpgrade) {
				return ((IUpgrade) item.getItem()).getUpgradeDefinition(item);
			} else if (IC2UpgradeIntegration.isUpgrade(item)) {
				return IC2UpgradeIntegration.getUpgrade(item);
			}
		}
		return null;
	}

	public static List<IUpgradeDefinition> getUpgradesFromItems(List<ItemStack> items) {
		//always add the UpgradeDefinition
		//duplicates should stack accorddingly
		//we can't easily check for equality
		return items.stream()
				.map(UpgradeHelper::getUpgradeDefinition)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
