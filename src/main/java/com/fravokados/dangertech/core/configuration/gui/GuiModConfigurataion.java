package com.fravokados.dangertech.core.configuration.gui;

import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.Reference;
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
				Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModNwCore.config.config.toString()));
	}

	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<>();
		list.add(new ConfigElement(ModNwCore.config.config.getCategory(Configuration.CATEGORY_GENERAL)));
        return list;
	}
}
