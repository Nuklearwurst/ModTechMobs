package com.fravokados.techmobs.techdata.effects;

import java.security.InvalidParameterException;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;

public class TDMobEffectArmor extends TDMobEffect {
	
	protected ItemStack[] armor;
	protected boolean asSet;
	protected int[] values;
	protected boolean removeArmor;
	protected boolean doNotRemoveIfSucceddedBefore = false; //not working
	protected boolean applyAll = true; //not working (settings are always default)
	protected boolean randomStart = true;
	
	public TDMobEffectArmor(ItemStack[] armor, boolean asSet, boolean removeArmor, int[] values) {
		if(armor.length < 4) {
			throw new InvalidParameterException("The armor array has to have all 4 armor pieces! (These can be null)");
		}
		if(values.length < 4) {
			throw new InvalidParameterException("The values array has to have all 4 values! (These can be zero)");
		}
		this.armor = armor;
		this.asSet = asSet;
		this.values = values;
		this.removeArmor = removeArmor;
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
			if(values[0] + values[1] + values[2] + values[3] > techdata) {
				//is to costly
				return false;
			}
		}
		
		if(!removeArmor) {
			int counter = 0;
			for(int i = 0; i < 4; i++) {
				if(e.getEquipmentInSlot(i + 1) != null && armor[i] != null) {
					//slot already full
					if(asSet) {
						return false;
					}
					counter++;
				}
				if(armor[i] == null || values[i] > techdata) {
					//we don't want to place sth in that slot
					//or it's too costly
					counter++;
				}
			}
			//all slots are full
			if(counter == 4) {
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
			for(int i = 0; i < 4; i++) {
				e.setCurrentItemOrArmor(i + 1, armor[i]);
			}
			return values[0] + values[1] + values[2] + values[3];
		}
		int start = e.worldObj.rand.nextInt(4);
		int used = 0;
		for(int i = 0; i < 4; i++) {
			int j = (start + i) % 4;
			if(values[j] > (techdata - used)) {
				continue;
			}
			if(!removeArmor && e.getEquipmentInSlot(j + 1) != null) {
				continue;
			}
			e.setCurrentItemOrArmor(j + 1, armor[j]);
			used += values[j];
		}
		return used;
	}

}
