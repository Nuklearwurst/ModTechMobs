package com.fravokados.dangertech.portals.block.tileentity;

import com.fravokados.dangertech.api.core.block.IBlockPlacedListener;
import com.fravokados.dangertech.api.core.block.IFacingSix;
import com.fravokados.dangertech.api.portals.component.IEntityPortalMandatoryComponent;
import com.fravokados.dangertech.core.lib.util.BlockUtils;
import com.fravokados.dangertech.core.lib.util.WorldUtils;
import com.fravokados.dangertech.portals.block.types.IPortalFrameWithState;
import com.fravokados.dangertech.portals.block.types.PortalFrameState;
import com.fravokados.dangertech.portals.lib.NBTKeys;
import com.fravokados.dangertech.portals.portal.PortalConstructor;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class TileEntityPortalFrame extends TileEntity implements IBlockPlacedListener, IEntityPortalMandatoryComponent, IFacingSix, IPortalFrameWithState {


	private BlockPos controllerPos;

	private EnumFacing facing = EnumFacing.DOWN;

	@Override
	public void setPortalController(BlockPos controllerPos, PortalMetrics metrics) {
		this.controllerPos = controllerPos;
		facing = metrics.front;
		WorldUtils.notifyBlockUpdateAtTile(this);
	}

	@Override
	public void disconnectController() {
		this.controllerPos = null;
	}

	@Override
	public void onBlockPostPlaced(World world, BlockPos pos, IBlockState state) {
		PortalConstructor.createPortalMultiBlock(world, pos);
	}

	public boolean isActive() {
		if(controllerPos == null) {
			return false;
		}
		TileEntity te = getWorld().getTileEntity(controllerPos);
		return te != null && te instanceof TileEntityPortalControllerEntity && ((TileEntityPortalControllerEntity) te).isActive();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		controllerPos = BlockUtils.readBlockPosFromNBT(nbt.getCompoundTag("controllerPos"));
		facing = EnumFacing.getFront(nbt.getByte(NBTKeys.TILE_FACING));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setTag("controllerPos", BlockUtils.writeBlockPosToNBT(controllerPos, new NBTTagCompound()));
		nbt.setByte(NBTKeys.TILE_FACING, (byte) facing.getIndex());
		return nbt;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = writeToNBT(new NBTTagCompound());
		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		if(nbt.hasKey(NBTKeys.TILE_FACING)) {
			facing = EnumFacing.getFront(nbt.getByte(NBTKeys.TILE_FACING));
			controllerPos = BlockUtils.readBlockPosFromNBT(nbt.getCompoundTag("controllerPos"));

			WorldUtils.notifyBlockUpdateAtTile(this);
		}
	}

	public void onDestroy() {
		if(controllerPos != null) {
			TileEntity te = getWorld().getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				if(((TileEntityPortalControllerEntity) te).isActive()) {
					getWorld().createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 2.0F, false);
					((TileEntityPortalControllerEntity) te).closePortal(true);
				}
				((TileEntityPortalControllerEntity) te).setState(TileEntityPortalControllerEntity.State.NO_MULTIBLOCK);
			}
		}
	}

	@Override
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
		WorldUtils.notifyBlockUpdateAtTile(this);
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	public TileEntityPortalControllerEntity.State getControllerState() {
		if(controllerPos != null) {
			TileEntity te = getWorld().getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).getState();
			}
		}
		return TileEntityPortalControllerEntity.State.NO_MULTIBLOCK;
	}

	@Override
	public PortalFrameState getPortalFrameState() {
		if(controllerPos != null) {
			TileEntity te = getWorld().getTileEntity(controllerPos);
			if(te != null && te instanceof TileEntityPortalControllerEntity) {
				return ((TileEntityPortalControllerEntity) te).getPortalFrameState();
			}
		}
		return PortalFrameState.DISCONNECTED;
	}
}
