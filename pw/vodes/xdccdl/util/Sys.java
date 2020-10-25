package pw.vodes.xdccdl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import pw.vodes.xdccdl.XDCCDL;

public class Sys {
	
	private static String debug = "[INFO] ";
	private static String error = "[ERROR] ";
	private static String warn = "[WARN] ";
	
	public static void out(String message, String type, boolean timestamP) {
		String prefix = "";
		String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		
		if(type.equalsIgnoreCase("debug") || type.equalsIgnoreCase("info") || type.equalsIgnoreCase("infos")) {
			prefix = debug;
		} else if (type.equalsIgnoreCase("error")) {
			prefix = error;
		} else if (type.equalsIgnoreCase("warn")) {
			prefix = warn;
		} else {
			prefix = "[" + type.toUpperCase() + "]";
		}
		System.out.println((timestamP ? "[" + timestamp + "] " : "") + prefix + message);
		if(XDCCDL.getInstance().window != null && XDCCDL.getInstance().window.textPane != null) {
			XDCCDL.getInstance().window.textPane.setText(XDCCDL.getInstance().window.textPane.getText() + (timestamP ? "[" + timestamp + "] " : "") + prefix + message + "\n");
			XDCCDL.getInstance().window.textPane.setCaretPosition(XDCCDL.getInstance().window.textPane.getDocument().getLength() - 1);
		}
	}
	
	public static void out(String message, String type) {
		out(message, type, true);
	}
	
	public static void out(String message) {
		out(message, "info", true);
	}

}