package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.mindim.lib.Strings;
import com.fravokados.dangertech.mindim.lib.Textures;
import com.fravokados.dangertech.api.upgrade.IUpgrade;
import com.fravokados.dangertech.api.upgrade.IUpgradeDefinition;
import com.fravokados.dangertech.api.upgrade.SimpleNonStackingUpgrade;
import com.fravokados.dangertech.api.upgrade.UpgradeTypes;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ItemMindDimUpgrade extends ItemMDMultiType implements IUpgrade {

    //FIXME: REVERSE upgrade not working
    public static final int META_REVERSE_DIRECTION = 0;
    public static final int META_CLOSE_INCOMING_PORTAL = 1;

    private IIcon iconReverse;
    private IIcon iconDisconnect;


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

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, META_REVERSE_DIRECTION));
        list.add(new ItemStack(item, 1, META_CLOSE_INCOMING_PORTAL));
    }

    @Override
    protected String getUnlocalizedNameForItem(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case META_REVERSE_DIRECTION:
                return Strings.Item.upgradeReverse;
            case META_CLOSE_INCOMING_PORTAL:
                return Strings.Item.upgradeDisconnect;
        }
        return Strings.Item.upgrade;
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon(Textures.ITEM_UPGRADE_DUMMY);
        iconDisconnect = reg.registerIcon(Textures.ITEM_UPGRADE_DISCONNECT);
        iconReverse = reg.registerIcon(Textures.ITEM_UPGRADE_REVERSE_DIRECTION);
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        switch (damage) {
            case META_REVERSE_DIRECTION:
                return iconReverse;
            case META_CLOSE_INCOMING_PORTAL:
                return iconDisconnect;
        }
        return iconDisconnect;
    }
}
