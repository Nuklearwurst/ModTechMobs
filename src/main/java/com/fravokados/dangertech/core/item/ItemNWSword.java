package com.fravokados.dangertech.core.item;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.Reference;
import com.fravokados.dangertech.core.lib.util.ModelUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public class ItemNWSword extends ItemSword {

	private final String modId;
	private final String itemName;

	public ItemNWSword(ToolMaterial material, String modId, String registryName, CreativeTabs tab) {
		super(material);
		this.modId = modId;
		this.itemName = registryName;

		this.setRegistryName(modId, itemName);
		this.setUnlocalizedName(modId + ":" + itemName);

		this.setCreativeTab(tab);
	}

	public ItemNWSword(ToolMaterial material, String registryName) {
		this(material, Reference.MOD_ID, registryName, ModNwCore.CREATIVE_TABS);
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
