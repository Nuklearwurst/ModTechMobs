package com.fravokados.techmobs.entity.ai;

import com.fravokados.techmobs.entity.EntityEMPCreeper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIEMPCreeperSwell extends EntityAIBase {
	/**
	 * The creeper that is swelling.
	 */
	EntityEMPCreeper swellingCreeper;
	/**
	 * The creeper's attack target. This is used for the changing of the creeper's state.
	 */
	EntityLivingBase creeperAttackTarget;

	public EntityAIEMPCreeperSwell(EntityEMPCreeper entity) {
		this.swellingCreeper = entity;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
		return this.swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D;
	}

	@Override
	public void startExecuting() {
		this.swellingCreeper.getNavigator().clearPathEntity();
		this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
	}

	@Override
	public void resetTask() {
		this.creeperAttackTarget = null;
	}

	@Override
	public void updateTask() {
		if (this.creeperAttackTarget == null) {
			this.swellingCreeper.setCreeperState(-1);
		} else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0D) {
			this.swellingCreeper.setCreeperState(-1);
		} else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
			this.swellingCreeper.setCreeperState(-1);
		} else {
			this.swellingCreeper.setCreeperState(1);
		}
	}
}