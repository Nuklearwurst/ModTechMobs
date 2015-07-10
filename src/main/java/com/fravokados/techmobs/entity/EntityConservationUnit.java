package com.fravokados.techmobs.entity;

import com.fravokados.techmobs.api.DangerousTechnologyAPI;
import com.fravokados.techmobs.common.EMPExplosion;
import com.fravokados.techmobs.common.IEmpHandler;
import com.fravokados.techmobs.common.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class EntityConservationUnit extends Entity implements IEmpHandler {

	private static final int DATA_WATCHER_DAMAGE = 17;
	public final float hoverStart = (float) (Math.random() * Math.PI * 2.0D);

	private String entityName;

	private int timeSinceHit = 0;
	public int age = 0;

	private int empCounter = 0;

	private ArrayList<ItemStack> mainInventory = new ArrayList<ItemStack>();

	public static final int LIFE_SPAN = 48000;
	public static final int IMMUNITY = 100;

	public EntityConservationUnit(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(1F, 3.0F);
		this.yOffset = 0.5F;
		this.stepHeight = 1.0F;
	}

	public EntityConservationUnit(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y + yOffset, z);
		motionX = motionY = motionZ = 0;
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
	}

	public void addCapturedDrops(List<EntityItem> drops) {
		boolean item = false;
		for (EntityItem drop : drops) {
			if (!item && drop.getEntityItem().getItem() == ModItems.conservationUnit) {
				item = true;
			} else {
				mainInventory.add(drop.getEntityItem());
			}
		}
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public float getShadowSize() {
		return 0.5F;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, (float) 0);
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.canBePushed() ? entity.boundingBox : null;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	protected void collideWithNearbyEntities() {
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

		if (list != null && !list.isEmpty()) {
			for (Object aList : list) {
				Entity entity = (Entity) aList;

				if (entity.canBePushed()) {
					if (entity.riddenByEntity != this && entity.ridingEntity != this) {
						double deltaX = entity.posX - this.posX;
						double deltaZ = entity.posZ - this.posZ;
						double abs_max = MathHelper.abs_max(deltaX, deltaZ);

						if (abs_max >= 0.001) {
							abs_max = (double) MathHelper.sqrt_double(abs_max);
							deltaX /= abs_max;
							deltaZ /= abs_max;
							double multiplier = 1.0D / abs_max;

							if (multiplier > 1.0D) {
								multiplier = 1.0D;
							}

							deltaX *= multiplier;
							deltaZ *= multiplier;
							deltaX *= 0.0001;
							deltaZ *= 0.0001;
							if (Math.abs(motionX - deltaX) > 0.1 && Math.abs(motionX) < Math.abs(motionX - deltaX)) {
								deltaX = 0;
							}
							if (Math.abs(motionZ - deltaZ) > 0.1 && Math.abs(motionZ) < Math.abs(motionZ - deltaZ)) {
								deltaZ = 0;
							}
							addVelocity(-deltaX, 0, -deltaZ);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canBePushed() {
		return age > IMMUNITY;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (!this.worldObj.isRemote && !this.isDead) {
			if (this.isEntityInvulnerable()) {
				return false;
			} else {
				this.setTimeSinceHit(10);
				this.setDamage(this.getDamage() + damage);
				this.setBeenAttacked();
				boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if (flag || this.getDamage() > 40.0F) {
					//drop items
					for (ItemStack itemStack : mainInventory) {
						if (itemStack != null && itemStack.stackSize > 0) {
							float dX = rand.nextFloat() * 0.8F + 0.1F;
							float dY = rand.nextFloat() * 0.8F + 0.1F;
							float dZ = rand.nextFloat() * 0.8F + 0.1F;

							EntityItem entityItem = new EntityItem(worldObj, posX + dX, posY + dY, posZ + dZ, itemStack.copy());

							if (itemStack.hasTagCompound()) {
								entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
							}

							float factor = 0.05F;
							entityItem.motionX = rand.nextGaussian() * factor;
							entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
							entityItem.motionZ = rand.nextGaussian() * factor;
							worldObj.spawnEntityInWorld(entityItem);
							itemStack.stackSize = 0;
						}
					}
					if(empCounter > 0) {
						EMPExplosion emp = new EMPExplosion(worldObj, posX, posY + yOffset, posZ, empCounter / 10, empCounter / 2);
						emp.doExplosionWithEffects();
					}
					this.setDead();
				}
			}
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}


	@Override
	public void onUpdate() {
		super.onUpdate();

		if (age >= LIFE_SPAN) {
			this.setDead();
		}

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamage() > 0.0F) {
			this.setDamage(this.getDamage() - 1.0F);
		}

		if(empCounter > 0) {
			empCounter--;
		}

		final float drag = 0.94F;

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.moveEntity(motionX, motionY, motionZ);
		this.motionX *= drag;
		this.motionY -= 0.04;
		this.motionY *= drag;
		this.motionZ *= drag;

		if (age < IMMUNITY) {
			if (age % 10 == 0) {
				worldObj.spawnParticle("reddust", posX, posY, posZ, rand.nextDouble() * 5, rand.nextDouble(), rand.nextDouble() * 5);
				worldObj.spawnParticle("reddust", posX, posY, posZ, rand.nextDouble() * 4, rand.nextDouble(), rand.nextDouble() * 4);
				worldObj.spawnParticle("reddust", posX, posY, posZ, rand.nextDouble() * 3, rand.nextDouble(), rand.nextDouble() * 3);
				worldObj.spawnParticle("lava", posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
				worldObj.spawnParticle("lava", posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
				worldObj.spawnParticle("lava", posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
			}
		}
		collideWithNearbyEntities();
		age++;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		if (tag.hasKey("CustomName", 8) && tag.getString("CustomName").length() > 0) {
			this.entityName = tag.getString("CustomName");
		}
		age = tag.getInteger("age");
		setDamage(tag.getFloat("damage"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		if (this.entityName != null && this.entityName.length() > 0) {
			tag.setString("CustomName", this.entityName);
		}
		tag.setInteger("age", age);
		tag.setFloat("damage", getDamage());
	}

	/**
	 * Gets the current amount of damage the entity has taken.
	 */
	public float getDamage() {
		return this.dataWatcher.getWatchableObjectFloat(DATA_WATCHER_DAMAGE);
	}

	/**
	 * Sets the current amount of damage the entity has taken.
	 */
	public void setDamage(float damage) {
		this.dataWatcher.updateObject(DATA_WATCHER_DAMAGE, damage);
	}


	public String getCommandSenderName() {
		return this.entityName != null ? this.entityName : super.getCommandSenderName();
	}

	public boolean hasCustomInventoryName() {
		return this.entityName != null;
	}

	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * Sets the entity's name.
	 */
	public void setEntityName(String name) {
		this.entityName = name;
	}

	public void setTimeSinceHit(int timeSinceHit) {
		this.timeSinceHit = timeSinceHit;
	}

	public int getTimeSinceHit() {
		return timeSinceHit;
	}

	@Override
	public void handleEMP(World world, double x, double y, double z, float strength, int radius, float factor) {
		this.empCounter = (int) (factor  * 20 * strength);
		this.attackEntityFrom(DangerousTechnologyAPI.damageSourceEMP, (float) (factor * 20 * strength));
	}
}