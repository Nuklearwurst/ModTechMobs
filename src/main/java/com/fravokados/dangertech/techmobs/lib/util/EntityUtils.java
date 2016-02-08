package com.fravokados.dangertech.techmobs.lib.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class EntityUtils {

	/**
	 * adds a task to the tasklist of the given entity (if there is not a task of the same class already added)<br>
	 * code adapted from CovertJarguar/Railcraft <a href="http://www.railcraft.info/">http://www.railcraft.info/</a>
	 *
	 * @param entity   the entity the task should be added to
	 * @param priority priority {@link EntityAITasks#addTask(int, EntityAIBase)}
	 * @param task     the task to add {@link EntityAITasks#addTask(int, EntityAIBase)}
	 * @return true if the task was added
	 */
	public static boolean addAITask(EntityLiving entity, int priority, EntityAIBase task) {
		for (EntityAITasks.EntityAITaskEntry entry : entity.tasks.taskEntries) {
			if (entry.action.getClass() == task.getClass())
				return false;
		}
		entity.tasks.addTask(priority, task);
		return true;
	}

	public static Entity rayTraceEntity(EntityPlayer player) {
		float reach = 5F;
		Vec3 vecPos = player.getPositionVector();
		Vec3 vecLook = player.getLook(1);
		Vec3 vecEnd = vecPos.addVector(vecLook.xCoord * reach, vecLook.yCoord * reach, vecLook.zCoord * reach);
		Entity pointedEntity = null;
		final float expand = 1.0F;
		List<Entity> list = player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().addCoord(vecLook.xCoord * reach, vecLook.yCoord * reach, vecLook.zCoord * reach).expand((double) expand, (double) expand, (double) expand));
		double lastDistance = reach;

		for (Entity entity : list) {
			float collisionBorderSize = entity.getCollisionBorderSize();
			AxisAlignedBB bounds = entity.getEntityBoundingBox().expand((double) collisionBorderSize, (double) collisionBorderSize, (double) collisionBorderSize);
			MovingObjectPosition movingobjectposition = bounds.calculateIntercept(vecPos, vecEnd);

			if (bounds.isVecInside(vecPos)) {
				if (lastDistance >= 0.0D) {
					pointedEntity = entity;
					lastDistance = 0.0D;
				}
			} else if (movingobjectposition != null) {
				double distanceTo = vecPos.distanceTo(movingobjectposition.hitVec);

				if (distanceTo < lastDistance || lastDistance == 0.0D) {
					if (entity == player.ridingEntity && !entity.canRiderInteract()) {
						if (lastDistance == 0.0D) {
							pointedEntity = entity;
						}
					} else {
						pointedEntity = entity;
						lastDistance = distanceTo;
					}
				}
			}
		}
		return pointedEntity;
	}

}
