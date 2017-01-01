package com.fravokados.dangertech.monsters.entity;

import com.fravokados.dangertech.api.DangerousTechnologyAPI;
import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.monsters.ModTechMobs;
import com.fravokados.dangertech.monsters.common.EMPExplosion;
import com.fravokados.dangertech.monsters.common.IEmpHandler;
import com.fravokados.dangertech.monsters.common.init.ModItems;
import com.fravokados.dangertech.monsters.lib.GUIIDs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class EntityConservationUnit extends Entity implements IEmpHandler, IInventory {


	private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityConservationUnit.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityConservationUnit.class, DataSerializers.VARINT);

	public static final int LIFE_SPAN = 48000;
	public static final int DYING = 47900;

	public static final int IMMUNITY = 100;

	public final float hoverStart = (float) (Math.random() * Math.PI * 2.0D);

	private String entityName;

	private int timeSinceHit = 0;
	public int age = 0;

	private int empCounter = 0;

	private ArrayList<ItemStack> mainInventory = new ArrayList<ItemStack>();

	private boolean shouldDrop = false;


	public EntityConservationUnit(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(1F, 2.6F);
		this.stepHeight = 1.0F;
	}

	public EntityConservationUnit(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y + getYOffset(), z);
		motionX = motionY = motionZ = 0;
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
	}

	public float getRenderYOffset() {
		return 0.3F;
	}

	public void addCapturedDrops(List<EntityItem> drops) {
		boolean item = false;
		for (EntityItem drop : drops) {
			//noinspection ConstantConditions
			if (!item && drop.getEntityItem().getItem() == ModItems.conservationUnit) {
				item = true;
			} else {
				mainInventory.add(drop.getEntityItem());
			}
		}
	}

	@Override
	public double getYOffset() {
		return 0.5F;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(DAMAGE, 0F);
		this.dataManager.register(AGE, 0);
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return getEntityBoundingBox();
	}

	protected void collideWithNearbyEntities() {
		List<Entity> list = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.2D, -0.01D, 0.2D));

		if (!list.isEmpty()) {
			for (Entity entity : list) {

				if (!this.isRidingSameEntity(entity)) {
					if (!entity.noClip && !this.noClip) {
						if (entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY - 1D) {
							double deltaX = entity.posX - this.posX;
							double deltaZ = entity.posZ - this.posZ;
							double abs_max = MathHelper.absMax(deltaX, deltaZ);

							if (abs_max >= 0.001) {
								abs_max = (double) MathHelper.sqrt(abs_max);
								deltaX /= abs_max;
								deltaZ /= abs_max;
								double multiplier = 1.0D / abs_max;

								if (multiplier > 1.0D) {
									multiplier = 1.0D;
								}

								deltaX *= multiplier;
								deltaZ *= multiplier;
								deltaX *= 0.003;
								deltaZ *= 0.003;
								deltaX *= (1.0F - this.entityCollisionReduction);
								deltaZ *= (1.0F - this.entityCollisionReduction);
								if (Math.abs(motionX - deltaX) > 0.1 && Math.abs(motionX) < Math.abs(motionX - deltaX)) {
									deltaX = 0;
								}
								if (Math.abs(motionZ - deltaZ) > 0.1 && Math.abs(motionZ) < Math.abs(motionZ - deltaZ)) {
									deltaZ = 0;
								}
								if (!this.isBeingRidden()) {
									addVelocity(-deltaX, 0, -deltaZ);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canBePushed() {
		return age > IMMUNITY || getEntityWorld().isRemote;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (!this.getEntityWorld().isRemote && !this.isDead) {
			if (this.isEntityInvulnerable(source)) {
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

							EntityItem entityItem = new EntityItem(getEntityWorld(), posX + dX, posY + dY, posZ + dZ, itemStack.copy());

							if (itemStack.hasTagCompound()) {
								//noinspection ConstantConditions
								entityItem.getEntityItem().setTagCompound(itemStack.getTagCompound().copy());
							}

							float factor = 0.05F;
							entityItem.motionX = rand.nextGaussian() * factor;
							entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
							entityItem.motionZ = rand.nextGaussian() * factor;
							getEntityWorld().spawnEntity(entityItem);
							itemStack.stackSize = 0;
						}
					}
					if (empCounter > 0) {
						EMPExplosion emp = new EMPExplosion(getEntityWorld(), posX, posY + getYOffset(), posZ, empCounter / 10, empCounter / 2);
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


		if (getEntityWorld().isRemote) {
			if(age < dataManager.get(AGE)) {
				age = dataManager.get(AGE);
			}
			if (age >= DYING) {
				for (int k = 0; k < 4; ++k)
				{
					double d2 = this.rand.nextGaussian() * 0.02D;
					double d0 = this.rand.nextGaussian() * 0.02D;
					double d1 = this.rand.nextGaussian() * 0.02D;
					this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1, new int[0]);
				}
			}
		}

		if (age >= LIFE_SPAN) {
			onDeath();
			this.setDead();
		}

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamage() > 0.0F) {
			this.setDamage(this.getDamage() - 1.0F);
		}

		if (empCounter > 0) {
			empCounter--;
		}

		final float drag = 0.94F;

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.move(motionX, motionY, motionZ);
		this.motionX *= drag;
		this.motionY -= 0.04;
		this.motionY *= drag;
		this.motionZ *= drag;

		if (getEntityWorld().isRemote) {
			if (age < IMMUNITY) {
				if (age % 10 == 0) {
					getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, rand.nextDouble() * 5, rand.nextDouble(), rand.nextDouble() * 5);
					getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, rand.nextDouble() * 4, rand.nextDouble(), rand.nextDouble() * 4);
					getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, rand.nextDouble() * 3, rand.nextDouble(), rand.nextDouble() * 3);
					getEntityWorld().spawnParticle(EnumParticleTypes.LAVA, posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
					getEntityWorld().spawnParticle(EnumParticleTypes.LAVA, posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
					getEntityWorld().spawnParticle(EnumParticleTypes.LAVA, posX, posY, posZ, rand.nextDouble() * 10, 0, rand.nextDouble() * 10);
				}
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
		setAge(tag.getInteger("age"));

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

	public void setAge(int age) {
		this.age = age;
		updateAge();
	}

	public void updateAge() {
		this.dataManager.set(AGE, age);
	}

	/**
	 * Gets the current amount of damage the entity has taken.
	 */
	public float getDamage() {
		return this.dataManager.get(DAMAGE);
	}

	/**
	 * Sets the current amount of damage the entity has taken.
	 */
	public void setDamage(float damage) {
		this.dataManager.set(DAMAGE, damage);
	}


	@Override
	public String getName() {
		return this.entityName != null ? this.entityName : super.getName();
	}

	@Override
	public int getSizeInventory() {
		return mainInventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot < mainInventory.size() ? mainInventory.get(slot) : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return ItemUtils.decrStackSize(this, slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, @Nullable ItemStack stack) {
		while (slot >= mainInventory.size()) {
			mainInventory.add(null);
		}
		mainInventory.set(slot, stack);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getEntityName());
	}

	@Override
	public boolean hasCustomName() {
		return this.entityName != null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.getEntityWorld().isRemote) {
			boolean empty = true;
			for (ItemStack item : mainInventory) {
				if (item != null) {
					empty = false;
				}
			}
			if (empty) {
				this.setAge(DYING);
			}
		}
	}

	private void onDeath() {
		playSound(SoundEvents.ENTITY_BLAZE_DEATH, 0.5F, 0.5F);
		if(!getEntityWorld().isRemote) {
			if(shouldDrop) {
				//noinspection ConstantConditions
				InventoryHelper.spawnItemStack(getEntityWorld(), posX, posY + 0.5F, posZ, new ItemStack(ModItems.conservationUnit, 1));
			}
			InventoryHelper.dropInventoryItems(getEntityWorld(), this, this);
		}
	}

	@Override
	public void setDead() {
		super.setDead();
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

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
		this.empCounter = (int) (factor * 20 * strength);
		this.attackEntityFrom(DangerousTechnologyAPI.damageSourceEMP, factor * 20 * strength);
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand) {
		player.openGui(ModTechMobs.instance, GUIIDs.CONSERVATION_UNIT, getEntityWorld(), getEntityId(), 0, 0);
		return true;
	}

	public boolean isShouldDrop() {
		return shouldDrop;
	}

	public void setShouldDrop(boolean shouldDrop) {
		this.shouldDrop = shouldDrop;
	}
}