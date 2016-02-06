package com.fravokados.dangertech.techmobs.entity;

import com.fravokados.dangertech.techmobs.common.EMPExplosion;
import com.fravokados.dangertech.techmobs.entity.ai.EntityAIEMPCreeperSwell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public class EntityEMPCreeper extends EntityMob {

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
	private int explosionRadius = 3;

	public EntityEMPCreeper(World world) {
		super(world);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIEMPCreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity<EntityOcelot>(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (byte) -1); //state (idle or fused)
		this.dataWatcher.addObject(17, (byte) 0); //powered (hit by lightning)
		this.dataWatcher.addObject(18, (byte) 0); //ignited (used for flint and steel and nbt
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
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

		if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
			tag.setBoolean("powered", true);
		}

		tag.setShort("Fuse", (short) this.fuseTime);
		tag.setByte("ExplosionRadius", (byte) this.explosionRadius);
		tag.setBoolean("ignited", this.isIgnited());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		this.dataWatcher.updateObject(17, (byte) (tag.getBoolean("powered") ? 1 : 0));

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
				this.playSound("creeper.primed", 1.0F, 0.5F);
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
	protected String getHurtSound() {
		return "mob.creeper.say";
	}

	@Override
	protected String getDeathSound() {
		return "mob.creeper.death";
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		//FIXME: should emp Creepers drop disks on death?
		if (source.getEntity() instanceof EntitySkeleton) {
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
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
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
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
		return Items.gunpowder;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state) {
		this.dataWatcher.updateObject(16, (byte) state);
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
		super.onStruckByLightning(entityLightningBolt);
		this.dataWatcher.updateObject(17, (byte) 1);
	}

	@Override
	protected boolean interact(EntityPlayer player) {
		//FIXME: should EMPCreeper explode when used with flint and steel?
		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			player.swingItem();

			if (!this.worldObj.isRemote) {
				this.ignite();
				itemstack.damageItem(1, player);
				return true;
			}
		}

		return super.interact(player);
	}

	private void explode() {
		if (!this.worldObj.isRemote) {
			if (this.isPowered()) {
				EMPExplosion.createExplosionWithYOffset(this, 1, explosionRadius * 2);
			} else {
				EMPExplosion.createExplosionWithYOffset(this, 1, explosionRadius);
			}

			this.setDead();
		}
	}

	public boolean isIgnited() {
		return this.dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void ignite() {
		this.dataWatcher.updateObject(18, (byte) 1);
	}
}