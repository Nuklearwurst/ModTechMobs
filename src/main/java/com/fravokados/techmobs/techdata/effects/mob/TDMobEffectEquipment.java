package com.fravokados.techmobs.techdata.effects.mob;

import java.security.InvalidParameterException;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;

public class TDMobEffectEquipment extends TDMobEffect {
	
	protected ItemStack[] equipment;
	protected boolean asSet;
	protected int[] values;
	/**
	 * do not use this
	 */
	@Deprecated
	protected boolean replaceEquipment;
	protected boolean doNotRemoveIfSucceddedBefore = false; //not working
	protected boolean applyAll = true; //not working (settings are always default)
	protected boolean randomStart = true;
	protected float[] dropChances = new float[] {-1, -1, -1, -1, -1};
	
	public TDMobEffectEquipment(ItemStack[] equipment, boolean asSet, boolean replaceEquipment, int[] values) {
		if(equipment.length < 5) {
			throw new InvalidParameterException("The equipment array has to have all 5 equipment pieces! (These can be null)");
		}
		if(values.length < 5) {
			throw new InvalidParameterException("The values array has to have all 5 values! (These can be zero)");
		}
		this.equipment = equipment;
		this.asSet = asSet;
		this.values = values;
		this.replaceEquipment = replaceEquipment;
	}
	
	public TDMobEffectEquipment setDropChance(int slot, float value) {
		dropChances[slot] = value;
		return this;
	}
	
	/**
	 * sets this equipmant effect to either drop at the default rate or not drop at all
	 * @param drop if set to false the chance to drop is set to zero
	 */
	public TDMobEffectEquipment setDoNotDrop(boolean drop) {
		for(int i = 0; i < 5; i++) {
			dropChances[i] = drop ? -1 : 0;
		}		
		return this;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase ent) {
		EntityLiving e = ((EntityLiving)ent);
//		//this has unexpected side effects
//		if(!e.canPickUpLoot()) {
//			//mob can't use Armor??
//			return false;
//		}
		if(!((e instanceof EntityZombie) || (e instanceof EntitySkeleton))) {
			return false;
		}

		if(asSet) {
			if(values[0] + values[1] + values[2] + values[3] + values[4] > techdata) {
				//is to costly
				return false;
			}
		}
		
		if(!replaceEquipment) {
			int counter = 0;
			for(int i = 0; i < 5; i++) {
				if(e.getEquipmentInSlot(i) != null && equipment[i] != null) {
					//slot already full
					if(asSet) {
						return false;
					}
					counter++;
				}
				if(equipment[i] == null || values[i] > techdata) {
					//we don't want to place sth in that slot
					//or it's too costly
					counter++;
				}
			}
			//all slots are full
			if(counter == 5) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int applyEffect(int techdata, EntityLivingBase entityLiving) {
		EntityLiving e = (EntityLiving) entityLiving;
		//everthing should be clear for a set
		if(asSet) {
			for(int i = 0; i < 5; i++) {
				e.setCurrentItemOrArmor(i, equipment[i]);
			}
			return values[0] + values[1] + values[2] + values[3] + values[4];
		}
		int start = e.worldObj.rand.nextInt(5);
		int used = 0;
		for(int i = 0; i < 5; i++) {
			int j = (start + i) % 5;
			if(values[j] > (techdata - used)) {
				continue;
			}
			if(!replaceEquipment && e.getEquipmentInSlot(j) != null) {
				continue;
			}
			e.setCurrentItemOrArmor(j, equipment[j]);
			if(dropChances[j] != -1) {
				e.setEquipmentDropChance(j, dropChances[j]);
			}
			used += values[j];
		}
		return used;
	}

	@Override
	public String toString() {
		return "MobEquipment: " + values;
	}
}
