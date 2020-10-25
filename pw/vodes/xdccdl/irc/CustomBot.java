package pw.vodes.xdccdl.irc;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import pw.vodes.xdccdl.server.Server;

public class CustomBot extends PircBotX {
	
	private Server serv;

	public CustomBot(Configuration configuration, Server serv) {
		super(configuration);
		this.serv = serv;
	}
	
	public Server getServ() {
		return serv;
	}

}
