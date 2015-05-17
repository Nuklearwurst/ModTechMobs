package com.fravokados.techmobs.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import com.fravokados.techmobs.ModTechMobs;
import com.fravokados.techmobs.entity.EntityCyberZombie;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.lib.util.LogHelper;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {

	@SuppressWarnings({"unused", "deprecation", "UnusedAssignment"})
	public static void init() {
		//TODO spawns
		//normal biomes
		BiomeGenBase[] forest = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST);
		BiomeGenBase[] hills = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.HILLS);
		BiomeGenBase[] desert = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.DESERT);
		BiomeGenBase[] mountain = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MOUNTAIN);
		BiomeGenBase[] jungle = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.JUNGLE);
		BiomeGenBase[] magic = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MAGICAL);
		BiomeGenBase[] plains = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.PLAINS);
		BiomeGenBase[] swamp = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SWAMP);
		BiomeGenBase[] frozen = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FROZEN);
		BiomeGenBase[] wasteland = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.WASTELAND);
		BiomeGenBase[] beach = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.BEACH);
		//special biomes
		BiomeGenBase[] mushroom = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MUSHROOM);
		BiomeGenBase[] water = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.WATER);
		//other dimensions
		BiomeGenBase[] end = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.END);
		BiomeGenBase[] nether = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);

		/* CyberZombie */
		registerCreature(EntityCyberZombie.class, Strings.Entity.CYBER_ZOMBIE, 0x000000, 0x00000);


		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, forest);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, hills);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, desert);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, mountain);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, jungle);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, magic);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, plains);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, swamp);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, frozen);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, wasteland);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.monster, beach);

	}


	private static void registerCreature(Class<? extends Entity> entity, String name, int back, int fore) {
		int id = getUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entity, name, id, back, fore);
		EntityRegistry.registerModEntity(entity, name, id, ModTechMobs.instance, 80, 3, true);
	}

	private static int getUniqueEntityId() {
		int id = 400;
		try {
			return EntityRegistry.findGlobalUniqueEntityId();
		} catch(Exception e) {
			LogHelper.error("Failed finding Global Entity Id!");
			do {
				id++;
			} while(EntityList.getStringFromID(id) != null);
			return id;
		}

	}


}
