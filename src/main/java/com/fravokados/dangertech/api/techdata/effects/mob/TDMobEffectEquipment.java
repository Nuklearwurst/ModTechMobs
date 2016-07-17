package com.fravokados.dangertech.api.techdata.effects.mob;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class TDMobEffectEquipment extends TDMobEffect {

	protected ItemStack[] equipment;
	protected boolean asSet;
	protected int[] values;
	protected boolean replaceEquipment;
	protected boolean applyAll = true; //not working (settings are always default)
	protected boolean randomStart = true;
	protected final float[] dropChances = new float[] {-1, -1, -1, -1, -1, -1};

	/**
	 * equipment and values array needs to have exactly 6 values (they can be null / 0)
	 * <p>
	 *     Items are in the following order:
	 *     <ul>
	 *         <li>MainHand</li>
	 *         <li>OffHand</li>
	 *         <li>Feet</li>
	 *         <li>Legs</li>
	 *         <li>Chest</li>
	 *         <li>Head</li>
	 *     </ul>
	 * </p>
	 * @see EntityEquipmentSlot
	 */
	public TDMobEffectEquipment(ItemStack[] equipment, boolean asSet, boolean replaceEquipment, int[] values) {
		if(equipment.length < 6) {
			throw new InvalidParameterException("The equipment array has to have all 6 equipment pieces! (These can be null)");
		}
		if(values.length < 6) {
			throw new InvalidParameterException("The values array has to have all 6 values! (These can be zero)");
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
	 * sets this equipment effect to either drop at the default rate or not drop at all
	 * @param drop if set to false the chance to drop is set to zero
	 */
	public TDMobEffectEquipment setDoesArmorDrop(boolean drop) {
		for(int i = 0; i < 6; i++) {
			dropChances[i] = drop ? -1 : 0;
		}		
		return this;
	}

	@Override
	public boolean isUsable(int techdata, EntityLivingBase ent) {
		EntityLiving e = ((EntityLiving)ent);
		if(!((e instanceof EntityZombie) || (e instanceof EntitySkeleton))) {
			return false;
		}

		if(asSet) {
			if(values[0] + values[1] + values[2] + values[3] + values[4] > techdata) {
				//is to costly
				return false;
			}
		}
		int counter = 0;
		for(int i = 0; i < 5; i++) {
			ItemStack stack = e.getItemStackFromSlot(EntityEquipmentSlot.values()[i]);
			if(stack != null && equipment[i] != null) {
				if(replaceEquipment) {
					if(stack.isItemEqual(equipment[i])) {
						if(asSet) {
							return false;
						}
						counter++;
					}
				} else {
					//slot already full
					if(asSet) {
						return false;
					}
					counter++;					
				}				
			}
			if(equipment[i] == null || values[i] > techdata) {
				//we don't want to place sth in that slot
				//or it's too costly
				counter++;
			}
		}
		//all slots are full
		return counter != 5;

	}

	@Override
	public int applyEffect(int techdata, EntityLivingBase entityLiving) {
		EntityLiving e = (EntityLiving) entityLiving;
		//everthing should be clear for a set
		if(asSet) {
			for(int i = 0; i < 5; i++) {
				e.setItemStackToSlot(EntityEquipmentSlot.values()[i], equipment[i].copy());
			}
			return values[0] + values[1] + values[2] + values[3] + values[4];
		}
		int start = e.worldObj.rand.nextInt(5);
		int used = 0;
		for(int i = 0; i < 5; i++) {
			int j = (start + i) % 5;
			if(equipment[j] == null) {
				continue;
			}
			if(values[j] > (techdata - used)) {
				continue;
			}
			final EntityEquipmentSlot slot = EntityEquipmentSlot.values()[j];
			final ItemStack stack = e.getItemStackFromSlot(slot);
			if((!replaceEquipment && stack != null) || (replaceEquipment && stack != null && stack.isItemEqual(equipment[j]))) {
				continue;
			}
			e.setItemStackToSlot(slot, equipment[j].copy());
			if(dropChances[j] != -1) {
				e.setDropChance(slot, dropChances[j]);
			}
			used += values[j];
		}
		return used;
	}

	/**
	 * checks contents and resets costs for equipment that is not available
	 */
	public TDMobEffectEquipment checkContents() {
		for(int i = 0; i < 5; i++) {
			if(equipment[i] == null) {
				values[i] = 0;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "MobEquipment: " + Arrays.toString(values);
	}
}
