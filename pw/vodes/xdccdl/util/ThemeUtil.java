package pw.vodes.xdccdl.util;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

public class ThemeUtil {
	
	public static void setTheme() {
		try {
			UIManager.setLookAndFeel(new MaterialLookAndFeel());
			MaterialLookAndFeel.changeTheme(new JMarsDarkTheme());
		} catch (Exception e) {
			e.printStackTrace();
		}
		editThemes();
	}
	
	private static void editThemes() {
		ToolTipManager.sharedInstance().setInitialDelay(100);
		setUIFont(new FontUIResource("Verdana", Font.PLAIN, 13));
		UIManager.put("ScrollBar.width", (int) ((int) UIManager.get("ScrollBar.width") * 0.6));
	}

	@SuppressWarnings("rawtypes")
	private static void setUIFont(FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, f);
		}
	}

}
