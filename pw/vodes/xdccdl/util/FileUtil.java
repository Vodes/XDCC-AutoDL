package pw.vodes.xdccdl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static void writeFile(File f, String s, boolean append) {
		if (!append && f.exists()) {
			f.delete();
		}
		try {
			if (!f.exists()) {
				f.createNewFile();
				f.setReadable(true, false);
			}
			FileWriter fw = new FileWriter(f, append);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(s);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLinesToFile(File f, List<String> s, boolean append) {
		if (!append && f.exists()) {
			f.delete();
		}
		try {
			if (!f.exists()) {
				f.createNewFile();
				f.setReadable(true, false);
			}
			FileWriter fw = new FileWriter(f, append);
			BufferedWriter writer = new BufferedWriter(fw);
			for(String st : s) {
				writer.write(st);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readURLToLine(String url) {
		List<String> lines = readURLToLines(url);
		String s = "";
		for(String st : lines) {
			s += st + "\n";
		}
		return s;
	}
	
	public static String readFileToLine(File f) {
		List<String> lines = readFileToLines(f);
		String s = "";
		for(String st : lines) {
			s += st + "\n";
		}
		return s;
	}
	
	public static List<String> readFileToLines(File f) {
		final List<String> lines = new ArrayList<String>();
		if(!f.exists()) {
			return lines;
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (!str.startsWith("#") && !str.startsWith("//")) {
					lines.add(str);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static List<String> readURLToLines(String url) {
		final List<String> lines = new ArrayList<String>();
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (!str.startsWith("#") && !str.startsWith("//")) {
					lines.add(str);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void deleteLineFromFile(String line, File f) {
		if(!f.exists()) {
			return;
		}
		List<String> lines = readFileToLines(f);
		if(lines.isEmpty()) {
			return;
		}
		for(String s : lines) {
			if(s.equalsIgnoreCase(line)) {
				lines.remove(s);
			}
		}
		
		writeLinesToFile(f, lines, false);
	}
	
}
