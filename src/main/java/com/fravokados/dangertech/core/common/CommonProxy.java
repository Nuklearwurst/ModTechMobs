package com.fravokados.dangertech.core.common;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.core.item.ItemNW;
import com.fravokados.dangertech.core.item.ItemNWSword;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerEvents() {}

	/**
	 * Register's the given block and any needed ItemBlock models
	 */
	public void registerBlock(BlockNW block, Class<? extends ItemBlock> itemBlock) {
		GameRegistry.registerBlock(block, itemBlock);
	}

	public void registerBlock(BlockNW block) {
		registerBlock(block, ItemBlock.class);
	}

	/**
	 * Register's the given item and any needed models
	 */
	public void registerItem(ItemNW item) {
		GameRegistry.registerItem(item);
	}

	public void registerItem(ItemNWSword item) {
		GameRegistry.registerItem(item);
	}

}
