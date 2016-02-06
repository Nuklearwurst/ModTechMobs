package com.fravokados.dangertech.core.block;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.util.ModelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public class BlockNW extends Block {

	private final String blockName;
	private final String modId;

	public BlockNW(String registryName, Material material) {
		this(material, Reference.MOD_ID, registryName, ModNwCore.CREATIVE_TABS);
	}

	public BlockNW(String registryName) {
		this(Material.rock, Reference.MOD_ID, registryName, ModNwCore.CREATIVE_TABS);
	}

	public BlockNW(Material material, String modId, String registryName, CreativeTabs tab) {
		super(material);
		this.modId = modId;
		this.blockName = registryName;

		this.setRegistryName(modId, blockName);
		this.setUnlocalizedName(modId + ":" + blockName);

		this.setCreativeTab(tab);
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		registerItemBlockModel(blockName, 0);
	}

	@SideOnly(Side.CLIENT)
	protected void registerItemBlockModel(String name, int meta) {
		ModelUtils.registerModelVariant(Item.getItemFromBlock(this), meta, modId, name);
	}

	public String getBlockName() {
		return blockName;
	}
}
