package com.fravokados.dangertech.core.item;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.util.ModelUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNW extends Item {

	private final String modId;
	private final String itemName;

	public ItemNW(String itemName) {
		this(Reference.MOD_ID, itemName, ModNwCore.CREATIVE_TABS);
	}

	/**
	 * @param modId the mod that register's this item
	 * @param registryName this name will be used both for registry, as well as for localization
	 * @param tab the creativetab this item should get added to
	 */
	public ItemNW(String modId, String registryName, CreativeTabs tab) {
		super();
		this.modId = modId;
		this.itemName = registryName;

		this.setRegistryName(modId, itemName);
		this.setUnlocalizedName(modId + ":" + itemName);

		this.setCreativeTab(tab);
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		registerItemModel(itemName, 0);
	}

	@SideOnly(Side.CLIENT)
	protected void registerItemModel(String name, int meta) {
		ModelUtils.registerModelVariant(this, meta, modId, name);
	}

	public String getItemName() {
		return itemName;
	}
}
