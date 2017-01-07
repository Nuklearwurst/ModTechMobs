package com.fravokados.dangertech.api.core.upgrade;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public interface IUpgradeInventory extends IInventory, INBTSerializable<NBTTagCompound> {

	List<IUpgradeDefinition> getUpgrades();

	@Override
	default NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagList list = new NBTTagList();

		ItemUtils.writeInventoryContentsToNBT(this, list);

		nbt.setTag("Upgrades", list);

		return nbt;
	}

	@Override
	default void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Upgrades")) {
			NBTTagList list = nbt.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
			ItemUtils.readInventoryContentsFromNBT(this, list);
		}
	}
}
