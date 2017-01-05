package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffectHandler;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

/**
 * @author Nuklearwurst
 */
public class ItemQuantumSword extends ItemTMSword {

	public ItemQuantumSword() {
		super(ToolMaterial.GOLD, Strings.Item.QUANTUM_SWORD);
	}

	@Override
	public float getDamageVsEntity() {
		return 1;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer origin, Entity target) {
		if (target.canBeAttackedWithItem()) {
			if (!target.hitByEntity(origin)) {
				if (target instanceof EntityLivingBase) {
					float attackDamage = (float) origin.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
					float attackStrength = origin.getCooledAttackStrength(0.5F);
					attackDamage *= (0.2F + attackStrength * attackStrength * 0.8F);

					if(attackDamage > 0) {

						int armor = ((EntityLivingBase) target).getTotalArmorValue();
						float oldHealth = ((EntityLivingBase)target).getHealth();

						target.attackEntityFrom(DamageSource.causePlayerDamage(origin).setDamageBypassesArmor(), (float) Math.max(2, armor * (origin.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 20)));
						origin.getEntityWorld().playSound(null, origin.posX, origin.posY, origin.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, origin.getRNG().nextFloat() * 0.1F + 0.9F);
						if (!origin.getEntityWorld().isRemote) {

							float diff = oldHealth - ((EntityLivingBase)target).getHealth();
							origin.addStat(StatList.DAMAGE_DEALT, Math.round(diff * 10.0F));
							if (origin.getEntityWorld() instanceof WorldServer && diff > 2.0F)
							{
								int k = (int)((double)diff * 0.5D);
								((WorldServer)origin.getEntityWorld()).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, target.posX, target.posY + (double)(target.height * 0.5F), target.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
							}

							int data = TDManager.getPlayerTechLevel(origin);
							if (data > Settings.TechData.SAFE_TECH_VALUE) {
								//randomize effects
								int rand = itemRand.nextInt(10);
								if (rand >= 8) {
									//special effects happen
									if (data > TechDataStorage.getInstance().getDangerousPlayerLevel()) { //very nasty
										TDEffectHandler.applyRandomEffectOnPlayer(origin, origin.getUniqueID(), itemRand);
										stack.damageItem(20, origin);
										origin.getEntityWorld().createExplosion(null, origin.posX, origin.posY + 1, origin.posZ, 0.4F, false);
									} else if (data > 0.8 * TechDataStorage.getInstance().getDangerousPlayerLevel()) { //normal effects
										TDEffectHandler.applyRandomEffectOnPlayer(origin, origin.getUniqueID(), itemRand);
										stack.damageItem(10, origin);
									} else { //simple effects
										origin.getEntityWorld().playSound(null, origin.posX, origin.posY, origin.posZ, SoundEvents.ENTITY_ENDERMEN_SCREAM, SoundCategory.PLAYERS, 1.0F, itemRand.nextInt() + 0.5F);
										origin.sendMessage(new TextComponentTranslation(Strings.Chat.quantumSword));
										stack.damageItem(4, origin);
									}
								} else if (rand >= 5) { //generic message
									stack.damageItem(2, origin);
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
