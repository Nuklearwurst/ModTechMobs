package com.fravokados.dangertech.techmobs.entity;

import com.fravokados.dangertech.techmobs.entity.ai.EntityAIScanArea;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.world.TechDataStorage;
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
		return Items.ROTTEN_FLESH;
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
			double damage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
			double modifier = data / TechDataStorage.getInstance().getDangerousPlayerLevel();
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5 + damage * modifier);
		}
		return super.attackEntityAsMob(entity);
	}
}
