package com.fravokados.dangertech.portals.plugin;

import com.fravokados.dangertech.portals.client.ClientPortalInfo;

/**
 * @author Nuklearwurst
 */
public class PluginLookingGlass {
	public static final String MOD_ID = "LookingGlass";

	private static boolean apiLoaded = false;

	public static boolean isAvailable() {
		return apiLoaded;
	}

//	public static void register(APIInstanceProvider provider) {
//		try {
//			Object apiinst = provider.getAPIInstance("view-2");
//			apiLoaded = PluginLookingGlassImpl.load(apiinst); //At this point, we've got an object of the right interface.
//		} catch (APIUndefined e) {
//			// The API we requested doesn't exist.  Give up with a nice log message.
//			LogHelperMD.info("LookinGlass API not found...");
//		} catch (APIVersionUndefined e) {
//			// The API we requested exists, but the version we wanted is missing in the local environment. We can try falling back to an older version.
//			LogHelperMD.info("Found an older version of LookingGlass, consider updating...");
//			LogHelperMD.info("Disabling LookingGlass integration!");
//		} catch (APIVersionRemoved e) {
//			// The API we requested exists, but the version we wanted has been removed and is no longer supported. Better update.
//			LogHelperMD.error("LookingGlass seems to be available, but has removed needed functionality!");
//			LogHelperMD.error("Please check for a newer version of " + Reference.MOD_NAME);
//		}
//	}

	public static void createPortalView(ClientPortalInfo info) {

	}
}
