package com.fravokados.dangertech.core.plugin.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Helper energy storage class
 * based on COFH-Redstone Flux API @link{cofh.api.energy.EnergyStorage}
 * modified to use double as energy stored, to support ic2
 */
public class EnergyStorage {

	private int capacity;
	protected double energy;

	public EnergyStorage(int capacity) {
		this.capacity = capacity;
		this.energy = 0;
	}

	/**
	 * Adds energy to the storage. Returns quantity of energy that was accepted.
	 *
	 * @param maxReceive Maximum amount of energy to be inserted.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
	 */
	public double receiveEnergy(double maxReceive, boolean simulate) {
		double energyReceived = Math.min(this.capacity - this.energy, maxReceive);
		return receiveEnergyAll(energyReceived, simulate);
	}

	/**
	 * Adds energy to the storage. Accepts all energy and might exceed maximum capacity.
	 *
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
	 */
	public double receiveEnergyAll(double amount, boolean simulate) {
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
	public double extractEnergy(double maxExtract, boolean simulate) {
		double energyExtracted = Math.min(this.energy, maxExtract);
		if(!simulate) {
			this.energy -= energyExtracted;
		}

		return energyExtracted;
	}

	/**
	 * Returns the amount of energy currently stored.
	 */
	public double getEnergyStored() {
		return this.energy;
	}

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	public int getMaxEnergyStored() {
		return capacity;
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
	public void setEnergyStored(double energy) {
		this.energy = energy;
		if(this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if(this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * same as {@link #setEnergyStored(double)} but converts energy parameter from EU to the given {@link EnergyType}
	 */
	public void setEnergyStored(double energy, EnergyType type) {
		setEnergyStored(energy * type.getConversionFromEU());
	}

	/**
	 * Read energy from the given {@link NBTTagCompound}
	 */
	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getDouble("Energy");
	}

	/**
	 * Save energy contained to the given {@link NBTTagCompound}
	 * @return the given {@link NBTTagCompound} to allow for chaining
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.energy < 0) {
			this.energy = 0;
		}

		nbt.setDouble("Energy", this.energy);
		return nbt;
	}


	/**
	 * @return returns true if this EnergyStorage the given amount of energy is available
	 */
	public boolean canUse(double energy) {
		return energy <= this.energy;
	}

	/**
	 * same as {@link #canUse(double)} but converts energy parameter from EU to the given {@link EnergyType}
	 */
	public boolean canUse(double energy, EnergyType type) {
		return canUse(energy * type.getConversionFromEU());
	}

	/**
	 * removes the given amount of energy if possible
	 * @return true if energy was used (false means there is not enough energy available)
	 * @deprecated use {@link #useEnergy(double, EnergyType)}
	 */
	@Deprecated
	public boolean useEnergy(double energy) {
		if(canUse(energy)) {
			this.energy -= energy;
			return true;
		} else{
			return false;
		}
	}

	public boolean useEnergy(double energy, EnergyType type) {
		return useEnergy(energy * type.getConversionFromEU());
	}

	/**
	 * @return the capacity remaining until this EnergyStorage is full
	 */
	public double getRoomForEnergy() {
		return capacity - energy;
	}

	/**
	 * same as {@link #hasRoomForEnergy(double)} but converts given energy parameter from EU to the given EnergyType
	 */
	public boolean hasRoomForEnergy(double energy, EnergyType type) {
		return hasRoomForEnergy(energy);
	}

	/**
	 * @return returns true if this EnergyStorage has enough space for the given amount of energy
	 */
	public boolean hasRoomForEnergy(double energy) {
		return energy <= getRoomForEnergy();
	}


	/**
	 * @return whether this EnergyStorage is full
	 */
	public boolean isFull() {
		return energy >= capacity;
	}
}
