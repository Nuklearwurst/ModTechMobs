package com.fravokados.dangertech.api.core.upgrade;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Capability implemented by things that can be upgraded
 *
 * @author Nuklearwurst
 */
public interface IUpgradable {

	@CapabilityInject(IUpgradable.class)
	Capability<IUpgradable> UPGRADABLE = null;

	IUpgradeInventory getUpgradeInventory();

	void updateUpgradeInformation();


	static void register() {
		CapabilityManager.INSTANCE.register(IUpgradable.class, new Capability.IStorage<IUpgradable>() {
			@Override
			public NBTBase writeNBT(Capability<IUpgradable> capability, IUpgradable instance, EnumFacing side) {
				return instance.getUpgradeInventory().serializeNBT();
			}

			@Override
			public void readNBT(Capability<IUpgradable> capability, IUpgradable instance, EnumFacing side, NBTBase nbt) {
				assert nbt instanceof NBTTagCompound : "Storage is no nbttagcoumpound!";
				instance.getUpgradeInventory().deserializeNBT((NBTTagCompound) nbt);
			}
		}, () -> new IUpgradable() {
			private InventoryUpgrade inventoryUpgrade = new InventoryUpgrade(9);

			@Override
			public IUpgradeInventory getUpgradeInventory() {
				return inventoryUpgrade;
			}

			@Override
			public void updateUpgradeInformation() {

			}
		});
	}

}
