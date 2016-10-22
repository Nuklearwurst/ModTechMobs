package com.fravokados.dangertech.portals.block.types;

import net.minecraft.util.IStringSerializable;

/**
 * @author Nuklearwurst
 */
public enum PortalFrameState implements IStringSerializable {
	ACTIVE("active"), CONNECTING("connecting"), DISABLED("disabled"), DISCONNECTED("disconnected");

	private String name;

	PortalFrameState(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getID() {
		return ordinal();
	}

	public static PortalFrameState forID(int id) {
		if(id > values().length) {
			id = 0;
		}
		return values()[id];
	}
}