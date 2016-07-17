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
import net.minecraftforge.fml.common.Optional;

/**
 * Base {@link TileEntity} for machines that accept energy
 * <p>
 *     This will handle all inworld interaction with energy-apis and is {@link IEnergyTypeAware}
 * </p>
 */
@Optional.InterfaceList({
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = PluginManager.TESLA),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = PluginManager.TESLA)
})
public abstract class TileEntityEnergyReceiver extends TileEntity implements IEnergyTypeAware, ITickable, IEnergySink, IEnergyReceiver, ITeslaConsumer, ITeslaHolder {
	@CapabilityInject(ITeslaConsumer.class)
	public static Capability<ITeslaConsumer> TESLA_CONSUMER = null;

	@CapabilityInject(ITeslaHolder.class)
	public static Capability<ITeslaHolder> TESLA_HOLDER = null;

	private EnergyType energyType = EnergyType.INVALID;
	protected final EnergyStorage energyStorage;

	protected boolean init = false;

	public TileEntityEnergyReceiver(int capacity) {
		energyStorage = new EnergyStorage(capacity);
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
		if (worldObj == null || !worldObj.isRemote) {
			if(init) {
				init = false;
				unloadMachine();
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (worldObj == null || !worldObj.isRemote) {
			if(init) {
				init = false;
				unloadMachine();
			}
		}
	}

	@Override
	public final void update() {
		if(!init) {
			init = true;
			if(!worldObj.isRemote) {
				loadMachine();
			}
		}
		if(worldObj.isRemote) {
			updateMachineClient();
		} else {
			updateMachineServer();
		}
	}

	protected void updateMachineServer() {}

	protected void updateMachineClient() {}

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
	 * forwarding call to {@link EnergyStorage#useEnergy(double, EnergyType)} using the tiles EnergyType
	 */
	public boolean useEnergy(double amount) {
		return energyStorage.useEnergy(amount, energyType);
	}

	public EnergyStorage getEnergyStorage() {
		return energyStorage;
	}

	public double getEnergyStored() {
		return energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		//Redstone-Flux
		if(energyType != EnergyType.RF) {
			return 0;
		}
		return (int) energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		//Redstone-Flux
		return (int) energyStorage.getEnergyStored();
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

	@Override
	public double getDemandedEnergy() {
		//EU - IC2
		if(energyType != EnergyType.IC2) {
			return 0;
		}
		return energyStorage.getRoomForEnergy();
	}

	@Override
	public abstract int getSinkTier();

	@Override
	public double injectEnergy(EnumFacing from, double amount, double voltage) {
		//EU - IC2
		if(energyType != EnergyType.IC2) {
			return amount;
		}
		return amount - energyStorage.receiveEnergyAll(amount, false);
	}

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing from) {
		//EU - IC2
		return energyType == EnergyType.IC2 && canConnectEnergy(from);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(energyType == EnergyType.TESLA) {
			if(capability == TESLA_CONSUMER || capability == TESLA_HOLDER) {
				return true;
			}
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(energyType == EnergyType.TESLA) {
			if(capability == TESLA_CONSUMER) {
				return TESLA_CONSUMER.cast(this);
			} else if(capability == TESLA_HOLDER) {
				return TESLA_HOLDER.cast(this);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long givePower(long power, boolean simulated) {
		//Tesla
		if(energyType != EnergyType.TESLA) {
			return 0;
		}
		return (long) energyStorage.receiveEnergy(power, simulated);
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long getStoredPower() {
		//Tesla
		if(energyType != EnergyType.TESLA) {
			return 0;
		}
		return (long) energyStorage.getEnergyStored();
	}

	@Optional.Method(modid = PluginManager.TESLA)
	@Override
	public long getCapacity() {
		//Tesla
		if(energyType != EnergyType.TESLA) {
			return 0;
		}
		return energyStorage.getMaxEnergyStored();
	}

}
