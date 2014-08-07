package com.fravokados.techmobs.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Facing;

import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.TDTickManager;

public class EntityAIScanArea extends EntityAIBase {
	/** time between two scans */
	public static final int maxTimer = 2400;
	/** if timer is higher entity will scout */
	public static final int minTimer = 1400;
	/** time between two scouts */
	public static final int scanOffset = 200;
	private int timer = 0;
	private EntityLiving e;
	
	public EntityAIScanArea(EntityLiving e) {
		this.e = e;
		setMutexBits(8);
	}

	@Override
	public boolean shouldExecute() {
		return timer == 0;
	}
	
	@Override
	public void startExecuting() {
		super.startExecuting();
		//Debug code
//		e.worldObj.createExplosion(e, e.posX, e.posY, e.posZ, 5.0F, true);
		if(timer == 0) {
//			LogHelper.info("Scanning chunk!");
			TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords( (int) (e.posX), (int) (e.posZ)));
			for(int i = 2; i < 6; i++) {
				TDTickManager.scheduleChunkScan(e.worldObj.getChunkFromBlockCoords( (int) (e.posX + Facing.offsetsXForSide[i] * 16), (int) (e.posZ + Facing.offsetsZForSide[i] * 16)));
			}
			timer = maxTimer;
		}
	}
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	return timer > 0;
    }
	@Override
	public void updateTask() {
		super.updateTask();
		timer--;
		if(timer > minTimer && timer % scanOffset == 0) {
//			LogHelper.info("scouting chunk!");
			TDManager.updateScoutedTechLevel(e.worldObj.getChunkFromBlockCoords( (int) (e.posX), (int) (e.posZ)));
			for(int i = 2; i < 6; i++) {
				TDManager.updateScoutedTechLevel(e.worldObj.getChunkFromBlockCoords( (int) (e.posX + Facing.offsetsXForSide[i] * 16), (int) (e.posZ + Facing.offsetsZForSide[i] * 16)));
			}
		}
	}
	
	@Override
	public void resetTask() {
		super.resetTask();
		timer = 0;
	}

}
