package com.fravokados.techmobs.common.handler;

import com.fravokados.techmobs.common.SleepingManager;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.fravokados.techmobs.api.item.IItemAttackTargetListener;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.effects.TDEffectHandler;
import com.fravokados.techmobs.world.TechDataStorage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class TMEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load evt) {
		TechDataStorage.onWorldLoad(evt);
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload evt) {
		TechDataStorage.onWorldUnload(evt);
	}
	
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload evt) {
		TechDataStorage.onChunkUnload(evt);
	}
	
	@SubscribeEvent
	public void onChunkDataSave(ChunkDataEvent.Save evt) {
		TechDataStorage.onChunkDataSave(evt);
	}
	
	@SubscribeEvent
	public void onChunkDataLoad(ChunkDataEvent.Load evt) {
		TechDataStorage.onChunkDataLoad(evt);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent evt) {
		TechDataStorage.onPlayerLogin(evt);
    }
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent evt) {
		TechDataStorage.onPlayerLogout(evt);
		//cleanup
		SleepingManager.removePlayer(evt.player);
	}

	@SubscribeEvent
	public void onPlayerWakeUp(PlayerWakeUpEvent evt) {
		SleepingManager.onPlayerWakeUp(evt.entityPlayer);
	}
	
	@SubscribeEvent
	public void onEntitySpawn(LivingSpawnEvent.CheckSpawn evt) {
		TDEffectHandler.onLivingSpawn(evt);
	}
	
	@SubscribeEvent
	public void onEntitySetAttackTarget(LivingSetAttackTargetEvent evt) {
		if(!evt.entity.worldObj.isRemote && evt.target instanceof EntityPlayer && (evt.entityLiving instanceof IMob || evt.entityLiving instanceof EntityTameable)) {
			EntityPlayer player = (EntityPlayer) evt.target;
			for(ItemStack stack : player.inventory.mainInventory) {
				if(stack != null && stack.stackSize != 0 && stack.getItem() instanceof IItemAttackTargetListener) {
					((IItemAttackTargetListener) stack.getItem()).onSetAttackTarget(evt, stack);
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityAttack(LivingAttackEvent evt) {
		if(!evt.entity.worldObj.isRemote && evt.entity instanceof EntityPlayer) {
			if(evt.source.getEntity() instanceof IMob) {
				TDManager.scanAndUpdatePlayerTD((EntityPlayer) evt.entity);
			}
		}
	}
}
