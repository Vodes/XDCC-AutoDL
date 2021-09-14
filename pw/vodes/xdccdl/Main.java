package pw.vodes.xdccdl;

import pw.vodes.xdccdl.util.ShutdownThread;
import pw.vodes.xdccdl.util.ThemeUtil;

public class Main {
	
	public static boolean forceCLI = false;
	
	public static void main(String[] args) {
		for(String s : args) {
			if(s.equalsIgnoreCase("--cli")) {
				forceCLI = true;
			}
		}
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		XDCCDL.getInstance().init();
	}

}
