package com.fravokados.techmobs.entity;

import com.fravokados.techmobs.entity.ai.EntityAIScanArea;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.world.TechDataStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

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

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if(entity instanceof EntityPlayer) {
			int data = TDManager.getPlayerScoutedTechLevel((EntityPlayer) entity);
			double damage = this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
			double modifier = data / TechDataStorage.getInstance().getDangerousPlayerLevel();
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.5 + damage * modifier);
		}
		return super.attackEntityAsMob(entity);
	}
}
