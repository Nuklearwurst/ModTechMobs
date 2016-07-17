package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.entity.EntityCyberZombie;
import com.fravokados.dangertech.techmobs.entity.EntityEMPCreeper;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static int INTERNAL_IDS = 0;

	@SuppressWarnings({"unused", "deprecation", "UnusedAssignment"})
	public static void init() {
		//normal biomes
		Biome[] forest = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST);
		Biome[] hills = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.HILLS);
		Biome[] hot = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.HOT);
		Biome[] mountain = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MOUNTAIN);
		Biome[] jungle = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.JUNGLE);
		Biome[] magic = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MAGICAL);
		Biome[] plains = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.PLAINS);
		Biome[] swamp = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SWAMP);
		Biome[] snowy = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SNOWY);
		Biome[] wasteland = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.WASTELAND);
		Biome[] beach = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.BEACH);
		//special biomes
		Biome[] mushroom = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MUSHROOM);
		Biome[] water = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.WATER);
		//other dimensions
		Biome[] end = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.END);
		Biome[] nether = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);

		/* CyberZombie */
		registerCreature(EntityCyberZombie.class, Strings.Entity.CYBER_ZOMBIE, 0x000000, 0x00000);
		/* EMPCreeper */
		registerCreature(EntityEMPCreeper.class, Strings.Entity.EMP_CREEPER, 0x123456, 0x421337);
		registerEntity(EntityConservationUnit.class, Strings.Entity.CON_UNIT);


		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, forest);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, hills);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, hot);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, mountain);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, jungle);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, magic);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, plains);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, swamp);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, snowy);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, wasteland);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, beach);
		EntityRegistry.removeSpawn(EntityCyberZombie.class, EnumCreatureType.MONSTER, nether);
		EntityRegistry.removeSpawn(EntityCyberZombie.class, EnumCreatureType.MONSTER, end);

	}


	private static void registerCreature(Class<? extends Entity> entity, String name, int back, int fore) {
		EntityRegistry.registerModEntity(entity, name, getNextLocalId(), ModTechMobs.instance, 80, 3, true, back, fore);
	}

	private static void registerEntity(Class<? extends Entity> entity, String name) {
		EntityRegistry.registerModEntity(entity, name, getNextLocalId(), ModTechMobs.instance, 80, 3, true);
	}

	private static int getNextLocalId() {
		return INTERNAL_IDS++;
	}


}
