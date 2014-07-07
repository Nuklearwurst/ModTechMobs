package com.fravokados.techmobs.item;

import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public interface IItemAttackTargetListener {

	public void onSetAttackTarget(LivingSetAttackTargetEvent evt);
}
