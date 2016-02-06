package com.fravokados.dangertech.mindim.dimension;

import com.fravokados.dangertech.mindim.configuration.Settings;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

/**
 * @author Nuklearwurst
 */
public class WorldProviderMiningDimension extends WorldProvider {

	@Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0.8F);
        this.dimensionId = Settings.dimensionId;

    }

	@Override
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderGenerate(worldObj, worldObj.getSeed() + 42, true, null);
    }

    @Override
    public String getDimensionName()
    {
        return "Mining Dimension";
    }

    @Override
    public String getInternalNameSuffix() {
        return "_mindim";
    }

    @Override
    public String getWelcomeMessage() {
        return "Hello";
    }
}
