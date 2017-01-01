package com.fravokados.dangertech.monsters.entity;

import com.fravokados.dangertech.monsters.common.EMPExplosion;
import com.fravokados.dangertech.monsters.entity.ai.EntityAIEMPCreeperSwell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class EntityEMPCreeper extends EntityMob {


	private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntityEMPCreeper.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityEMPCreeper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntityEMPCreeper.class, DataSerializers.BOOLEAN);

	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;
	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;
	/**
	 * Explosion radius for this creeper.
	 */
	private int explosionRadius = 5;

	public EntityEMPCreeper(World world) {
		super(world);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIEMPCreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity<EntityOcelot>(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATE, -1);
		this.dataManager.register(POWERED, false);
		this.dataManager.register(IGNITED, false);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	/*
	@Override
	public int getMaxSafePointTries() {
		return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}
	*/

	@Override
	public void fall(float distance, float multiplier) {
		super.fall(distance, multiplier);
		this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + distance * 1.5F);

		if (this.timeSinceIgnited > this.fuseTime - 5) {
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);

		if (this.dataManager.get(POWERED)) {
			tag.setBoolean("powered", true);
		}

		tag.setShort("Fuse", (short) this.fuseTime);
		tag.setByte("ExplosionRadius", (byte) this.explosionRadius);
		tag.setBoolean("ignited", this.isIgnited());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		this.dataManager.set(POWERED, tag.getBoolean("powered"));

		if (tag.hasKey("Fuse", 99)) {
			this.fuseTime = tag.getShort("Fuse");
		}

		if (tag.hasKey("ExplosionRadius", 99)) {
			this.explosionRadius = tag.getByte("ExplosionRadius");
		}

		if (tag.getBoolean("ignited")) {
			this.ignite();
		}
	}

	@Override
	public void onUpdate() {
		if (this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;

			if (this.isIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

		super.onUpdate();
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		//FIXME: should emp Creepers drop disks on death?
		if (source.getEntity() instanceof EntitySkeleton) {
			int i = Item.getIdFromItem(Items.RECORD_13);
			int j = Item.getIdFromItem(Items.RECORD_WAIT);
			int k = i + this.rand.nextInt(j - i + 1);
			this.dropItem(Item.getItemById(k), 1);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity e) {
		return true;
	}

	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean isPowered() {
		return this.dataManager.get(POWERED);
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float tick) {
		return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * tick) / (float) (this.fuseTime - 2);
	}

	@Override
	protected Item getDropItem() {
		return Items.REDSTONE;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState() {
		return this.dataManager.get(STATE);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state) {
		this.dataManager.set(STATE, state);
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
		super.onStruckByLightning(entityLightningBolt);
		this.dataManager.set(POWERED, true);
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		//FIXME: should EMPCreeper explode when used with flint and steel?
		if (stack != null && stack.getItem() == Items.FLINT_AND_STEEL)
		{
			this.getEntityWorld().playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			player.swingArm(hand);

			if (!this.getEntityWorld().isRemote)
			{
				this.ignite();
				stack.damageItem(1, player);
				return true;
			}
		}

		return super.processInteract(player, hand, stack);
	}

	private void explode() {
		if (!this.getEntityWorld().isRemote) {
			if (this.isPowered()) {
				EMPExplosion.createExplosionWithYOffset(this, 1, explosionRadius * 2);
			} else {
				EMPExplosion.createExplosionWithYOffset(this, 1, explosionRadius);
			}

			this.setDead();
		}
	}

	public boolean isIgnited() {
		return this.dataManager.get(IGNITED);
	}

	public void ignite() {
		this.dataManager.set(IGNITED, true);
	}
}