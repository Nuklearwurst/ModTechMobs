package com.fravokados.dangertech.portals.lib;

import com.fravokados.dangertech.core.lib.util.GeneralUtils;

/**
 * @author Nuklearwurst
 */
public class Strings {

	public static final String CREATIVE_TAB = "mindim";

	public static final class Block {
		public static final String portalMachineBase = "portal_component";
		public static final String portalMachineController = "portal_controller";
		public static final String portalMachineFrame = "portal_frame";

		public static final String portal = "mindim_portal";
	}

	public static final class Item {
		public static final String destinationCard = "destination_card";
		public static final String destinationCardMinDim = "destination_card_mindim";
		public static final String debugger = "debugger";
		public static final String upgrade = "upgrade";

		public static final String upgradeReverse = "upgrade_reverse";
		public static final String upgradeDisconnect = "upgrade_disconnect";
	}

	public static final class TileEntity {

		public static final String TILE_ENTITY_PORTAL_CONTROLLER_ENTITY = "TileEntityPortalControllerEntity";
		public static final String TILE_ENTITY_PORTAL = "TileEntityPortal";
		public static final String TILE_ENTITY_PORTAL_FRAME = "TileEntityPortalFrame";
	}

	public static final class Chat {
		public static final String BLOCK_PLACING_CANCELLED = "chat.mindim.cancelBlockPlacing";
	}

	public static final class Gui {
		public static final String DESTINATION_CARD_PORTAL_BLOCKS_STORED = "gui.mindim.destinationCard.portalBlocksStored";

		public static final String CONTROLLER_NAME = "gui.mindim.controller.name";
		public static final String CONTROLLER_NAME_UNNAMED = "gui.mindim.controller.name.unnamed";
		public static final String CONTROLLER_DESTINATION = "gui.mindim.controller.destination";
		public static final String CONTROLLER_STATE = "gui.mindim.controller.state";
		public static final String CONTROLLER_ERROR = "gui.mindim.controller.error";
		public static final String CONTROLLER_EDIT_NAME_HOVER = "gui.mindim.controller.edit.name";

		public static final String CONTROLLER_DESTINATION_UNKNOWN = "gui.mindim.controller.destination.unknown";
		public static final String CONTROLLER_DESTINATION_MINDIM = "gui.mindim.controller.destination.mindim";
		public static final String CONTROLLER_DESTINATION_ERROR = "gui.mindim.controller.destination.error";
		public static final String CONTROLLER_DESTINATION_NONE = "gui.mindim.controller.destination.none";

		public static final String GUI_ADD = "gui.add";
		public static final String GUI_START = "gui.start";
		public static final String GUI_STOP = "gui.stop";

		public static final String CONTROLLER_ERROR_MSG_SHORT_BASE = "gui.error.short.";
		public static final String CONTROLLER_ERROR_MSG_DETAIL_BASE = "gui.error.detail.";
		public static final String[] CONTROLLER_ERROR_MSG = new String[] {"noError", "invalidDestination", "invalidStructure", "connectionInterrupted", "powerFailure", "destinationChanged", "missingResources", "unknown"};
		public static final String CONTROLLER_STATE_MSG_SHORT_BASE = "gui.controller.state.short.";
		public static final String CONTROLLER_STATE_MSG_DETAIL_BASE = "gui.controller.state.detail.";
		public static final String[] CONTROLLER_STATE_MSG = {"noMultiBlock", "ready", "connecting", "outgoingPortal", "incomingConnection", "incomingPortal"};
	}

	public static final class Tooltip {
		public static final String ITEM_DESTINATION_CARD_TYPE = "tooltip.item.destinationCard.type";
		public static final String ITEM_DESTINATION_CARD_DESTINATION = "tooltip.item.destinationCard.destination";
		public static final String ITEM_DESTINATION_CARD_DIMENSION = "tooltip.item.destinationCard.dimension";
		public static final String ITEM_DESTINATION_CARD_EMPTY = "tooltip.item.destinationCard.empty";
		public static final String ITEM_DESTINATION_CARD_MINDIM = "tooltip.item.destinationCard.mindim";
		public static final String ITEM_DESTINATION_CARD_MINDIM_INFO_1 = "tooltip.item.destinationCard.mindim.info_1";
		public static final String ITEM_DESTINATION_CARD_MINDIM_ENERGY_TYPE = "tooltip.item.destinationCard.mindim.energy_type";

		public static final String NAME = "tooltip.name";
		public static final String CONTROLLER_UPGRADES_INSTALLED = "tooltip.controller.upgrades.installed";

		public static final String UNKNOWN_DESTINATION = "tooltip.unknown_destination";
	}

	public static final class Sounds {
		public static final String PORTAL_OPEN = "portal.travel";
		public static final String PORTAL_CONNECT = "portal.portal";
		public static final String PORTAL_CLOSE = "portal.trigger";
	}

	/**
	 * Configuration keys
	 */
	public static final class Keys {
		public static final class Debug {
			public static final String DEBUG = "debug_deobf";
			public static final String DEBUG_TESTING = "debug_testing";
		}
		public static final class General {
			public static final String PORTAL_SPAWN_WITH_CARD = "portal_spawns_with_card";
			public static final String PORTAL_MAX_CONNECTION_LENGTH = "portal_max_connection_length";
			public static final String PORTAL_LOAD_CHUNKS = "portal_loads_destination_chunk";
		}
	}

	public static String translate(String key) {
		return GeneralUtils.translate(key);
	}

	public static String translateWithFormat(String key, Object... values) {
		return GeneralUtils.translateWithFormat(key, values);
	}
}
