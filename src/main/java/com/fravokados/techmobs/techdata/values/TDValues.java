package com.fravokados.techmobs.techdata.values;

import java.util.HashMap;
import java.util.Map;

import com.fravokados.techmobs.configuration.Settings;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.techdata.values.player.TDEntryItem;
import com.fravokados.techmobs.techdata.values.player.TDEntrySimpleItem;
import com.fravokados.techmobs.techdata.values.world.TDEntrySimpleTileEntity;
import com.fravokados.techmobs.techdata.values.world.TDEntryTileEntity;

/**
 * contains TDValue lists for blocks and items
 * 
 * @author Nuklearwurst
 *
 */
public class TDValues {
	//TODO if needed maybe implement special value handlers that are not bound to one TileEntity class / one item

	/**
	 * Contains information about Tileentities
	 */
	public static Map<Class<? extends TileEntity>, TDEntryTileEntity> tileEntityEntries = new HashMap<Class<? extends TileEntity>, TDEntryTileEntity>();
	
	/**
	 * Contains information about Items
	 */
	public static Map<Item, TDEntryItem> itemEntries = new HashMap<Item, TDEntryItem>();
	
	/**
	 * registers a new Item Entry
	 * @param item
	 * @param entry
	 */
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
		LogHelper.info("Registered item: " + item.getUnlocalizedName() + "with entry: " + entry);
	}
	
	/**
	 * registers a simple Item entry
	 * @param item
	 * @param value
	 */
	public static void registerItemEntry(Item item, int value) {
		registerItemEntry(item, new TDEntrySimpleItem(value));
	}
	
	/**
	 * registers a simple Item entry
	 * @param item
	 * @param value
	 */
	public static void registerItemEntry(Item item, int damage, int value) {
		registerItemEntry(item, new TDEntrySimpleItem(damage, value));
	}
	
	/**
	 * registers a new TileEntityEntry
	 * @param clazz
	 * @param entry
	 */
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
	
	/**
	 * registers a new TileEntityEntry with a fixed value
	 * @param clazz
	 * @param value
	 */
	public static void registerTileEntityEntry(Class<? extends TileEntity> clazz, int value) {
		registerTileEntityEntry(clazz, new TDEntrySimpleTileEntity(value));
	}
	
	
	
	public static void init() {
		if(Settings.DEBUG) {
			//tileentities
			registerTileEntityEntry(TileEntityFurnace.class, 1000);
			//items
			registerItemEntry(Items.diamond_sword, 1000);
		}
	}
}
