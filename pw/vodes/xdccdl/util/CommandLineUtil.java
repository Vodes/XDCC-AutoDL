package pw.vodes.xdccdl.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;

public class CommandLineUtil {

	public static void runCommand(ArrayList<String> commands, DownloadAble dla) {
		Process process;
		ArrayList<String> commandList = new ArrayList<>();
		if (getOS() == OS.Windows) {
			commandList.add("cmd");
			commandList.add("/c");
			commandList.add("\"" + commands.get(0) + "\"");
		} else if (getOS() == OS.Linux) {
			commandList.add("bash");
			commandList.add("-c");
			commandList.addAll(commands);
		} else if (getOS() == OS.Mac) {
			commandList.add("/bin/bash");
			commandList.add("-c");
			commandList.addAll(commands);
		} else {
			return;
		}
		ProcessBuilder processbuilder = new ProcessBuilder(commandList).directory(new File(dla.getDownloadDir()));
		Process p;
		try {
			p = processbuilder.start();
			new OutputRedirector(p.getInputStream()).start();
			new OutputRedirector(p.getErrorStream()).start();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static OS getOS() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return OS.Windows;
		} else if (SystemUtils.IS_OS_LINUX) {
			return OS.Linux;
		} else {
			return OS.Mac;
		}
	}

}
