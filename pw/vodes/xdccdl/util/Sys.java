package pw.vodes.xdccdl.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pw.vodes.xdccdl.XDCCDL;

public class Sys {
	
	private static String debug = "";
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
		String output = (timestamP ? "[" + timestamp + "] " : "") + prefix + message;
		System.out.println(output);
		XDCCDL.getInstance().logs.add(output);
		if(XDCCDL.getInstance().window != null && XDCCDL.getInstance().window.textPane != null) {
			XDCCDL.getInstance().window.updateLog();
		}
	}
	
	public static void out(String message, String type) {
		out(message, type, true);
	}
	
	public static void out(String message) {
		out(message, "info", true);
	}
	
	public static List<String> readWordlist(String url) {
		final List<String> lines = new ArrayList<String>();
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (!str.startsWith("#") && !str.startsWith("//") && str.length() < 7) {
					lines.add(str);
				}
			}
			in.close();
		} catch (Exception e) {
			//ignore
		}
		return lines;
	}

}