package com.fravokados.techmobs.entity;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

import com.fravokados.techmobs.entity.ai.EntityAIScanArea;

/**
 * special zombie with special abilities
 * @author Nuklearwurst
 *
 */
public class EntityCyberZombie extends EntityZombie {

	public EntityCyberZombie(World world) {
		super(world);
		this.tasks.addTask(3, new EntityAIScanArea(this));
	}

}
