package pw.vodes.xdccdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

import pw.vodes.xdccdl.download.DownloadAbleManager;
import pw.vodes.xdccdl.irc.IRCBot;
import pw.vodes.xdccdl.irc.IrcCheckerThread;
import pw.vodes.xdccdl.irc.IrcStartThread;
import pw.vodes.xdccdl.option.OptionManager;
import pw.vodes.xdccdl.option.types.OptionString;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.server.ServerManager;
import pw.vodes.xdccdl.ui.WindowMain;
import pw.vodes.xdccdl.util.Sys;
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
	public File tempdirectory;
	public OptionManager optionManager;
	public DownloadAbleManager dlaManager;
	public ServerManager serverManager;
	public WindowMain window;
	public CopyOnWriteArrayList<IRCBot> bots = new CopyOnWriteArrayList<>();
	public InetAddress inet;
	public IrcCheckerThread ircCheck;
	public final double version = 1.3;
	
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
		serverManager = new ServerManager();
		serverManager.init();
		window = new WindowMain();
		window.frmXdccautodl.setVisible(true);
		window.frmXdccautodl.toFront();
		VersionChecker.checkVersion();
		
		try {
			inet = InetAddress.getByName(getIP());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Server serv: serverManager.getServers()) {
			new IrcStartThread(serv).start();
		}
		
		ircCheck = new IrcCheckerThread();
		ircCheck.start();
	}
	
	private void addOptions() {
		XDCCDL.getInstance().optionManager.options.add(new OptionString("Download-Path", "Please choose a path."));
	}
	
	
	private String getIP() throws Exception {
		URL u = new URL("http://icanhazip.com");
		BufferedReader connection = new BufferedReader(new InputStreamReader(u.openStream()));
		String line; 
		while ((line = connection.readLine()) != null) {
			Sys.out("Your IP is: **.**" + line.substring(5));
			return line;
		}
		connection.close();
		return "127.0.0.1";
	}

}
