package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the Setting type for a list of strings. It uses the Properties class
 * for ease of use. The widget is NOT complete yet since there's no theme for
 * ListBox.
 * 
 * @author ShaRose
 */
public class SettingList extends Setting<Properties> {

	public SettingList(String title) {
		this(title, new Properties());
	}

	public SettingList(String title, Properties defaultvalue) {
		backendName = title;
		defaultValue = defaultvalue;
		values.put("", defaultvalue);
	}

	@Override
	public void fromString(String s, String context) {
		Properties prop = new Properties();
		try {
			prop.loadFromXML(new ByteArrayInputStream(s.getBytes("UTF-8")));
		} catch (Throwable e) {
			ModSettings.dbgout("Error reading SettingList from context '"
					+ context + "': " + e);
		}
		values.put(context, prop);
		if (displayWidget != null) {
			displayWidget.update();
		}
	}

	@Override
	public Properties get(String context) {
		if (values.get(context) != null) {
			return values.get(context);
		} else if (values.get("") != null) {
			return values.get("");
		} else {
			return defaultValue;
		}
	}

	@Override
	public void set(Properties v, String context) {
		values.put(context, v);
		if (parent != null) {
			parent.save(context);
		}
		if (displayWidget != null) {
			displayWidget.update();
		}
	}

	@Override
	public String toString(String context) {
		try {
			Properties prop = get(context);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			prop.storeToXML(output, "GuiAPI SettingList: DO NOT EDIT.");
			return output.toString("UTF-8");
		} catch (IOException e) {
			ModSettings.dbgout("Error writing SettingList from context '"
					+ context + "': " + e);
			return "";
		}
	}

}
