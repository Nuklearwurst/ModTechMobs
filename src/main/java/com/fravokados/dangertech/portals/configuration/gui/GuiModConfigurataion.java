package com.fravokados.dangertech.portals.configuration.gui;

import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiModConfigurataion extends GuiConfig {

	public GuiModConfigurataion(GuiScreen parent) {
		super(parent,
				getConfigElements(),
				Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModMiningDimension.config.config.toString()));
	}

    @SuppressWarnings({ "rawtypes" })
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new ConfigElement(ModMiningDimension.config.config.getCategory(Configuration.CATEGORY_GENERAL)));
        return list;
    }


}
