package com.fravokados.dangertech.api.upgrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to read upgrade stats<br/>
 * Not used to store upgrade effects!
 *
 * @author Nuklearwurst
 */
public class UpgradeStatCollection {

	private Map<String, Object> upgrades = new HashMap<String, Object>();

	public UpgradeStatCollection() {

	}

	public Object getValue(String s) {
		return upgrades.get(s);
	}

	public void setValue(String s, Object value) {
		upgrades.put(s, value);
	}

	public float getFloat(String s, float def) {
		Object o = getValue(s);
		if(o == null || !(o instanceof Float)) {
			return def;
		}
		return (Float) o;
	}

	public int getInt(String s, int def) {
		Object o = getValue(s);
		if(o == null || !(o instanceof Integer)) {
			return def;
		}
		return (Integer) o;
	}

	public void setFloat(String s, float value) {
		setValue(s, value);
	}

	public void setInt(String s, int value) {
		setValue(s, value);
	}

	public void addInt(String s, int value) {
		setInt(s, getInt(s, 0) + value);
	}

	public void multiplyFloat(String s, float value) {
		setFloat(s, getFloat(s, 1.0F) * value);
	}

	public void addFloat(String s, float value) {
		setFloat(s, getFloat(s, 0) + value);
	}

	public void setBoolean(String s, boolean b) {
		setValue(s, b);
	}

	public void addUpgrade(String s) {
		setBoolean(s, true);
	}

	public boolean getBoolean(String s, boolean def) {
		Object o = getValue(s);
		if(o == null || !(o instanceof Boolean)) {
			return def;
		}
		return (Boolean) o;
	}

	public boolean getBoolean(String s) {
		return getBoolean(s, false);
	}

	public boolean hasKey(String s) {
		return upgrades.containsKey(s);
	}

	public boolean hasKey(UpgradeTypes type) {
		return hasKey(type.id);
	}

	public static UpgradeStatCollection getUpgradeStatsFromDefinitions(List<IUpgradeDefinition> upgrades) {
		UpgradeStatCollection col = new UpgradeStatCollection();
		for(IUpgradeDefinition upgrade : upgrades) {
			upgrade.applyTo(col);
		}
		return col;
	}
}
