package com.fravokados.dangertech.core.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public interface IModelProvider {

	void registerModels();
}
