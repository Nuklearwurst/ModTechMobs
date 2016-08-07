package com.fravokados.dangertech.monsters.common.handler;

import com.fravokados.dangertech.api.item.IItemAttackTargetListener;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.monsters.common.SleepingManager;
import com.fravokados.dangertech.monsters.common.init.ModItems;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.entity.EntityConservationUnit;
import com.fravokados.dangertech.monsters.entity.ai.EntityAIScanArea;
import com.fravokados.dangertech.monsters.lib.util.EntityUtils;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffectHandler;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
		SleepingManager.onPlayerWakeUp(evt);
	}

	@SubscribeEvent
	public void onPlayerSetSpawn(PlayerSetSpawnEvent evt) {
		SleepingManager.onPlayerSetSpawn(evt);
	}

	@SubscribeEvent
	public void onEntitySpawn(LivingSpawnEvent.CheckSpawn evt) {
		TDEffectHandler.onLivingSpawn(evt);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent evt) {
		if (evt.getWorld().isRemote) {
			return;
		}
		if (Settings.TechScanning.INJECT_SCANNING_AI > 0 && (Settings.TechScanning.INJECT_SCANNING_AI == 1 || evt.getWorld().rand.nextInt(Settings.TechScanning.INJECT_SCANNING_AI) == 0)) {
			if (evt.getEntity() instanceof EntityZombie || evt.getEntity() instanceof EntitySkeleton) {
				EntityLiving e = ((EntityLiving) evt.getEntity());
				EntityUtils.addAITask(e, 3, new EntityAIScanArea(e));
			}
		}
	}


	@SubscribeEvent
	public void onEntitySetAttackTarget(LivingSetAttackTargetEvent evt) {
		if (!evt.getEntity().worldObj.isRemote && evt.getTarget() instanceof EntityPlayer && (evt.getEntityLiving() instanceof IMob || evt.getEntityLiving() instanceof EntityTameable)) {
			EntityPlayer player = (EntityPlayer) evt.getTarget();
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
		if (!evt.getEntity().worldObj.isRemote && evt.getEntity() instanceof EntityPlayer) {
			if (evt.getSource().getEntity() instanceof IMob) {
				TDManager.scanAndUpdatePlayerTD((EntityPlayer) evt.getEntity());
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDropsOnDeath(PlayerDropsEvent evt) {
		World world = evt.getEntityPlayer().getEntityWorld();
		if (!world.isRemote && !world.getGameRules().getBoolean("keepInventory")) {
			for (EntityItem entityItem : evt.getDrops()) {
				//noinspection ConstantConditions
				if (entityItem != null && entityItem.getEntityItem().getItem() == ModItems.conservationUnit && entityItem.getEntityItem().stackSize != 0) {
					int x = (int) evt.getEntityPlayer().posX;
					int y = (int) evt.getEntityPlayer().posY;
					int z = (int) evt.getEntityPlayer().posZ;
					BlockPos pos = new BlockPos(x, y, z);
					if (BlockUtils.isBlockReplaceable(world, pos)) {
						while (BlockUtils.isBlockReplaceable(world, pos.down()) && y > 3) {
							y--;
							pos = pos.down();
						}
					} else {
						boolean found = false;
						for (int dist = 0; dist < 3; dist++) {
							for (EnumFacing dir : EnumFacing.VALUES) {
								if (BlockUtils.isBlockReplaceable(world, pos.offset(dir))) {
									x += dir.getFrontOffsetX();
									y += dir.getFrontOffsetY();
									z += dir.getFrontOffsetZ();
									pos = pos.offset(dir);
									while (BlockUtils.isBlockReplaceable(world, pos.down()) && y > 3) {
										y--;
										pos = pos.down();
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
					entity.addCapturedDrops(evt.getDrops());
					world.spawnEntityInWorld(entity);
					evt.setCanceled(true);
					break;
				}
			}
		}
	}
}
