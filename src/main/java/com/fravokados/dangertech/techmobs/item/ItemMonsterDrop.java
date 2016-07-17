package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.lib.Textures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class ItemMonsterDrop extends ItemTM {

	public ItemMonsterDrop() {
		super(Strings.Item.MONSTER_DROP);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab,
			List list) {
		for(int i = 0; i < Strings.Item.MONSTER_DROP_SUBTYPES.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}


	@Override
	public void registerModels() {
		for (int i = 0; i < Strings.Item.MONSTER_DROP_SUBTYPES.length; i++)
		{
			registerItemModel(Strings.Item.MONSTER_DROP + "_" + Strings.Item.MONSTER_DROP_SUBTYPES[i], i);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack s) {
		return String.format("item.%s%s_%s", Textures.MOD_ASSET_DOMAIN, Strings.Item.MONSTER_DROP, Strings.Item.MONSTER_DROP_SUBTYPES[MathHelper.clamp_int(s.getItemDamage(), 0, Strings.Item.MONSTER_DROP_SUBTYPES.length - 1)]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player,
			List tags, boolean b) {
		tags.add(I18n.translateToLocal(Strings.Item.MONSTER_DROP_TOOLTIP[MathHelper.clamp_int(stack.getItemDamage(), 0, Strings.Item.MONSTER_DROP_TOOLTIP.length)]));
	}

}
