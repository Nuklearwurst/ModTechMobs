package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.configuration.Settings;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemConservationUnit extends ItemTM {

	public ItemConservationUnit() {
		super(Strings.Item.CONSERVATION_UNIT);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(Settings.DEBUG) {
			if (!world.isRemote) {
				EntityConservationUnit entity = new EntityConservationUnit(world);
				entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0);
				world.spawnEntityInWorld(entity);
			}
			return true;
		}
		return false;
	}
}
