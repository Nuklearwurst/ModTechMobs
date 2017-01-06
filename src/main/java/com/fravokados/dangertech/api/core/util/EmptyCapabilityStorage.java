package com.fravokados.dangertech.api.core.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Capability storage for capabilities that don't need to store any data
 * @author Nuklearwurst
 */
public class EmptyCapabilityStorage<T> implements Capability.IStorage<T> {

	@Override
	public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {

	}
}
