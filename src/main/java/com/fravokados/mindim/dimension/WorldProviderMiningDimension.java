package com.fravokados.mindim.dimension;

import com.fravokados.mindim.configuration.Settings;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

/**
 * @author Nuklearwurst
 */
public class WorldProviderMiningDimension extends WorldProvider {

    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0.8F);
        this.dimensionId = Settings.dimensionId;

    }

    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderGenerate(worldObj, worldObj.getSeed() + 42, true);
    }

    public String getDimensionName()
    {
        return "Mining Dimension";
    }

    @Override
    public String getWelcomeMessage() {
        return "Hello";
    }
}
