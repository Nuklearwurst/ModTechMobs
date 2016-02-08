package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.api.upgrade.IUpgrade;
import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.SimpleNonStackingUpgrade;
import com.fravokados.dangertech.api.upgrade.UpgradeTypes;
import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.Textures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ItemMindDimUpgrade extends ItemMDMultiType implements IUpgrade {

    //FIXME: REVERSE upgrade not working
    public static final int META_EMPTY = 0;
    public static final int META_REVERSE_DIRECTION = 1;
    public static final int META_CLOSE_INCOMING_PORTAL = 2;

    public ItemMindDimUpgrade() {
        super(Strings.Item.upgrade);
    }

    @Override
    public IUpgradeDefinition getUpgradeDefinition(ItemStack item) {
        switch (item.getItemDamage()) {
            case META_REVERSE_DIRECTION:
                return new SimpleNonStackingUpgrade(UpgradeTypes.REVERSE_DIRECTION);
            case META_CLOSE_INCOMING_PORTAL:
                return new SimpleNonStackingUpgrade(UpgradeTypes.DISCONNECT_INCOMING);
        }
        return null;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
	    list.add(new ItemStack(item, 1, META_EMPTY));
        list.add(new ItemStack(item, 1, META_REVERSE_DIRECTION));
        list.add(new ItemStack(item, 1, META_CLOSE_INCOMING_PORTAL));
    }

    @Override
    protected String getNameForItemStack(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case META_REVERSE_DIRECTION:
                return Strings.Item.upgradeReverse;
            case META_CLOSE_INCOMING_PORTAL:
                return Strings.Item.upgradeDisconnect;
        }
        return Strings.Item.upgrade;
    }

    @Override
    public void registerModels() {
	    registerItemModel(Textures.ITEM_UPGRADE_DUMMY, META_EMPTY);
	    registerItemModel(Textures.ITEM_UPGRADE_DISCONNECT, META_CLOSE_INCOMING_PORTAL);
	    registerItemModel(Textures.ITEM_UPGRADE_REVERSE_DIRECTION, META_REVERSE_DIRECTION);
    }
}
