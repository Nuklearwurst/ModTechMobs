package com.fravokados.techmobs.creativetab;

import com.fravokados.techmobs.lib.Strings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabTechMobs {

	public static final CreativeTabs TAB_TM = new CreativeTabs(Strings.CREATIVE_TAB) {
		@Override
		public Item getTabIconItem() {
			//TODO proper creative tab item icon
			return Items.rotten_flesh;
		}
	};

}
