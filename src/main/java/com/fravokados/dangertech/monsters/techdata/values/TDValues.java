package com.fravokados.dangertech.monsters.techdata.values;

import com.fravokados.dangertech.api.monsters.techdata.TechdataRegistries;
import com.fravokados.dangertech.api.monsters.techdata.values.ITechdataCapability;
import com.fravokados.dangertech.api.monsters.techdata.values.IValueRegistry;
import com.fravokados.dangertech.api.monsters.techdata.values.player.TDEntryItem;
import com.fravokados.dangertech.api.monsters.techdata.values.player.TDEntrySimpleItem;
import com.fravokados.dangertech.api.monsters.techdata.values.player.TDEntrySimpleMultiItem;
import com.fravokados.dangertech.api.monsters.techdata.values.world.TDEntrySimpleTileEntity;
import com.fravokados.dangertech.api.monsters.techdata.values.world.TDEntryTileEntity;
import com.fravokados.dangertech.monsters.configuration.Settings;
import com.fravokados.dangertech.monsters.lib.util.LogHelperTM;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * contains TDValue lists for blocks and items
 *
 * @author Nuklearwurst
 */
public class TDValues implements IValueRegistry {

	/**
	 * Contains information about Tileentities
	 */
	public final Map<Class<? extends TileEntity>, TDEntryTileEntity> tileEntityEntries = new HashMap<>();

	/**
	 * Contains information about Items
	 */
	public final Map<Item, TDEntryItem> itemEntries = new HashMap<>();

	/**
	 * registers a new Item Entry
	 */
	@Override
	public void registerItemEntry(Item item, @Nullable TDEntryItem entry) {
		//unneeded
		if (entry == null && itemEntries.containsKey(item)) {
			LogHelperTM.warn("Removing item-techdata mapping!");
			itemEntries.remove(item);
			return;
		}
		//warning
		if (itemEntries.containsKey(item)) {
			LogHelperTM.error("Error registering item-techdata!");
			LogHelperTM.error("The item " + item.getUnlocalizedName() + " already has an associated techdata entry!");
			LogHelperTM.error("Overwriting existing data!");
		}
		itemEntries.put(item, entry);
		LogHelperTM.info("Registered item: " + item.getUnlocalizedName() + " with entry: " + entry);
	}

	/**
	 * registers a simple Item entry
	 */
	@Override
	public void registerItemEntry(Item item, int value) {
		registerItemEntry(item, new TDEntrySimpleItem(value));
	}

	@Override
	public void registerItemEntry(ItemStack item, int value) {
		registerItemEntry(item.getItem(), value);
	}

	/**
	 * registers multiple Item Meta-sensitive with
	 */
	@Override
	public void registerMultiItemEntry(Item item, int[] meta, int[] values) {
		TDEntrySimpleMultiItem entry = getOrCreateMultiItemEntry(item);
		entry.add(meta, values);
		registerItemEntry(item, entry);
	}

	/**
	 * registers an Item Meta-sensitive
	 */
	@Override
	public void registerMultiItemEntry(Item item, int meta, int values) {
		TDEntrySimpleMultiItem entry = getOrCreateMultiItemEntry(item);
		entry.add(meta, values);
		registerItemEntry(item, entry);
	}

	private TDEntrySimpleMultiItem getOrCreateMultiItemEntry(Item item) {
		TDEntrySimpleMultiItem entry = new TDEntrySimpleMultiItem();
		if (itemEntries.containsKey(item)) {
			TDEntryItem old = itemEntries.get(item);
			if (old instanceof TDEntrySimpleItem) {
				//Migrate simple item entry to MultiItem entry
				entry.add((TDEntrySimpleItem) old);
			} else if (old instanceof TDEntrySimpleMultiItem) {
				return (TDEntrySimpleMultiItem) old;
			}
		}
		return entry;
	}

	/**
	 * registers an ItemStack Meta-sensitive
	 */
	@Override
	public void registerMultiItemEntry(ItemStack stack, int value) {
		registerMultiItemEntry(stack.getItem(), stack.getItemDamage(), value);
	}

	/**
	 * registers a simple Item entry
	 */
	@Override
	public void registerItemEntry(Item item, int damage, int value) {
		registerItemEntry(item, new TDEntrySimpleItem(damage, value));
	}

	/**
	 * registers a new TileEntityEntry
	 */
	@Override
	public void registerTileEntityEntry(Class<? extends TileEntity> clazz, @Nullable TDEntryTileEntity entry) {
		//unneeded
		if (entry == null && tileEntityEntries.containsKey(clazz)) {
			LogHelperTM.warn("Removing tileentity-techdata mapping!");
			tileEntityEntries.remove(clazz);
			return;
		}
		//warning
		if (tileEntityEntries.containsKey(clazz)) {
			LogHelperTM.error("Error registering tileentity-techdata!");
			LogHelperTM.error("The tileentity " + clazz.toString() + " already has an associated techdata entry!");
			LogHelperTM.error("Overwriting existing data!");
		}
		tileEntityEntries.put(clazz, entry);
		LogHelperTM.info("Registered TileEntity: " + clazz + " with entry: " + entry);
	}

	/**
	 * registers a new TileEntityEntry with a fixed value
	 */
	@Override
	public void registerTileEntityEntry(Class<? extends TileEntity> clazz, int value) {
		registerTileEntityEntry(clazz, new TDEntrySimpleTileEntity(value));
	}

	@Override
	public TDEntryTileEntity getEntryTileEntity(Class<? extends TileEntity> clazz) {
		return tileEntityEntries.get(clazz);
	}

	@Override
	public TDEntryItem getEntryItem(Item item) {
		return itemEntries.get(item);
	}

	@Override
	public int getTechDataForItem(ItemStack stack) {
		TDEntryItem item = TDValues.getInstance().getEntryItem(stack.getItem());
		if (item != null) {
			return item.getTechLevelForItem(stack);
		} else if (stack.hasCapability(ITechdataCapability.TECHDATA, null)) {
			return stack.getCapability(ITechdataCapability.TECHDATA, null).getTechData();
		}
		return 0;
	}

	@Override
	public int getTechDataForTileEntity(TileEntity te) {
		TDEntryTileEntity entry = TDValues.getInstance().getEntryTileEntity(te.getClass().asSubclass(TileEntity.class));
		if (entry != null) {
			return entry.getTechValueForTileEntity(te);
		} else if (te.hasCapability(ITechdataCapability.TECHDATA, null)) {
			return te.getCapability(ITechdataCapability.TECHDATA, null).getTechData();
		}
		return 0;
	}

	public static void init() {
		if (Settings.DEBUG) {
			//tileentities
			getInstance().registerTileEntityEntry(TileEntityFurnace.class, 1000);
			//items
			getInstance().registerItemEntry(Items.DIAMOND_SWORD, 1000);
		}
	}

	public static IValueRegistry getInstance() {
		return TechdataRegistries.valueRegistry;
	}
}
