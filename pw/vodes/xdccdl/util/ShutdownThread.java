package pw.vodes.xdccdl.util;

import pw.vodes.xdccdl.XDCCDL;

public class ShutdownThread extends Thread {
	
	@Override
	public void run() {
		XDCCDL.getInstance().optionManager.saveOptions();
		super.run();
	}

}
