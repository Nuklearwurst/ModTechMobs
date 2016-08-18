package com.fravokados.dangertech.core.plugin.energy;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.PluginManager;
import com.fravokados.dangertech.core.plugin.ic2.IC2EnergyPlugin;
import com.fravokados.dangertech.core.plugin.vanilla.VanillaEnergyPlugin;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Functions to help manage energy apis
 * @author Nuklearwurst
 */
public class EnergyManager {

	private static final Map<EnergyType, IEnergyPlugin> plugins = new EnumMap<>(EnergyType.class);
	private static final Set<EnergyType> availableEnergyTypes = EnumSet.noneOf(EnergyType.class);

	/**
	 * checks whether this item can provide energy to the specified energy type
	 * @param item the itemstack to test
	 * @param type the energy type
	 * @param sinkTier sink tier
	 * @return true if the given item can provide energy to the specified energy type
	 */
	public static boolean canItemProvideEnergy(@Nullable ItemStack item, EnergyType type, int sinkTier) {
		if(item == null) return false;

		if(plugins.containsKey(type)) {
			return plugins.get(type).canItemProvideEnergy(item, type, sinkTier);
		}
		return false;
	}

	public static void createItemVariantsForEnergyTypes(List<ItemStack> list, Item item, int meta, Collection<EnergyType> types) {
		for (EnergyType type : types) {
			list.add(createItemStackWithEnergyType(item, 1, meta, type));
		}
	}

	public static void createItemVariantsForEnergyTypes(List<ItemStack> list, ItemStack item, Collection<EnergyType> types) {
		for (EnergyType type : types) {
			list.add(addEnergyTypeInformationToItem(item.copy(), type));
		}
	}

	public static ItemStack createItemStackWithEnergyType(Block block, int amount, int damage, EnergyType type) {
		Item item = Item.getItemFromBlock(block);
		assert item != null;
		return createItemStackWithEnergyType(item, amount, damage, type);
	}

	public static ItemStack createItemStackWithEnergyType(Item item, int amount, int damage, EnergyType type) {
		ItemStack stack = new ItemStack(item, amount, damage);
		addEnergyTypeInformationToItem(stack, type);
		return stack;
	}

	public static ItemStack addEnergyTypeInformationToItem(ItemStack item, EnergyType type) {
		type.writeToNBT(ItemUtils.getNBTTagCompound(item));
		return item;
	}

	public static void writeEnergyTypeToEnergyTypeAware(IEnergyTypeAware tile, ItemStack stack) {
		if(!stack.hasTagCompound()) return;
		//noinspection ConstantConditions
		EnergyType types = EnergyType.readFromNBT(stack.getTagCompound());
		if(types != EnergyType.INVALID) {
			tile.setEnergyType(types);
		}
	}

	/**
	 * searches an inventory for Items with an EnergyType
	 * @param inv inventory that is to be searched
	 * @return returns the first energytype found. Null if none.
	 */
	public static EnergyType getFirstEnergyTypeOfInventory(IInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.hasTagCompound()) {
				//noinspection ConstantConditions
				EnergyType type = EnergyType.readFromNBT(stack.getTagCompound());
				if (type != EnergyType.INVALID) {
					return type;
				}
			}

		}
		return null;
	}

	/**
	 * searches an inventory for Items with an EnergyType
	 * @param inv inventory that is to be searched
	 * @return null if none of the items have an EnergyType or different ones were found. Otherwise this will return the EnergyType found.
	 */
	public static EnergyType getEnergyTypeOfInventory(IInventory inv) {
		EnergyType typeFound = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack currentStack = inv.getStackInSlot(i);
			if (currentStack != null) {
				//make sure energy types line up
				if (currentStack.hasTagCompound()) {
					//noinspection ConstantConditions
					EnergyType type = EnergyType.readFromNBT(currentStack.getTagCompound());
					if (type != EnergyType.INVALID) {
						if (typeFound == null) {
							typeFound = type;
						} else if (typeFound != type) {
							return null;
						}
					}
				}
			}
		}
		return typeFound;
	}

	public static void rechargeEnergyStorageFromInventory(EnergyStorage storage, EnergyType type, IInventory inventory, int slot, int sinkTier) {
		if (!storage.isFull()) {
			final ItemStack stack = inventory.getStackInSlot(slot);
			if (stack != null) {
				if(plugins.containsKey(type)) {
					plugins.get(type).rechargeEnergyStorageFromInventory(stack, storage, type, inventory, slot, sinkTier);
				}
			}
		}
	}

	public static void init() {
		plugins.clear();
		availableEnergyTypes.clear();

		plugins.put(EnergyType.VANILLA, new VanillaEnergyPlugin());
		availableEnergyTypes.add(EnergyType.VANILLA);

		if(PluginManager.ic2Activated()) {
			plugins.put(EnergyType.IC2, new IC2EnergyPlugin());
			availableEnergyTypes.add(EnergyType.IC2);
		}

		if(PluginManager.teslaActivated()) {
			availableEnergyTypes.add(EnergyType.TESLA);
		}

		availableEnergyTypes.add(EnergyType.RF);
	}

	public static void disableVanillaEnergyType() {
		availableEnergyTypes.remove(EnergyType.VANILLA);
	}

	public static void registerEnergyPlugin(EnergyType type, IEnergyPlugin plugin) {
		availableEnergyTypes.add(type);
		plugins.put(type, plugin);
	}

	public static Set<EnergyType> getAvailableEnergyTypes() {
		return availableEnergyTypes;
	}
}
