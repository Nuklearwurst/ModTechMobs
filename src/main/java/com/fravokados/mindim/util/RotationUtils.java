package com.fravokados.mindim.util;

import com.fravokados.mindim.block.IFacingSix;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Nuklearwurst
 */
public class RotationUtils {

	public static void updateFacing(IFacingSix te, EntityLivingBase player, int x, int y, int z) {
		//rotate block
		if (player != null)
		{
			int rotationSegment = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			if (player.rotationPitch >= 65)
			{
				te.setFacing((byte)1);
			}
			else if (player.rotationPitch <= -65)
			{
				te.setFacing((byte)0);
			}
			else
			{
				switch (rotationSegment)
				{
					case 0: te.setFacing((byte) 2); break;
					case 1: te.setFacing((byte) 5); break;
					case 2: te.setFacing((byte) 3); break;
					case 3: te.setFacing((byte) 4); break;
					default:
						te.setFacing((byte) 0); break;
				}
			}
		}
	}

	public static double getOffsetXForRotation(double x, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t, ForgeDirection rot_3_o, ForgeDirection rot_3_t) {
		return x * getTransformationDirection(rot_1_o, rot_1_t).offsetX + x * getTransformationDirection(rot_2_o, rot_2_t).offsetX + x * getTransformationDirection(rot_3_o, rot_3_t).offsetX;
	}

	public static double getOffsetYForRotation(double y, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t, ForgeDirection rot_3_o, ForgeDirection rot_3_t) {
		return y * getTransformationDirection(rot_1_o, rot_1_t).offsetY + y * getTransformationDirection(rot_2_o, rot_2_t).offsetY + y * getTransformationDirection(rot_3_o, rot_3_t).offsetY;
	}

	public static double getOffsetZForRotation(double z, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t, ForgeDirection rot_3_o, ForgeDirection rot_3_t) {
		return z * getTransformationDirection(rot_1_o, rot_1_t).offsetZ + z * getTransformationDirection(rot_2_o, rot_2_t).offsetZ + z * getTransformationDirection(rot_3_o, rot_3_t).offsetZ;
	}

	public static double getOffsetXForRotation(double x, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t) {
		return getOffsetXForRotation(x, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.getRotation(rot_2_o), rot_1_t.getRotation(rot_2_t));
	}

	public static double getOffsetYForRotation(double y, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t) {
		return getOffsetYForRotation(y, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.getRotation(rot_2_o), rot_1_t.getRotation(rot_2_t));
	}

	public static double getOffsetZForRotation(double z, ForgeDirection rot_1_o, ForgeDirection rot_1_t, ForgeDirection rot_2_o, ForgeDirection rot_2_t) {
		return getOffsetZForRotation(z, rot_1_o, rot_1_t, rot_2_o, rot_2_t, rot_1_o.getRotation(rot_2_o), rot_1_t.getRotation(rot_2_t));
	}

	public static ForgeDirection getTransformationDirection(ForgeDirection a1, ForgeDirection a2) {
		return ForgeDirection.getOrientation(getTransformationDirection(a1.ordinal(), a2.ordinal()));
	}

	public static int getTransformationDirection(int a1, int a2) {
		int[][] matrix = {
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
				{1, 0, 3, 2, 5, 4},
				{0, 1, 2, 3, 4, 5},
		};
		return matrix[a1][a2];
	}

	public static float get2DRotation(ForgeDirection from, ForgeDirection to) {
		if(from == to) {
			return 0;
		} else if(from.getOpposite() == to) {
			return 180;
		} else {
			switch (from) {
				case SOUTH:
					return to == ForgeDirection.WEST ? 90 : -90;
				case WEST:
					return to == ForgeDirection.NORTH ? 90 : -90;
				case NORTH:
					return to == ForgeDirection.EAST ? 90 : -90;
				case EAST:
					return to == ForgeDirection.SOUTH ? 90 : -90;
			}
		}
		return 0;
	}
}
