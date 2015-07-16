package com.fravokados.mindim.block.tileentity;

import com.fravokados.mindim.block.BlockPortalFrame;
import com.fravokados.mindim.block.IBlockPlacedListener;
import com.fravokados.mindim.block.IFacingSix;
import com.fravokados.mindim.block.ModBlocks;
import com.fravokados.mindim.client.ClientPortalInfo;
import com.fravokados.mindim.portal.PortalConstructor;
import ic2.api.tile.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class TileEntityPortalFrame extends TileEntity implements IBlockPlacedListener, IEntityPortalMandatoryComponent, IFacingSix, IWrenchable {

	private boolean validPortal = false;

	private int coreX;
	private int coreY;
	private int coreZ;

	private short facing = 1;
	private TileEntityPortalControllerEntity.State controllerState;

	public void setPortalController(int x, int y, int z) {
		this.coreX = x;
		this.coreY = y;
		this.coreZ = z;
		this.validPortal = true;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void onBlockPostPlaced(World world, int x, int y, int z, int meta) {
		PortalConstructor.createPortalMultiBlock(world, x, y, z);
	}

	public boolean isActive() {
		TileEntity te = worldObj.getTileEntity(coreX, coreY, coreZ);
		return validPortal && te != null && te instanceof TileEntityPortalControllerEntity && ((TileEntityPortalControllerEntity) te).isActive();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		coreX = nbt.getInteger("coreX");
		coreY = nbt.getInteger("coreY");
		coreZ = nbt.getInteger("coreZ");
		facing = nbt.getShort("facing");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("coreX", coreX);
		nbt.setInteger("coreY", coreY);
		nbt.setInteger("coreZ", coreZ);
		nbt.setShort("facing", facing);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setShort("facing", facing);
		nbt.setInteger("x_core", coreX);
		nbt.setInteger("y_core", coreY);
		nbt.setInteger("z_core", coreZ);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		if(nbt != null && nbt.hasKey("facing")) {
//			int oldFacing = facing;
			facing = nbt.getShort("facing");
//			int oldCoreX = coreX;
//			int oldCoreY = coreY;
//			int oldCoreZ = coreZ;
			coreX = nbt.getInteger("x_core");
			coreY = nbt.getInteger("y_core");
			coreZ = nbt.getInteger("z_core");
			validPortal = true;
//			if(oldFacing != facing || oldCoreX != coreX || oldCoreY != coreY || oldCoreZ != coreZ) {
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//			}
		}
	}

	public void onDestroy() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(coreX, coreY, coreZ);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				if(((TileEntityPortalControllerEntity) te).isActive()) {
					worldObj.createExplosion(null, xCoord, yCoord, zCoord, 2.0F, false);
					((TileEntityPortalControllerEntity) te).closePortal(true);
				}
//				((TileEntityPortalControllerEntity) te).updateMetrics();
			}
		}
	}

	public void setFacing(short facing) {
		this.facing = facing;
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public float getWrenchDropRate() {
		return 1;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_FRAME_ENTITY);
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return side != facing;
	}

	public short getFacing() {
		return facing;
	}

	public TileEntityPortalControllerEntity.State getControllerState() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(coreX, coreY, coreZ);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).getState();
			}
		}
		return TileEntityPortalControllerEntity.State.NO_MULTIBLOCK;
	}

	public ClientPortalInfo getClientRenderInfo() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(coreX, coreY, coreZ);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).renderInfo;
			}
		}
		return null;
	}
}
