package com.fravokados.dangertech.core.block;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.item.ItemBlockNW;
import com.fravokados.dangertech.core.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

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
		this(Material.ROCK, Reference.MOD_ID, registryName, ModNwCore.CREATIVE_TABS);
	}

	public BlockNW(Material material, String modId, String registryName, CreativeTabs tab) {
		super(material);
		this.modId = modId;
		this.blockName = registryName;

		this.setRegistryName(modId, blockName);
		this.setUnlocalizedName(modId + ":" + blockName);

		this.setCreativeTab(tab);
	}

	public String getBlockName() {
		return blockName;
	}

	/**
	 * Creates a new instance of the associated Item for this Block
	 * <p/>
	 * Can return null when Block has no Item
	 * @return an Item associated with this block (registry name already set)
	 */
	@Nullable
	public Item createItemBlock() {
		return new ItemBlockNW(this);
	}

	public String getModId() {
		return modId;
	}
}
