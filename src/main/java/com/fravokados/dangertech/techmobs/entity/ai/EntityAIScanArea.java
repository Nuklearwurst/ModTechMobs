package com.fravokados.dangertech.techmobs.entity.ai;

import com.fravokados.dangertech.techmobs.techdata.TDTickManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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
//			LogHelperTM.logDev("Mob is scanning chunk!");
			scanChunk();
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
			scanChunk();
		}
	}

	private void scanChunk() {
		TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords(new BlockPos((int) (e.posX), (int) e.posY, (int) (e.posZ))));
		for (int i = 2; i < 6; i++) {
			TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords(new BlockPos((int) (e.posX + EnumFacing.getFront(i).getFrontOffsetX() * 16), 0, (int) (e.posZ + EnumFacing.getFront(i).getFrontOffsetX() * 16))));
		}
	}

	@Override
	public void resetTask() {
		super.resetTask();
		timer = 0;
	}

}
