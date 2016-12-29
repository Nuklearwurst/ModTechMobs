package com.fravokados.dangertech.core.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
public interface IModelProvider {

	@SideOnly(Side.CLIENT)
	void registerModels();
}
