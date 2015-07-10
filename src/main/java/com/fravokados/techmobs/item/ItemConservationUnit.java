package com.fravokados.techmobs.item;

import com.fravokados.techmobs.configuration.Settings;
import com.fravokados.techmobs.entity.EntityConservationUnit;
import com.fravokados.techmobs.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemConservationUnit extends ItemTM {

	public ItemConservationUnit() {
		super(Strings.Item.CONSERVATION_UNIT);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(Settings.DEBUG) {
			if (!world.isRemote) {
				EntityConservationUnit entity = new EntityConservationUnit(world);
				entity.setLocationAndAngles(x + 0.5, y + 1.5, z + 0.5, 0, 0);
				world.spawnEntityInWorld(entity);
			}
			return true;
		}
		return false;
	}
}
