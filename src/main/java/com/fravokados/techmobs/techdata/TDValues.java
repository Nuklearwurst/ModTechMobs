package com.fravokados.techmobs.techdata;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.techdata.values.TDEntryItem;
import com.fravokados.techmobs.techdata.values.TDEntrySimpleTileEntity;
import com.fravokados.techmobs.techdata.values.TDEntryTileEntity;

/**
 * contains TDValue lists for blocks and items
 * 
 * @author Nuklearwurst
 *
 */
public class TDValues {

	/**
	 * Contains information about Tileentities
	 */
	public static Map<Class<? extends TileEntity>, TDEntryTileEntity> tileEntityEntries = new HashMap<Class<? extends TileEntity>, TDEntryTileEntity>();
	
	/**
	 * Contains information about Items
	 */
	public static Map<Item, TDEntryItem> itemEntries = new HashMap<Item, TDEntryItem>();
	
	public static void registerItemEntry(Item item, TDEntryItem entry) {
		//unneeded
		if(entry == null && itemEntries.containsKey(item)) {
			LogHelper.warn("Removing item-techdata mapping!");
			itemEntries.remove(item);
			return;
		}
		//warning
		if(itemEntries.containsKey(item)) {
			LogHelper.error("Error registering item-techdata!");
			LogHelper.error("The item " + item.getUnlocalizedName() + " already has an associated techdata entry!");
			LogHelper.error("Overwriting existing data!");
		}
		itemEntries.put(item, entry);
	}
	
	public static void registerTileEntityEntry(Class<? extends TileEntity> clazz, TDEntryTileEntity entry) {
		//unneeded
		if(entry == null && tileEntityEntries.containsKey(clazz)) {
			LogHelper.warn("Removing tileentity-techdata mapping!");
			tileEntityEntries.remove(clazz);
			return;
		}
		//warning
		if(tileEntityEntries.containsKey(clazz)) {
			LogHelper.error("Error registering tileentity-techdata!");
			LogHelper.error("The tileentity " + clazz.toString() + " already has an associated techdata entry!");
			LogHelper.error("Overwriting existing data!");
		}
		tileEntityEntries.put(clazz, entry);
		LogHelper.info("Registered TileEntity: " + clazz + " with entry: " + entry);
	}
	
	public static void registerTileEntityEntry(Class<? extends TileEntity> clazz, int value) {
		registerTileEntityEntry(clazz, new TDEntrySimpleTileEntity(value));
	}
	
	public static void init() {
		registerTileEntityEntry(TileEntityFurnace.class, 1000);
	}
}
