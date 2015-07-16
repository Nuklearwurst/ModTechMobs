package com.fravokados.mindim.block.tileentity.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Helper energy storage class
 * based on COFH-Redstone Flux API @link{cofh.api.energy.EnergyStorage}
 * modified to use double as energy stored, to support ic2
 * @author Nuklearwurst
 */
public class EnergyStorage {


	private double energy;
	private int capacity;

	public EnergyStorage(int capacity) {
		this.energy = 0;
		this.capacity = capacity;
	}


	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getDouble("Energy");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.energy < 0) {
			this.energy = 0;
		}

		nbt.setDouble("Energy", this.energy);
		return nbt;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		if(this.energy > capacity) {
			this.energy = capacity;
		}

	}

	public void setEnergyStored(double energy) {
		this.energy = energy;
		if(this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if(this.energy < 0) {
			this.energy = 0;
		}

	}

	public void modifyEnergyStored(double energy) {
		this.energy += energy;
		if(this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if(this.energy < 0) {
			this.energy = 0;
		}

	}

	public double receiveEnergy(double maxReceive, boolean simulate) {
		double energyReceived = Math.min(this.capacity - this.energy, maxReceive);
		if(!simulate) {
			this.energy += energyReceived;
		}

		return energyReceived;
	}

	public double extractEnergy(double maxExtract, boolean simulate) {
		double energyExtracted = Math.min(this.energy, maxExtract);
		if(!simulate) {
			this.energy -= energyExtracted;
		}

		return energyExtracted;
	}

	public double getEnergyStored() {
		return this.energy;
	}

	public int getMaxEnergyStored() {
		return this.capacity;
	}

	public boolean canUse(double energy) {
		return energy <= this.energy;
	}

	public boolean useEnergy(double energy) {
		if(canUse(energy)) {
			this.energy -= energy;
			return true;
		} else{
			return false;
		}
	}

	public boolean isFull() {
		return getEnergyStored() >= getMaxEnergyStored();
	}
}
