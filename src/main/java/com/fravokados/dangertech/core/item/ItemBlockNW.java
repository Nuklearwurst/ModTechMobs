package com.fravokados.dangertech.core.item;

import com.fravokados.dangertech.core.block.BlockNW;
import com.fravokados.dangertech.core.client.IModelProvider;
import com.fravokados.dangertech.core.lib.util.ModelUtils;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Base class for ItemBlocks
 */
public class ItemBlockNW extends ItemBlock implements IModelProvider {

	public ItemBlockNW(BlockNW block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		registerItemBlockModel(getBlockName(), 0);
	}

	public String getBlockName() {
		return ((BlockNW)block).getBlockName();
	}

	public String getModId() {
		return ((BlockNW)block).getModId();
	}

	@SideOnly(Side.CLIENT)
	protected void registerItemBlockModel(String name, int meta) {
		ModelUtils.registerModelVariant(this, meta, getModId(), name);
	}
}
