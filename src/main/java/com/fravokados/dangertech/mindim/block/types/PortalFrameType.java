package com.fravokados.dangertech.mindim.block.types;

import net.minecraft.util.IStringSerializable;

/**
 * @author Nuklearwurst
 */
public enum PortalFrameType implements IStringSerializable {
	BASIC_FRAME("basic_frame"), BASIC_CONTROLLER("basic_controller");
//	ITEM_PORTAL("item_portal"), FLUID_PORTAL("fluid_portal"), ENERGY_PORTAL("energy_portal"),
//	FRAME_MINECART("minecart_frame"), CONTROLLER_MINECART("minecart_controller");

	private String name;

	PortalFrameType(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getID() {
		return ordinal();
	}

	public static PortalFrameType forID(int id) {
		if(id > values().length) {
			id = 0;
		}
		return values()[id];
	}
}
