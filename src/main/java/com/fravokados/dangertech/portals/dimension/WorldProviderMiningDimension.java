package com.fravokados.dangertech.portals.dimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

/**
 * @author Nuklearwurst
 */
public class WorldProviderMiningDimension extends WorldProvider {

    public WorldProviderMiningDimension()
    {
    }

    @Override
    public String getWelcomeMessage() {
        return "Hello";
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.dimensionType;
    }
}
