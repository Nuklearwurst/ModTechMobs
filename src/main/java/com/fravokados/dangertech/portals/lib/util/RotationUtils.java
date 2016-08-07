package com.fravokados.dangertech.portals.lib.util;

import com.fravokados.dangertech.api.block.IFacingSix;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

/**
 * @author Nuklearwurst
 */
public class RotationUtils {

	public static EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase entity) {
		return BlockPistonBase.getFacingFromEntity(pos, entity);
	}

	public static void updateFacing(IFacingSix te, EntityLivingBase player, BlockPos pos) {
		//rotate block
		int rotationSegment = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if (player.rotationPitch >= 65)
		{
			te.setFacing(EnumFacing.UP);
		}
		else if (player.rotationPitch <= -65)
		{
			te.setFacing(EnumFacing.DOWN);
		}
		else
		{
			switch (rotationSegment)
			{
				case 0: te.setFacing(EnumFacing.NORTH); break;
				case 1: te.setFacing(EnumFacing.EAST); break;
				case 2: te.setFacing(EnumFacing.SOUTH); break;
				case 3: te.setFacing(EnumFacing.WEST); break;
				default:
					te.setFacing(EnumFacing.DOWN); break;
			}
		}
	}

	public static double getOffsetXForRotation(double x, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t, EnumFacing rot_3_o, EnumFacing rot_3_t) {
		return x * getTransformationDirection(rot_1_o, rot_1_t).getFrontOffsetX() + x * getTransformationDirection(rot_2_o, rot_2_t).getFrontOffsetX() + x * getTransformationDirection(rot_3_o, rot_3_t).getFrontOffsetX();
	}

	public static double getOffsetYForRotation(double y, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t, EnumFacing rot_3_o, EnumFacing rot_3_t) {
		return y * getTransformationDirection(rot_1_o, rot_1_t).getFrontOffsetY() + y * getTransformationDirection(rot_2_o, rot_2_t).getFrontOffsetY() + y * getTransformationDirection(rot_3_o, rot_3_t).getFrontOffsetY();
	}

	public static double getOffsetZForRotation(double z, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t, EnumFacing rot_3_o, EnumFacing rot_3_t) {
		return z * getTransformationDirection(rot_1_o, rot_1_t).getFrontOffsetZ() + z * getTransformationDirection(rot_2_o, rot_2_t).getFrontOffsetZ() + z * getTransformationDirection(rot_3_o, rot_3_t).getFrontOffsetZ();
	}

	public static double getOffsetXForRotation(double x, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t) {
		return getOffsetXForRotation(x, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.rotateAround(rot_2_o.getAxis()), rot_1_t.rotateAround(rot_2_t.getAxis()));
	}

	public static double getOffsetYForRotation(double y, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t) {
		return getOffsetYForRotation(y, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.rotateAround(rot_2_o.getAxis()), rot_1_t.rotateAround(rot_2_t.getAxis()));
	}

	public static double getOffsetZForRotation(double z, EnumFacing rot_1_o, EnumFacing rot_1_t, EnumFacing rot_2_o, EnumFacing rot_2_t) {
		return getOffsetZForRotation(z, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.rotateAround(rot_2_o.getAxis()), rot_1_t.rotateAround(rot_2_t.getAxis()));
	}

	public static EnumFacing getTransformationDirection(EnumFacing a1, EnumFacing a2) {
		return EnumFacing.getFront(getTransformationDirection(a1.ordinal(), a2.ordinal()));
	}

	public static int getTransformationDirection(int a1, int a2) {
		final int[][] matrix = {
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
		};
		return matrix[a1][a2];
	}

	public static float get2DRotation(EnumFacing from, EnumFacing to) {
		if(from == to) {
			return 0;
		} else if(from.getOpposite() == to) {
			return 180;
		} else {
			switch (from) {
				case SOUTH:
					return to == EnumFacing.WEST ? 90 : -90;
				case WEST:
					return to == EnumFacing.NORTH ? 90 : -90;
				case NORTH:
					return to == EnumFacing.EAST ? 90 : -90;
				case EAST:
					return to == EnumFacing.SOUTH ? 90 : -90;
			}
		}
		return 0;
	}
}
