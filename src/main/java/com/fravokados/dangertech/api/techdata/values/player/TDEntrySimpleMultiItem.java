package com.fravokados.dangertech.api.techdata.values.player;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nuklearwurst
 */
public class TDEntrySimpleMultiItem extends TDEntryItem {

	private Map<Integer, Integer> items = new HashMap<>();

	public TDEntrySimpleMultiItem() {

	}

	public TDEntrySimpleMultiItem(int meta, int value) {
		add(meta, value);
	}

	public TDEntrySimpleMultiItem(ItemStack stack, int value) {
		add(stack, value);
	}

	public TDEntrySimpleMultiItem(int[] stacks, int[] values) {
		add(stacks, values);
	}

	public TDEntrySimpleMultiItem(ItemStack[] stacks, int[] values) {
		add(stacks, values);
	}

	public void add(Map<Integer, Integer> stacks) {
		items.putAll(stacks);
	}

	public void add(TDEntrySimpleItem stack) {
		items.put(stack.meta, stack.value);
	}

	public void add(int meta, int value) {
		items.put(meta, value);
	}

	public void add(ItemStack stack, int value) {
		items.put(stack.getItemDamage(), value);
	}

	public void add(int[] stacks, int[] values) {
		for(int i = 0; i < stacks.length; i++) {
			items.put(stacks[i], values[i]);
		}
	}

	public void add(ItemStack[] stacks, int[] values) {
		for(int i = 0; i < stacks.length; i++) {
			items.put(stacks[i].getItemDamage(), values[i]);
		}
	}

	public TDEntrySimpleMultiItem(Map<Integer, Integer> stacks) {
		items.putAll(stacks);
	}

	public TDEntrySimpleMultiItem(TDEntrySimpleItem stack) {
		items.put(stack.meta, stack.value);
	}

	@Override
	public int getTechLevelForItem(ItemStack item) {
		for(Map.Entry<Integer, Integer> stack : items.entrySet()) {
			if(stack.getKey() == item.getItemDamage()) {
				return stack.getValue();
			}
		}
		return 0;
	}
}
