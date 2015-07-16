package com.fravokados.mindim.util;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.Collection;

/**
 * @author Nuklearwurst
 */
public class TeleportUtils {

	public static void transferPlayerToDimension(EntityPlayerMP entityPlayerMP, int dimension)
	{
		transferPlayerToDimension(entityPlayerMP, dimension, entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
	}

	@SuppressWarnings(value = "unchecked")
	public static void transferPlayerToDimension(EntityPlayerMP entityPlayerMP, int targetDimension, double x, double y, double z, float yaw, float pitch)
	{
		if(entityPlayerMP.worldObj.isRemote) {return;}

		//minecraft server instance
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		//origin
		int originDimension = entityPlayerMP.dimension;
		WorldServer worldServerOrigin = server.worldServerForDimension(entityPlayerMP.dimension);
		//target dimenion
		entityPlayerMP.dimension = targetDimension;
		WorldServer worldServerDestination = server.worldServerForDimension(entityPlayerMP.dimension);
		//respawn player
		entityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP.dimension, worldServerDestination.difficultySetting, worldServerDestination.getWorldInfo().getTerrainType(), entityPlayerMP.theItemInWorldManager.getGameType())); // Forge: Use new dimensions information
		//remove player from origin world
		worldServerOrigin.removePlayerEntityDangerously(entityPlayerMP);
		entityPlayerMP.isDead = false;
		//transfer entity to new world
		transferEntityToWorld(entityPlayerMP, worldServerOrigin, worldServerDestination);
		server.getConfigurationManager().func_72375_a(entityPlayerMP, worldServerOrigin);
		//update player location
		entityPlayerMP.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
		//update player world
		entityPlayerMP.theItemInWorldManager.setWorld(worldServerDestination);
		//update player data
		server.getConfigurationManager().updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerDestination);
		server.getConfigurationManager().syncPlayerInventory(entityPlayerMP);
		//update potion effects

		for (PotionEffect potioneffect : (Collection<PotionEffect>)entityPlayerMP.getActivePotionEffects()) {
			entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), potioneffect));
		}
		//fire event
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(entityPlayerMP, originDimension, targetDimension);
	}

	public static void transferEntityToDimension(Entity entity, int targetDimension) {
		transferEntityToDimension(entity, targetDimension, entity.posX, entity.posY, entity.posZ, entity.rotationYaw);
	}

	public static void transferEntityToDimension(Entity entity, int targetDimension, double posX, double posY, double posZ, float rotationYaw)
	{
		if (!entity.worldObj.isRemote && !entity.isDead)
		{
			MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();

			int originDimension = entity.dimension;

			WorldServer worldServerOrigin = minecraftserver.worldServerForDimension(originDimension);
			WorldServer worldServerDestination = minecraftserver.worldServerForDimension(targetDimension);

			entity.dimension = targetDimension;

			entity.worldObj.removeEntity(entity);
			entity.isDead = false;

			transferEntityToWorld(entity, worldServerOrigin, worldServerDestination, posX, posY, posZ, rotationYaw);

			Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), worldServerDestination);

			if (newEntity != null)
			{
				newEntity.copyDataFrom(entity, true);
				worldServerDestination.spawnEntityInWorld(newEntity);
			}

			entity.isDead = true;

			worldServerOrigin.resetUpdateEntityTick();
			worldServerDestination.resetUpdateEntityTick();
		}
	}

	public static void transferEntityToWorld(Entity entity, WorldServer worldServerOrigin, WorldServer worldServerDestination)
	{
		transferEntityToWorld(entity, worldServerOrigin, worldServerDestination, entity.posX, entity.posY, entity.posZ, entity.rotationYaw);
	}

	public static void transferEntityToWorld(Entity entity, WorldServer worldServerOrigin, WorldServer worldServerDestination, double posX, double posY, double posZ, float rotationYaw)
	{
		if (entity.isEntityAlive())
		{
			entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, entity.rotationPitch);
			worldServerDestination.spawnEntityInWorld(entity);
			worldServerDestination.updateEntityWithOptionalForce(entity, false);
		}

		entity.setWorld(worldServerDestination);
	}

}
