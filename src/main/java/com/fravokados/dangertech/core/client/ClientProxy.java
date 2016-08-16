package com.fravokados.dangertech.core.client;

import com.fravokados.dangertech.core.common.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public Item registerItem(Item item) {
		super.registerItem(item);
		if(item instanceof IModelProvider) {
			((IModelProvider)item).registerModels();
		}
		return item;
	}
}
