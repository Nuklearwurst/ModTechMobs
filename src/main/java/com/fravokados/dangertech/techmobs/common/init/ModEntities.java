package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.entity.EntityCyberZombie;
import com.fravokados.dangertech.techmobs.entity.EntityEMPCreeper;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static int INTERNAL_IDS = 0;

	@SuppressWarnings({"unused", "deprecation", "UnusedAssignment"})
	public static void init() {
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
		/* EMPCreeper */
		registerCreature(EntityEMPCreeper.class, Strings.Entity.EMP_CREEPER, 0x123456, 0x421337);
		registerEntity(EntityConservationUnit.class, Strings.Entity.CON_UNIT);


		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, forest);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, hills);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, desert);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, mountain);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, jungle);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, magic);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, plains);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, swamp);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, frozen);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, wasteland);
		EntityRegistry.addSpawn(EntityCyberZombie.class, 10, 1, 5, EnumCreatureType.MONSTER, beach);
		EntityRegistry.removeSpawn(EntityCyberZombie.class, EnumCreatureType.MONSTER, nether);
		EntityRegistry.removeSpawn(EntityCyberZombie.class, EnumCreatureType.MONSTER, end);

	}


	private static void registerCreature(Class<? extends Entity> entity, String name, int back, int fore) {
		int id = getUniqueGlobalEntityId();
		EntityRegistry.registerGlobalEntityID(entity, name, id, back, fore);
		EntityRegistry.registerModEntity(entity, name, getNextLocalId(), ModTechMobs.instance, 80, 3, true);
	}

	private static void registerEntity(Class<? extends Entity> entity, String name) {
		EntityRegistry.registerModEntity(entity, name, getNextLocalId(), ModTechMobs.instance, 80, 3, true);
	}

	private static int getUniqueGlobalEntityId() {
		int id = 400;
		try {
			return EntityRegistry.findGlobalUniqueEntityId();
		} catch(Exception e) {
			LogHelperTM.error("Failed finding Global Entity Id!");
			do {
				id++;
			} while(EntityList.getStringFromID(id) != null);
			return id;
		}
	}

	private static int getNextLocalId() {
		return INTERNAL_IDS++;
	}


}
