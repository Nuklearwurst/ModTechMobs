package com.fravokados.dangertech.core.client;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.core.common.CommonProxy;
import com.fravokados.dangertech.core.item.ItemNW;
import com.fravokados.dangertech.core.item.ItemNWSword;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void registerBlock(BlockNW block, Class<? extends ItemBlock> itemBlock) {
		super.registerBlock(block, itemBlock);
		block.registerModels();
	}

	@Override
	public void registerItem(ItemNW item) {
		super.registerItem(item);
		item.registerModels();
	}

	@Override
	public void registerItem(ItemNWSword item) {
		super.registerItem(item);
		item.registerModels();
	}
}
