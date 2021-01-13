package pw.vodes.xdccdl;

import java.awt.Desktop;
import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import pw.vodes.xdccdl.download.DownloadAbleManager;
import pw.vodes.xdccdl.irc.IRCBot;
import pw.vodes.xdccdl.irc.IrcCheckerThread;
import pw.vodes.xdccdl.irc.IrcStartThread;
import pw.vodes.xdccdl.option.OptionManager;
import pw.vodes.xdccdl.option.types.OptionBoolean;
import pw.vodes.xdccdl.option.types.OptionString;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.server.ServerManager;
import pw.vodes.xdccdl.ui.TrayIconUtil;
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
	public List<String> wordlist = new ArrayList<String>();
	public List<String> logs = new ArrayList<String>();
	public DownloadThreadQueue threadQueue = new DownloadThreadQueue();
	public TrayIcon tray;
	public String defaultDownloadPath;
	public final double version = 1.8;
	
	public void init() {
		directory = new File(System.getProperty("user.home"), "Vodes" + File.separator + "XDCC-DL");
		File defaultDlDir = new File(directory.getAbsolutePath(), "Downloads");
		defaultDlDir.mkdirs();
		defaultDownloadPath = defaultDlDir.getAbsolutePath();

		threadQueue.start();
		optionManager = new OptionManager();
		addOptions();
		optionManager.loadOptions();
		dlaManager = new DownloadAbleManager();
		dlaManager.init();
		serverManager = new ServerManager();
		serverManager.init();
		if(Desktop.isDesktopSupported()) {
			window = new WindowMain();
			window.frmXdccautodl.setVisible(true);
			window.frmXdccautodl.toFront();
			window.updateLog();
			tray = new TrayIconUtil().create();
		} else {
			Sys.out("Launching in CLI Mode");
			Sys.out("Check Configuration at " + directory.getAbsolutePath());
		}
		VersionChecker.checkVersion();
		Sys.out("Getting wordlist...");
		Sys.out("(It might take some time)");
		wordlist = Sys.readWordlist("https://raw.githubusercontent.com/openethereum/wordlist/master/res/wordlist.txt");
		Sys.out("Wordlist Size: " + wordlist.size());
		for(Server serv: serverManager.getServers()) {
			new IrcStartThread(serv).start();
		}
		ircCheck = new IrcCheckerThread();
		ircCheck.start();
	}
	
	private void addOptions() {
		XDCCDL.getInstance().optionManager.options.add(new OptionBoolean("Use-xdccJS", false));
	}
	
	public String getRandomName(boolean client) {
		Random r = new Random();
		if(wordlist != null && !wordlist.isEmpty()) {
			String word1 = wordlist.get(r.nextInt(wordlist.size())).trim();
			String word2 = wordlist.get(r.nextInt(wordlist.size())).trim();
			word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1).toLowerCase();
			word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1).toLowerCase();
			return word1 + word2 + r.nextInt(9999);
		} else {
			return client ? "XdlClient-" + r.nextInt(99999) : "XDL-" + r.nextInt(9999999);
		}
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
