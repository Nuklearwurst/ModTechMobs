package com.fravokados.dangertech.mindim.block.tileentity;

import com.fravokados.dangertech.api.block.IBlockPlacedListener;
import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.api.portal.IEntityPortalMandatoryComponent;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.mindim.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.mindim.block.types.PortalFrameState;
import com.fravokados.dangertech.mindim.client.ClientPortalInfo;
import com.fravokados.dangertech.mindim.portal.PortalConstructor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class TileEntityPortalFrame extends TileEntity implements IBlockPlacedListener, IEntityPortalMandatoryComponent, IFacingSix, IPortalFrameWithState {

	private boolean validPortal = false;

	private BlockPos controllerPos;

	private EnumFacing facing = EnumFacing.NORTH;

	public void setPortalController(BlockPos controllerPos) {
		this.controllerPos = controllerPos;
		this.validPortal = true;
		worldObj.markBlockForUpdate(getPos());
	}

	@Override
	public void onBlockPostPlaced(World world, BlockPos pos, IBlockState state) {
		PortalConstructor.createPortalMultiBlock(world, pos);
	}

	public boolean isActive() {
		TileEntity te = worldObj.getTileEntity(controllerPos);
		return validPortal && te != null && te instanceof TileEntityPortalControllerEntity && ((TileEntityPortalControllerEntity) te).isActive();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagCompound nbtController = nbt.getCompoundTag("controllerPos");
		if(nbtController != null) {
			controllerPos = BlockUtils.readBlockPosFromNBT(nbtController);
		}
		facing = EnumFacing.getFront(nbt.getByte("facing"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound nbtController = new NBTTagCompound();
		BlockUtils.writeBlockPosToNBT(controllerPos, nbtController);
		nbt.setTag("controllerPos", nbtController);
		nbt.setByte("facing", (byte) facing.getIndex());
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("facing", (byte) facing.getIndex());
		if(controllerPos != null) {
			nbt.setInteger("x_core", controllerPos.getX());
			nbt.setInteger("y_core", controllerPos.getY());
			nbt.setInteger("z_core", controllerPos.getZ());
		}
		return new S35PacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		if(nbt != null && nbt.hasKey("facing")) {
			facing = EnumFacing.getFront(nbt.getShort("facing"));
			if(nbt.hasKey("x_core")) {
				controllerPos = new BlockPos(nbt.getInteger("x_core"), nbt.getInteger("y_core"), nbt.getInteger("z_core"));
			} else {
				controllerPos = null;
			}
			validPortal = true;
			this.worldObj.markBlockForUpdate(getPos());
		}
	}

	public void onDestroy() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				if(((TileEntityPortalControllerEntity) te).isActive()) {
					worldObj.createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 2.0F, false);
					((TileEntityPortalControllerEntity) te).closePortal(true);
				}
			}
		}
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

//	@Override
//	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
//		return true;
//	}
//
//	@Override
//	public float getWrenchDropRate() {
//		return 1;
//	}
//
//	@Override
//	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
//		return new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_FRAME.ordinal());
//	}
//
//	@Override
//	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
//		return side != facing;
//	}

	public EnumFacing getFacing() {
		return facing;
	}

	public TileEntityPortalControllerEntity.State getControllerState() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).getState();
			}
		}
		return TileEntityPortalControllerEntity.State.NO_MULTIBLOCK;
	}

	public ClientPortalInfo getClientRenderInfo() {
		if(validPortal) {
			TileEntity te = worldObj.getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).renderInfo;
			}
		}
		return null;
	}

	@Override
	public PortalFrameState getPortalFrameState() {
		if(validPortal && controllerPos != null) {
			TileEntity te = worldObj.getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).getPortalFrameState();
			}
		}
		return PortalFrameState.DISABLED;
	}
}
