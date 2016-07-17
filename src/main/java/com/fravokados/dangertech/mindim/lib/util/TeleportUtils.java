package com.fravokados.dangertech.mindim.lib.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

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
		entityPlayerMP.connection.sendPacket(new SPacketRespawn(entityPlayerMP.dimension, worldServerDestination.getDifficulty(), worldServerDestination.getWorldInfo().getTerrainType(), entityPlayerMP.interactionManager.getGameType())); // Forge: Use new dimensions information
		//remove player from origin world
		worldServerOrigin.removeEntityDangerously(entityPlayerMP);
		entityPlayerMP.isDead = false;
		//transfer entity to new world
		transferEntityToWorld(entityPlayerMP, worldServerOrigin, worldServerDestination);
		server.getPlayerList().preparePlayer(entityPlayerMP, worldServerOrigin);
		//update player location
		entityPlayerMP.connection.setPlayerLocation(x, y, z, yaw, pitch);
		//update player world
		entityPlayerMP.interactionManager.setWorld(worldServerDestination);
		//update player data
		server.getPlayerList().updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerDestination);
		server.getPlayerList().syncPlayerInventory(entityPlayerMP);
		//update potion effects
		for (PotionEffect potioneffect : entityPlayerMP.getActivePotionEffects()) {
			entityPlayerMP.connection.sendPacket(new SPacketEntityEffect(entityPlayerMP.getEntityId(), potioneffect));
		}
		//update Experience level
		entityPlayerMP.connection.sendPacket(new SPacketSetExperience(entityPlayerMP.experience, entityPlayerMP.experienceTotal, entityPlayerMP.experienceLevel));
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
			if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(entity, targetDimension)) return;

			MinecraftServer minecraftserver = entity.getServer();

			if(minecraftserver == null) {
				LogHelperMD.error("Minecraft Server is null!! This is a bug!!");
				return;
			}

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
				//Copy Data
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				entity.writeToNBT(nbttagcompound);
				nbttagcompound.removeTag("Dimension");
				newEntity.readFromNBT(nbttagcompound);

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
