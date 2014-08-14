package com.fravokados.techmobs.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.techdata.TDManager;
import com.fravokados.techmobs.world.TechDataStorage;

public class ItemMonsterDetector extends ItemTM implements IItemAttackTargetListener {

	//special keys for different warning messages
	private static final String CREEPER = "special_creeper";
	private static final String ZOMBIE_BABY = "special_zombie_baby";

	public ItemMonsterDetector() {
		super();
		this.setUnlocalizedName(Strings.Item.MONSTER_DETECTOR);
	} 

	@Override
	public void onSetAttackTarget(LivingSetAttackTargetEvent evt) {
		if(evt.target instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.target;
			int data = TDManager.getPlayerTechLevel(player.getCommandSenderName());
			//safe techvalue --> good information
			if(data <= Settings.TechData.SAFE_TECH_VALUE) {
				sendExactWarningMessage(player, getSpecialEntityName(evt.entityLiving));			
			} else {
				//randomize effects
				int rand = evt.entity.worldObj.rand.nextInt(data);
				if(rand >= 0.8 * data) {
					//special effects happen
					if(data > TechDataStorage.getDangerousPlayerLevel()) { //very nasty
						//TODO evil stuff
						player.addChatMessage(new ChatComponentText("Keep Calm!!!"));
					} else if(data > 0.8 * TechDataStorage.getDangerousPlayerLevel()) { //normal effects
						player.addChatMessage(new ChatComponentText("Hey"));
						//TODO special effects
					} else { //simple effects
						int i = evt.entity.worldObj.rand.nextInt(4);
						switch(i) {
						case 0:
							sendExactWarningMessage(player, CREEPER);
							break;
						case 1:
							sendExactWarningMessage(player, ZOMBIE_BABY);
							break;
						default:
							break;
						}
					}
				} else if(rand >= 0.5 * data) { //generic message
					sendGenericWarningMessage(player);						
				} else { //exact message
					sendExactWarningMessage(player, getSpecialEntityName(evt.entityLiving));
				}
			}
		}
	}
	
	/**
	 * returns the name of the entity or a special identifier for additional chat messages (eg. creeper)
	 * @param e
	 * @return
	 */
	private  String getSpecialEntityName(EntityLivingBase e) {
		String name = e.getCommandSenderName();
		if(e.worldObj.rand.nextBoolean()) {
			if(e instanceof EntityCreeper) {
				name = CREEPER;
			} else if( e instanceof EntityZombie) {
				if(e.isChild()) {
					name = ZOMBIE_BABY;
				}
			}
		}
		return name;
	}

	public void sendGenericWarningMessage(EntityPlayer player) {
		player.addChatMessage(new ChatComponentTranslation(Strings.Chat.mobTargetingWarning_generic).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
	}

	public void sendExactWarningMessage(EntityPlayer player, String name) {
		if(name == null) {
			sendGenericWarningMessage(player);
		} else if(CREEPER.equals(name)) {
			player.addChatMessage(new ChatComponentTranslation(Strings.Chat.mobTargetingWarning_creeper_1));
		} else if(ZOMBIE_BABY.equals(name)) {
			player.addChatMessage(new ChatComponentTranslation(Strings.Chat.mobTargetingWarning_babyZombie_1));
		} else {
			player.addChatMessage(new ChatComponentTranslation(Strings.Chat.mobTargetingWarning_exact_1)
			.appendSibling(new ChatComponentText(" " + name + " ")
			.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
			.appendSibling(new ChatComponentTranslation(Strings.Chat.mobTargetingWarning_exact_2)));
		}
	}
}