package com.fravokados.dangertech.api.techdata.values;

import com.fravokados.dangertech.api.techdata.values.player.TDEntryItem;
import com.fravokados.dangertech.api.techdata.values.world.TDEntryTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Nuklearwurst
 */
public interface TDValueRegistry {
	void registerItemEntry(Item item, TDEntryItem entry);

	void registerItemEntry(Item item, int value);

	void registerItemEntry(ItemStack item, int value);

	void registerMultiItemEntry(Item item, int[] meta, int[] values);

	void registerMultiItemEntry(Item item, int meta, int values);

	void registerMultiItemEntry(ItemStack stack, int value);

	void registerItemEntry(Item item, int damage, int value);

	void registerTileEntityEntry(Class<? extends TileEntity> clazz, TDEntryTileEntity entry);

	void registerTileEntityEntry(Class<? extends TileEntity> clazz, int value);

	TDEntryTileEntity getEntryTileEntity(Class< ? extends TileEntity> clazz);

	TDEntryItem getEntryItem(Item item);

	int getTechDataForTileEntity(TileEntity te);

	int getTechDataForItem(ItemStack item);
}
