package com.fravokados.dangertech.core.item;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.GUIIDs;
import com.fravokados.dangertech.core.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Tool used to manipulate machines
 * @author Nuklearwurst
 */
public class ItemUpgradeTool extends ItemNW {

	public ItemUpgradeTool() {
		super(Strings.Item.UPGRADE_TOOL);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof IUpgradable) {
			playerIn.openGui(ModNwCore.instance, GUIIDs.UPGRADE_TOOL, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
