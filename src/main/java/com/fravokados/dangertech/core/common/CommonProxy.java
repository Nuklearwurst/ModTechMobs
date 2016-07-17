package com.fravokados.dangertech.core.common;

import com.fravokados.dangertech.core.block.BlockNW;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerEvents() {}

	/**
	 * Register's the given block and any needed ItemBlock models
	 */
	public void registerBlock(BlockNW block) {
		GameRegistry.register(block);
		Item item = block.createItemBlock();
		if(item != null) {
			registerItem(item);
		}
	}

	/**
	 * Register's the given item and any needed models
	 */
	public void registerItem(Item item) {
		GameRegistry.register(item);
	}
}
