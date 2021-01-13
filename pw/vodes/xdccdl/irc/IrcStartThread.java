package pw.vodes.xdccdl.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class IrcStartThread extends Thread {

	private Server serv;

	public IrcStartThread(Server serv) {
		this.serv = serv;
	}

	@Override
	public void run() {
		IRCBot bot;
		try {
			bot = new IRCBot(serv);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		try {
			XDCCDL.getInstance().bots.add(bot);
			bot.connect(serv.getIp());
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.run();
	}


}
