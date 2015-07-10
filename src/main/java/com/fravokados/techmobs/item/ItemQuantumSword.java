package com.fravokados.techmobs.item;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.techdata.effects.TDEffectHandler;
import com.fravokados.techmobs.world.TechDataStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;

/**
 * @author Nuklearwurst
 */
public class ItemQuantumSword extends ItemTMSword {

	public ItemQuantumSword() {
		super(ToolMaterial.GOLD, Strings.Item.QUANTUM_SWORD);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer origin, Entity target) {
		if (target instanceof EntityLivingBase) {
			int armor = ((EntityLivingBase) target).getTotalArmorValue();
			target.attackEntityFrom(DamageSource.inWall, (float) Math.max(2, armor * (origin.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() / 20)));
			origin.worldObj.playSoundEffect(origin.posX, origin.posY, origin.posZ, "random.eat", 1.0F, origin.getRNG().nextFloat() * 0.1F + 0.9F);
			if (!origin.worldObj.isRemote) {
				int data = TDManager.getPlayerTechLevel(origin);
				if (data > Settings.TechData.SAFE_TECH_VALUE) {
					//randomize effects
					int rand = itemRand.nextInt(data);
					if (rand >= 0.8 * data) {
						//special effects happen
						if (data > TechDataStorage.getInstance().getDangerousPlayerLevel()) { //very nasty
							TDEffectHandler.applyRandomEffectOnPlayer(origin, origin.getCommandSenderName(), itemRand);
							stack.damageItem(20, origin);
							origin.worldObj.createExplosion(null, origin.posX, origin.posY + 1, origin.posZ, 0.4F, false);
						} else if (data > 0.8 * TechDataStorage.getInstance().getDangerousPlayerLevel()) { //normal effects
							TDEffectHandler.applyRandomEffectOnPlayer(origin, origin.getCommandSenderName(), itemRand);
							stack.damageItem(10, origin);
						} else { //simple effects
							origin.worldObj.playSoundEffect(origin.posX, origin.posY, origin.posZ, "mob.endermen.scream", 1.0F, itemRand.nextInt() + 0.5F);
							origin.addChatMessage(new ChatComponentTranslation(Strings.Chat.quantumSword));
							stack.damageItem(4, origin);
						}
					} else if (rand >= 0.5 * data) { //generic message
						stack.damageItem(2, origin);
					}
				} else {
					stack.damageItem(1, origin);
				}
			}
			return true;
		}
		return false;
	}
}
