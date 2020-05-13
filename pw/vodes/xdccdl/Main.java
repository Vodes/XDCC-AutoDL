package pw.vodes.xdccdl;

import pw.vodes.xdccdl.util.ShutdownThread;
import pw.vodes.xdccdl.util.ThemeUtil;

public class Main {
	
	public static void main(String[] args) {
		ThemeUtil.setTheme();
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		XDCCDL.getInstance().init();
	}

}
