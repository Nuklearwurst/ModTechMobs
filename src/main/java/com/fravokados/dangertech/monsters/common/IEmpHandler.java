package com.fravokados.dangertech.monsters.common;

import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public interface IEmpHandler {
	void handleEMP(World world, double x, double y, double z, float strength, int radius, float factor);
}
