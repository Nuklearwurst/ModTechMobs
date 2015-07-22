package com.fravokados.dangertech.techmobs.entity.ai;

import com.fravokados.dangertech.techmobs.lib.util.LogHelperTM;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Facing;

public class EntityAIScanArea extends EntityAIBase {
	/**
	 * time between two scans
	 */
	public static final int maxTimer = 2400;
	/**
	 * if timer is higher entity will scout
	 */
	public static final int scoutTimer = 1400;
	/**
	 * time between two scouts
	 */
	public static final int scanOffset = 200;
	private int timer = 0;
	private final EntityLiving e;

	public EntityAIScanArea(EntityLiving e) {
		this.e = e;
		setMutexBits(0);
	}

	@Override
	public boolean shouldExecute() {
		return e.ticksExisted > 100 && timer == 0;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		if (timer == 0) {
			LogHelperTM.logDev("Mob is scanning chunk!");
			TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords((int) (e.posX), (int) (e.posZ)));
			for (int i = 2; i < 6; i++) {
				TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords((int) (e.posX + Facing.offsetsXForSide[i] * 16), (int) (e.posZ + Facing.offsetsZForSide[i] * 16)));
			}
			timer = maxTimer;
		}
	}

	@Override
	public boolean continueExecuting() {
		return timer > 0;
	}

	@Override
	public void updateTask() {
		super.updateTask();
		timer--;
		if (timer > scoutTimer && timer % scanOffset == 0) {
			TDManager.updateScoutedTechLevel(e.worldObj.getChunkFromBlockCoords((int) (e.posX), (int) (e.posZ)));
			for (int i = 2; i < 6; i++) {
				TDManager.updateScoutedTechLevel(e.worldObj.getChunkFromBlockCoords((int) (e.posX + Facing.offsetsXForSide[i] * 16), (int) (e.posZ + Facing.offsetsZForSide[i] * 16)));
			}
		}
	}

	@Override
	public void resetTask() {
		super.resetTask();
		timer = 0;
	}

}
