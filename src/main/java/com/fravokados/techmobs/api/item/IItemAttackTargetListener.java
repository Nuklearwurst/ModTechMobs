package com.fravokados.techmobs.api.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public interface IItemAttackTargetListener {

	void onSetAttackTarget(LivingSetAttackTargetEvent evt, ItemStack stack);
}
