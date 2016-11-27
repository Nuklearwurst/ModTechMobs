package com.fravokados.dangertech.monsters.item;

import com.fravokados.dangertech.monsters.lib.Strings;
import com.fravokados.dangertech.monsters.lib.Textures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class ItemMonsterDrop extends ItemTM {

	public ItemMonsterDrop() {
		super(Strings.Item.MONSTER_DROP);
	}


	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
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
		return String.format("item.%s%s_%s", Textures.MOD_ASSET_DOMAIN, Strings.Item.MONSTER_DROP, Strings.Item.MONSTER_DROP_SUBTYPES[MathHelper.clamp(s.getItemDamage(), 0, Strings.Item.MONSTER_DROP_SUBTYPES.length - 1)]);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tags, boolean b) {
		tags.add(Strings.translate(Strings.Item.MONSTER_DROP_TOOLTIP[MathHelper.clamp(stack.getItemDamage(), 0, Strings.Item.MONSTER_DROP_TOOLTIP.length)]));
	}

}
