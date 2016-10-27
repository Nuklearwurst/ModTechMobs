package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Helper energy storage class
 * based on COFH-Redstone Flux API @link{cofh.api.energy.EnergyStorage}
 */
public class EnergyStorage implements IEnergyStorage {

	private int capacity;
	protected int energy;

	private boolean allowExtract;
	private boolean allowInsert;

	public EnergyStorage(int capacity, boolean allowExtract, boolean allowInsert) {
		this.capacity = capacity;
		this.allowExtract = allowExtract;
		this.allowInsert = allowInsert;
		this.energy = 0;
	}

	/**
	 * Adds energy to the storage. Returns quantity of energy that was accepted.
	 *
	 * @param maxReceive Maximum amount of energy to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
	 */
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(this.capacity - this.energy, maxReceive);
		return receiveEnergyAll(energyReceived, simulate);
	}

	/**
	 * Adds energy to the storage. Accepts all energy and might exceed maximum capacity.
	 *
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
	 */
	public int receiveEnergyAll(int amount, boolean simulate) {
		if(!simulate) {
			this.energy += amount;
		}
		return amount;
	}

	/**
	 * Removes energy from the storage. Returns quantity of energy that was removed.
	 *
	 * @param maxExtract Maximum amount of energy to be extracted.
	 * @param simulate  If TRUE, the extraction will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
	 */
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(this.energy, maxExtract);
		if(!simulate) {
			this.energy -= energyExtracted;
		}

		return energyExtracted;
	}

	/**
	 * Returns the amount of energy currently stored.
	 */
	@Override
	public int getEnergyStored() {
		return this.energy;
	}

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return allowExtract;
	}

	@Override
	public boolean canReceive() {
		return allowInsert;
	}

	public void setAllowExtract(boolean allowExtract) {
		this.allowExtract = allowExtract;
	}

	public void setAllowInsert(boolean allowInsert) {
		this.allowInsert = allowInsert;
	}

	/**
	 * sets the new capacity (if EnergyStorage contains more energy than the new capacity excess energy gets lost)
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		if(this.energy > capacity) this.energy = capacity;
	}

	/**
	 * same as {@link #setCapacity(int)} but converts energy parameter from EU to the given {@link EnergyType}
	 */
	public void setCapacity(int capacity, EnergyType type) {
		setCapacity((int) (capacity * type.getConversionFromEU()));
	}

	/**
	 * sets the energy stored
	 * <p>
	 *     this will ensure that the stored energy does not exceed the maximum capacity
	 * </p>
	 */
	public void setEnergyStored(int energy) {
		this.energy = energy;
		if(this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if(this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * same as {@link #setEnergyStored(int)} but converts energy parameter from EU to the given {@link EnergyType}
	 */
	public void setEnergyStored(int energy, EnergyType type) {
		setEnergyStored((int) (energy * type.getConversionFromEU()));
	}

	/**
	 * Read energy from the given {@link NBTTagCompound}
	 */
	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("Energy");
	}

	/**
	 * Save energy contained to the given {@link NBTTagCompound}
	 * @return the given {@link NBTTagCompound} to allow for chaining
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.energy < 0) {
			this.energy = 0;
		}

		nbt.setInteger("Energy", this.energy);
		return nbt;
	}


	/**
	 * @return returns true if this EnergyStorage the given amount of energy is available
	 */
	public boolean canUse(int energy) {
		return energy <= this.energy;
	}

	/**
	 * same as {@link #canUse(int)} but converts energy parameter from EU to the given {@link EnergyType}
	 */
	public boolean canUse(int energy, EnergyType type) {
		return canUse((int) (energy * type.getConversionFromEU()));
	}

	private boolean useEnergyDo(int energy) {
		if(canUse(energy)) {
			this.energy -= energy;
			return true;
		} else{
			return false;
		}
	}

	/**
	 * removes the given amount of energy if possible
	 * @return true if energy was used (false means there is not enough energy available)
	 */
	public boolean useEnergy(int energy, EnergyType type) {
		return useEnergyDo((int) (energy * type.getConversionFromEU()));
	}

	/**
	 * @return the capacity remaining until this EnergyStorage is full
	 */
	public int getRoomForEnergy() {
		return capacity - energy;
	}

	/**
	 * same as {@link #hasRoomForEnergy(int)} but converts given energy parameter from EU to the given EnergyType
	 */
	public boolean hasRoomForEnergy(int energy, EnergyType type) {
		return hasRoomForEnergy(energy);
	}

	/**
	 * @return returns true if this EnergyStorage has enough space for the given amount of energy
	 */
	public boolean hasRoomForEnergy(int energy) {
		return energy <= getRoomForEnergy();
	}


	/**
	 * @return whether this EnergyStorage is full
	 */
	public boolean isFull() {
		return energy >= capacity;
	}
}
