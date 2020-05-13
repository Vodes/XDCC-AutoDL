package pw.vodes.xdccdl.util;

import pw.vodes.xdccdl.XDCCDL;

public class VersionChecker {
	
	public static void checkVersion() {
		double onlineVersion = Double.parseDouble(FileUtil.readURLToLine("https://raw.githubusercontent.com/Vodes/XDCC-AutoDL/master/version.txt"));
		if(XDCCDL.getInstance().version < onlineVersion) {
			Sys.out("New Update available.", "WARN");
			Sys.out("Please go to https://github.com/Vodes/XDCC-AutoDL/");
			XDCCDL.getInstance().window.frmXdccautodl.setTitle(XDCCDL.getInstance().window.frmXdccautodl.getTitle() + " [OUTDATED]");
		}
	}

}
