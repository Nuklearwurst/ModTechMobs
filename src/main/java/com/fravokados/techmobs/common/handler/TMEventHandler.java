package com.fravokados.techmobs.common.handler;

import com.fravokados.techmobs.api.item.IItemAttackTargetListener;
import com.fravokados.techmobs.common.init.ModItems;
import com.fravokados.techmobs.common.SleepingManager;
import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.entity.EntityConservationUnit;
import com.fravokados.techmobs.entity.ai.EntityAIScanArea;
import com.fravokados.techmobs.lib.util.BlockUtils;
import com.fravokados.techmobs.lib.util.EntityUtils;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.effects.TDEffectHandler;
import com.fravokados.techmobs.world.TechDataStorage;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

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
	public void onEntityJoinWorld(EntityJoinWorldEvent evt) {
		if (evt.world.isRemote) {
			return;
		}
		if (Settings.TechScanning.INJECT_SCANNING_AI > 0 && (Settings.TechScanning.INJECT_SCANNING_AI == 1 || evt.world.rand.nextInt(Settings.TechScanning.INJECT_SCANNING_AI) == 0)) {
			if (evt.entity instanceof EntityZombie || evt.entity instanceof EntitySkeleton) {
				EntityLiving e = ((EntityLiving) evt.entity);
				EntityUtils.addAITask(e, 3, new EntityAIScanArea(e));
			}
		}
	}


	@SubscribeEvent
	public void onEntitySetAttackTarget(LivingSetAttackTargetEvent evt) {
		if (!evt.entity.worldObj.isRemote && evt.target instanceof EntityPlayer && (evt.entityLiving instanceof IMob || evt.entityLiving instanceof EntityTameable)) {
			EntityPlayer player = (EntityPlayer) evt.target;
			for (ItemStack stack : player.inventory.mainInventory) {
				if (stack != null && stack.stackSize != 0 && stack.getItem() instanceof IItemAttackTargetListener) {
					((IItemAttackTargetListener) stack.getItem()).onSetAttackTarget(evt, stack);
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttack(LivingAttackEvent evt) {
		if (!evt.entity.worldObj.isRemote && evt.entity instanceof EntityPlayer) {
			if (evt.source.getEntity() instanceof IMob) {
				TDManager.scanAndUpdatePlayerTD((EntityPlayer) evt.entity);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDropsOnDeath(PlayerDropsEvent evt) {
		World world = evt.entityPlayer.getEntityWorld();
		if (!world.isRemote && !world.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			for (EntityItem entityItem : evt.drops) {
				if (entityItem != null && entityItem.getEntityItem()  != null && entityItem.getEntityItem().stackSize != 0 && entityItem.getEntityItem().getItem() == ModItems.conservationUnit) {
					//TODO energy Requirements
					int x = (int) evt.entityPlayer.posX;
					int y = (int) evt.entityPlayer.posY;
					int z = (int) evt.entityPlayer.posZ;
					if (BlockUtils.isBlockReplaceable(world, x, y, z)) {
						while (BlockUtils.isBlockReplaceable(world, x, y - 1, z) && y > 3) {
							y--;
						}
					} else {
						boolean found = false;
						for (int dist = 0; dist < 3; dist++) {
							for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
								if (BlockUtils.isBlockReplaceable(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
									x += dir.offsetX;
									y += dir.offsetY;
									z += dir.offsetZ;
									while (BlockUtils.isBlockReplaceable(world, x, y - 1, z) && y > 3) {
										y--;
									}
									found = true;
									break;
								}
							}
							if (found) {
								break;
							}
						}
						if (!found) {
							break;
						}
					}
					EntityConservationUnit entity = new EntityConservationUnit(world, x, y + 1, z);
					entity.addCapturedDrops(evt.drops);
					world.spawnEntityInWorld(entity);
					evt.setCanceled(true);
					break;
				}
			}
		}
	}
}
