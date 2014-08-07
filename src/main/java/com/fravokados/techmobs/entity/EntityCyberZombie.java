package com.fravokados.techmobs.entity;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
	
	@Override
	protected Item getDropItem() {
		return Items.rotten_flesh;
	}
	
	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		//drop rotten flesh
		super.dropFewItems(recentlyHit, looting);
		//dropping of special loot
		//TODO event? for dropping special loot
	}
	
	

}
