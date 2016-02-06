package com.fravokados.dangertech.techmobs.common.handler;

import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.client.gui.GuiConservationUnit;
import com.fravokados.dangertech.techmobs.client.gui.GuiCreativeTechnology;
import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.inventory.ContainerConservationUnit;
import com.fravokados.dangertech.techmobs.inventory.ContainerCreativeTechnology;
import com.fravokados.dangertech.techmobs.lib.GUIIDs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.CREATIVE_TECHNOLOGY:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te instanceof TileEntityCreativeTechnology) {
					return new ContainerCreativeTechnology((TileEntityCreativeTechnology) te);
				}
				break;
			}
			case GUIIDs.CONSERVATION_UNIT:
			{
				List list = world.getEntitiesWithinAABB(EntityConservationUnit.class, AxisAlignedBB.fromBounds(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1));
				if(list.isEmpty() || list.size() > 1) {
					player.closeScreen();
					player.openContainer = null;
					player.addChatComponentMessage(new ChatComponentText("Cannot access! Please try a different location!"));
				} else {
					EntityConservationUnit e = (EntityConservationUnit) list.get(0);
					return new ContainerConservationUnit(player.inventory, e);
				}
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.CREATIVE_TECHNOLOGY:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te instanceof TileEntityCreativeTechnology) {
					return new GuiCreativeTechnology((TileEntityCreativeTechnology) te, new ContainerCreativeTechnology((TileEntityCreativeTechnology) te));
				}
				break;
			}
			case GUIIDs.CONSERVATION_UNIT:
			{
				Entity e = Minecraft.getMinecraft().objectMouseOver.entityHit;
				if (e == null || !(e instanceof EntityConservationUnit)) {
					List list = world.getEntitiesWithinAABB(EntityConservationUnit.class, AxisAlignedBB.fromBounds(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1));
					if(list.isEmpty() || list.size() > 1) {
						player.closeScreen();
						player.openContainer = null;
					} else {
						e = (Entity) list.get(0);
					}
				}
				if(e != null && e instanceof EntityConservationUnit) {
					return new GuiConservationUnit((EntityConservationUnit) e, new ContainerConservationUnit(player.inventory, (EntityConservationUnit) e));
				}
			}
		}
		return null;
	}
}
