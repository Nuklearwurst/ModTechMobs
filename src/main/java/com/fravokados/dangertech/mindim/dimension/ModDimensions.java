package com.fravokados.dangertech.mindim.dimension;

import com.fravokados.dangertech.mindim.configuration.Settings;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

/**
 * @author Nuklearwurst
 */
public class ModDimensions {

	public static DimensionType dimensionType;

	public static void init() {
		dimensionType = DimensionType.register("Mining Dimension", "_mindim", Settings.providerId, WorldProviderMiningDimension.class, false);

		Settings.dimensionId = DimensionManager.getNextFreeDimId();
		DimensionManager.registerDimension(Settings.dimensionId, dimensionType);
	}

}
