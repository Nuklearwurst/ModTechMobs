package com.fravokados.techmobs.item;

import com.fravokados.techmobs.ModTechMobs;
import com.fravokados.techmobs.lib.GUIIDs;
import com.fravokados.techmobs.lib.Strings;
import com.fravokados.techmobs.upgrade.IUpgradable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Tool used to manipulate machines
 * @author Nuklearwurst
 */
public class ItemUpgradeTool extends ItemTM {

	public ItemUpgradeTool() {
		this.setUnlocalizedName(Strings.Item.UPGRADE_TOOL);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float f1, float f2, float f3) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IUpgradable) {
			player.openGui(ModTechMobs.instance, GUIIDs.UPGRADE_TOOL, world, x, y, z);
		}
		return true;
	}
}
