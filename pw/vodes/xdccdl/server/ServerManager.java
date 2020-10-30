package pw.vodes.xdccdl.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.irc.IRCBot;
import pw.vodes.xdccdl.irc.IrcStartThread;
import pw.vodes.xdccdl.util.FileUtil;

public class ServerManager {
	
	private CopyOnWriteArrayList<Server> servers = new CopyOnWriteArrayList<>();
	private File serverFile = new File(XDCCDL.getInstance().directory, "servers.txt");
	
	public void init() {
		if(!serverFile.exists()) {
			try {
				serverFile.createNewFile();
				addDefault();
			} catch (IOException e) {}
			addDefault();
			return;
		}
		List<String> fileReadout = FileUtil.readFileToLines(serverFile);
		if(fileReadout.isEmpty()) {
			this.addDefault();
			return;
		}
		for(String s : fileReadout) {
			if(!s.contains(";;")) {
				continue;
			}
			String[] parts = s.split(";;");
			servers.add(new Server(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
		}
	}
	
	private void addDefault() {
		getServers().add(new Server("Rizon - HorribleSubs", "irc.rizon.net", "#HorribleSubs", true));
	}
	
	public void save() {
		List<String> lines = new ArrayList<>();
		for(Server serv : getServers()) {
			lines.add(serv.getName() + ";;" + serv.getIp() + ";;" + serv.getChannels() + ";;" + serv.isEnabled());
		}
		if(!lines.isEmpty()) {
			FileUtil.writeLinesToFile(serverFile, lines, false);
		}
	}
	
	public CopyOnWriteArrayList<Server> getServers() {
		return servers;
	}
	
	public void addServer(Server serv) {
		if(getServers().isEmpty()) {
			this.getServers().add(serv);
			this.save();
			new IrcStartThread(serv).start();
		}

	}
	
	public void replaceServer(Server toReplace, Server replacement) {
		this.getServers().set(this.getServers().indexOf(toReplace), replacement);
		for(IRCBot bot : XDCCDL.getInstance().bots) {
			if(bot != null && bot.getServ() == toReplace) {
				if(bot.isConnected()) {
					bot.disconnect();
					try {
						bot.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				new IrcStartThread(replacement).start();
				break;
			}
		}
		this.save();
	}
	
	public void removeServer(Server serv) {
		if(getServers().size() < 2) {
			return;
		}
		this.getServers().remove(serv);
		this.save();
		for(IRCBot bot : XDCCDL.getInstance().bots) {
			if(bot != null && bot.getServ() == serv) {
				if(bot.isConnected()) {
					bot.disconnect();
					try {
						bot.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				XDCCDL.getInstance().bots.remove(bot);
				break;
			}
		}
	}

}
