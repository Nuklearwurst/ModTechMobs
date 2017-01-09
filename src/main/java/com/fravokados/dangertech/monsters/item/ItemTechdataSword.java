package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Nuklearwurst
 */
public class ItemTechdataSword extends ItemTMSword {

	private static final String NBT_KEY_TECHDATA = "td_owner";

	public ItemTechdataSword() {
		super(ToolMaterial.DIAMOND, Strings.Item.TECHDATA_SWORD);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if(attacker instanceof EntityPlayer) {
			NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
			int value = TDManager.getPlayerScoutedTechLevel((EntityPlayer) attacker);
			if(value <= 0) {
				nbt.setInteger(NBT_KEY_TECHDATA, 0);
			} else {
				int damage = (int) ((1 - (1 / (float) value)) * 40);
				nbt.setInteger(NBT_KEY_TECHDATA, damage);
			}
		}
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {

		Multimap<String, AttributeModifier> multimap = HashMultimap.create();

		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getDamageVsEntity(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4, 0));
		}

		return multimap;
	}

	private double getDamageVsEntity(ItemStack stack) {
		return ItemUtils.getNBTTagCompound(stack).getInteger(NBT_KEY_TECHDATA);
	}


}
