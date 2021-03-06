package com.fravokados.dangertech.monsters.common.handler;

import com.fravokados.dangertech.monsters.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.monsters.client.gui.GuiConservationUnit;
import com.fravokados.dangertech.monsters.client.gui.GuiCreativeTechnology;
import com.fravokados.dangertech.monsters.entity.EntityConservationUnit;
import com.fravokados.dangertech.monsters.inventory.ContainerConservationUnit;
import com.fravokados.dangertech.monsters.inventory.ContainerCreativeTechnology;
import com.fravokados.dangertech.monsters.lib.GUIIDs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class GuiHandler implements IGuiHandler {

	@Override
	@Nullable
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.CREATIVE_TECHNOLOGY: {
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof TileEntityCreativeTechnology) {
					return new ContainerCreativeTechnology((TileEntityCreativeTechnology) te);
				}
				break;
			}
			case GUIIDs.CONSERVATION_UNIT: {
				Entity e = world.getEntityByID(x);
				if (e != null && e instanceof EntityConservationUnit) {
					return new ContainerConservationUnit(player.inventory, (EntityConservationUnit) e);
				}
				break;
			}
		}
		return null;
	}

	@Override
	@Nullable
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.CREATIVE_TECHNOLOGY: {
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof TileEntityCreativeTechnology) {
					return new GuiCreativeTechnology((TileEntityCreativeTechnology) te, new ContainerCreativeTechnology((TileEntityCreativeTechnology) te));
				}
				break;
			}
			case GUIIDs.CONSERVATION_UNIT: {
				Entity e = world.getEntityByID(x);
				if (e != null && e instanceof EntityConservationUnit) {
					return new GuiConservationUnit((EntityConservationUnit) e, new ContainerConservationUnit(player.inventory, (EntityConservationUnit) e));
				}
			}
		}
		return null;
	}
}
