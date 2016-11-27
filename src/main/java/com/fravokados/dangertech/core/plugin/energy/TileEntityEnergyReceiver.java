package com.fravokados.dangertech.core.plugin.energy;

import cofh.api.energy.IEnergyReceiver;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2EnergyPlugin;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

/**
 * Base {@link TileEntity} for machines that accept energy
 * <p>
 * This will handle all inworld interaction with energy-apis and is {@link IEnergyTypeAware}
 * </p>
 */
@Optional.InterfaceList({
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = PluginManager.TESLA),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = PluginManager.TESLA),
		@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = PluginManager.IC2)
})
public abstract class TileEntityEnergyReceiver extends TileEntity
		implements IEnergyTypeAware, ITickable,	IEnergySink, IEnergyReceiver, ITeslaConsumer, ITeslaHolder {
	@CapabilityInject(ITeslaConsumer.class)
	public static Capability<ITeslaConsumer> TESLA_CONSUMER = null;

	@CapabilityInject(ITeslaHolder.class)
	public static Capability<ITeslaHolder> TESLA_HOLDER = null;

	private EnergyType energyType = EnergyType.INVALID;
	protected final EnergyStorage energyStorage;

	protected boolean init = false;

	public TileEntityEnergyReceiver(int capacity) {
		energyStorage = new EnergyStorage(capacity, false, true);
	}

	@Override
	public EnergyType getEnergyType() {
		return energyType;
	}

	@Override
	public void setEnergyType(EnergyType types) {
		energyType = types;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyType = EnergyType.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		energyType.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (world == null || !world.isRemote) {
			if (init) {
				init = false;
				unloadMachine();
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (world == null || !world.isRemote) {
			if (init) {
				init = false;
				unloadMachine();
			}
		}
	}

	@Override
	public final void update() {
		if (!init) {
			init = true;
			if (!world.isRemote) {
				loadMachine();
			}
		}
		if (world.isRemote) {
			updateMachineClient();
		} else {
			updateMachineServer();
		}
	}

	protected void updateMachineServer() {
	}

	protected void updateMachineClient() {
	}

	/**
	 * gets called only on the server on the first update tick
	 */
	protected void loadMachine() {
		if (energyType == EnergyType.IC2 && PluginManager.ic2Activated()) {
			//init ic2 energy net
			IC2EnergyPlugin.postIC2TileLoadEvent(this);
		}
	}

	/**
	 * gets called only on the server when tile is invalidated or unloaded
	 */
	protected void unloadMachine() {
		if (energyType == EnergyType.IC2 && PluginManager.ic2Activated()) {
			IC2EnergyPlugin.postIC2TileUnloadEvent(this);
		}
	}

	/**
	 * forwarding call to {@link EnergyStorage#useEnergy(int, EnergyType)} using the tiles EnergyType
	 */
	public boolean useEnergy(int amount) {
		return energyStorage.useEnergy(amount, energyType);
	}

	public EnergyStorage getEnergyStorage() {
		return energyStorage;
	}

	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		//Redstone-Flux
		if (energyType != EnergyType.RF) {
			return 0;
		}
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		//Redstone-Flux
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		//Redstone-Flux
		return energyStorage.getMaxEnergyStored();
	}

	/**
	 * This method needs to implemented by subclasses and will be used for all energy apis
	 */
	@Override
	public abstract boolean canConnectEnergy(EnumFacing from);

	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public double getDemandedEnergy() {
		//EU - IC2
		if (energyType != EnergyType.IC2) {
			return 0;
		}
		return energyStorage.getRoomForEnergy();
	}

	@Override
	public abstract int getSinkTier();

	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public double injectEnergy(EnumFacing from, double amount, double voltage) {
		//EU - IC2
		if (energyType != EnergyType.IC2) {
			return amount;
		}
		int received = (int) amount;
		energyStorage.receiveEnergyAll(received, false);
		return amount - received;
	}

	@Optional.Method(modid = PluginManager.IC2)
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing from) {
		//EU - IC2
		return energyType == EnergyType.IC2 && canConnectEnergy(from);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		switch (energyType) {
			case TESLA:
				if (capability == TESLA_CONSUMER || capability == TESLA_HOLDER) {
					return true;
				}
				break;
			case FORGE:
				if(capability == CapabilityEnergy.ENERGY) {
					return true;
				}
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		switch (energyType) {
			case TESLA:
				if (capability == TESLA_CONSUMER) {
					return TESLA_CONSUMER.cast(this);
				} else if (capability == TESLA_HOLDER) {
					return TESLA_HOLDER.cast(this);
				}
				break;
			case FORGE:
				if (capability == CapabilityEnergy.ENERGY) {
					return CapabilityEnergy.ENERGY.cast(energyStorage);
				}
				break;
		}
		return super.getCapability(capability, facing);
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long givePower(long power, boolean simulated) {
		//Tesla
		if (energyType != EnergyType.TESLA) {
			return 0;
		}
		return (long) energyStorage.receiveEnergy((int) power, simulated);
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long getStoredPower() {
		//Tesla
		if (energyType != EnergyType.TESLA) {
			return 0;
		}
		return (long) energyStorage.getEnergyStored();
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long getCapacity() {
		//Tesla
		if (energyType != EnergyType.TESLA) {
			return 0;
		}
		return energyStorage.getMaxEnergyStored();
	}

}
