package pw.vodes.xdccdl;

import java.io.File;

import org.pircbotx.PircBotX;

import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.download.DownloadAbleManager;
import pw.vodes.xdccdl.irc.DccThread;
import pw.vodes.xdccdl.irc.IrcStartThread;
import pw.vodes.xdccdl.option.OptionManager;
import pw.vodes.xdccdl.option.types.OptionString;
import pw.vodes.xdccdl.ui.WindowMain;
import pw.vodes.xdccdl.util.ThemeUtil;
import pw.vodes.xdccdl.util.VersionChecker;

public class XDCCDL {
	
	private static XDCCDL instance;
	
	public static XDCCDL getInstance() {
		if(instance == null) {
			instance = new XDCCDL();
		}
		return instance;
	}
	
	public File directory;
	public OptionManager optionManager;
	public DownloadAbleManager dlaManager;
	public WindowMain window;
	public PircBotX bot;
	public DccThread dccT;
	public final double version = 1.0;
	
	public void init() {
		directory = new File(System.getProperty("user.home"), "Vodes" + File.separator + "XDCC-DL");
		if(!directory.exists()) {
			directory.mkdirs();
		}
		optionManager = new OptionManager();
		addOptions();
		optionManager.loadOptions();
		dlaManager = new DownloadAbleManager();
		dlaManager.init();
		window = new WindowMain();
		window.frmXdccautodl.setVisible(true);
		window.frmXdccautodl.toFront();
		new IrcStartThread().start();
		dccT = new DccThread();
		dccT.start();
		VersionChecker.checkVersion();
	}
	
	private void addOptions() {
		XDCCDL.getInstance().optionManager.options.add(new OptionString("Download-Path", "Please choose a path."));
	}

}
