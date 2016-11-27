package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.api.item.IItemAttackTargetListener;
import com.fravokados.dangertech.core.lib.util.GeneralUtils;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.techdata.TDManager;
import com.fravokados.dangertech.monsters.techdata.effects.TDEffectHandler;
import com.fravokados.dangertech.monsters.world.TechDataStorage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

import javax.annotation.Nullable;

public class ItemMonsterDetector extends ItemTM implements IItemAttackTargetListener {

	//special keys for different warning messages
	private static final String CREEPER = "special_creeper";
	private static final String ZOMBIE_BABY = "special_zombie_baby";

	public ItemMonsterDetector() {
		super(Strings.Item.MONSTER_DETECTOR);
		this.setMaxDamage(100);
	}

	@Override
	public void onSetAttackTarget(LivingSetAttackTargetEvent evt, ItemStack stack) {
		if (evt.getTarget() instanceof EntityPlayer) {
			//noinspection ConstantConditions
			if (evt.getEntityLiving().getLastAttacker() != null && evt.getEntityLiving().getLastAttacker().equals(evt.getTarget()) && evt.getEntityLiving().getLastAttackerTime() < 100) {
				return;
			}
			EntityPlayer player = (EntityPlayer) evt.getTarget();
			int data = TDManager.getPlayerTechLevel(player);
			//safe techvalue --> good information
			if (data <= Settings.TechData.SAFE_TECH_VALUE) {
				sendExactWarningMessage(player, getSpecialEntityName(evt.getEntityLiving()));
			} else {
				//randomize effects
				int rand = itemRand.nextInt(data);
				if (rand >= 0.8 * data) {
					//special effects happen
					if (data > TechDataStorage.getInstance().getDangerousPlayerLevel()) { //very nasty
						TDEffectHandler.applyRandomEffectOnPlayer(player, player.getUniqueID(), itemRand);
						stack.damageItem(20, player);
						player.world.createExplosion(null, player.posX, player.posY + 1, player.posZ, 0.4F, false);
					} else if (data > 0.8 * TechDataStorage.getInstance().getDangerousPlayerLevel()) { //normal effects
						TDEffectHandler.applyRandomEffectOnPlayer(player, player.getUniqueID(), itemRand);
						stack.damageItem(10, player);
					} else { //simple effects
						int i = evt.getEntity().world.rand.nextInt(4);
						switch (i) {
							case 0:
								sendExactWarningMessage(player, CREEPER);
								break;
							case 1:
								sendExactWarningMessage(player, ZOMBIE_BABY);
								break;
							default:
								break;
						}
						stack.damageItem(4, player);
					}
				} else if (rand >= 0.5 * data) { //generic message
					sendGenericWarningMessage(player);
					stack.damageItem(1, player);
				} else { //exact message
					sendExactWarningMessage(player, getSpecialEntityName(evt.getEntityLiving()));
					if (itemRand.nextInt(10) == 0) {
						stack.damageItem(1, player);
					}
				}
			}
		}
	}

	/**
	 * returns the name of the entity or a special identifier for additional chat messages (eg. creeper)
	 */
	private String getSpecialEntityName(EntityLivingBase e) {
		String name = e.getName();
		if (e.world.rand.nextBoolean()) {
			if (e instanceof EntityCreeper) {
				name = CREEPER;
			} else if (e instanceof EntityZombie) {
				if (e.isChild()) {
					name = ZOMBIE_BABY;
				}
			}
		}
		return name;
	}

	public void sendGenericWarningMessage(EntityPlayer player) {
		player.sendMessage(new TextComponentTranslation(Strings.Chat.mobTargetingWarning_generic).setStyle(new Style().setColor(TextFormatting.RED)));
	}

	public void sendExactWarningMessage(EntityPlayer player, @Nullable String name) {
		if (name == null) {
			sendGenericWarningMessage(player);
		} else if (CREEPER.equals(name)) {
			player.sendMessage(new TextComponentTranslation(GeneralUtils.getRandomTranslation(Strings.Chat.mobTargetingWarning_creeper, GeneralUtils.random)));
		} else if (ZOMBIE_BABY.equals(name)) {
			player.sendMessage(new TextComponentTranslation(GeneralUtils.getRandomTranslation(Strings.Chat.mobTargetingWarning_babyZombie, GeneralUtils.random)));
		} else {
			player.sendMessage(new TextComponentTranslation(Strings.Chat.mobTargetingWarning_exact_1)
					.appendSibling(new TextComponentString(" " + name + " ")
							.setStyle(new Style().setColor(TextFormatting.RED)))
					.appendSibling(new TextComponentTranslation(Strings.Chat.mobTargetingWarning_exact_2)));
		}
	}
}
