package com.fravokados.techmobs.lib.util;

import com.google.common.collect.Lists;

import java.util.List;

public class CommandUtils {
	
	/**
	 * inspired by OpenModsLib of the OpenMods-Team <br>
	 * <a href="https://github.com/OpenMods/OpenModsLib/">https://github.com/OpenMods/OpenModsLib/</a>
	 */
	public static List<String> filterCompletion(String prefix, List<String> completionList) {
		if (prefix == null || prefix.isEmpty() || prefix.equals(" ")) {
			return Lists.newArrayList(completionList);
		}
		prefix = prefix.toLowerCase();
		List<String> out = Lists.newArrayList();
		for(String obj : completionList) {
			if (obj.toLowerCase().startsWith(prefix)) {
				out.add(obj);
			}
		}
		return out;
	}

}
