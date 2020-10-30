package pw.vodes.xdccdl.irc;

import pw.vodes.xdccdl.XDCCDL;

public class IrcCheckerThread extends Thread {
	
	@Override
	public void run() {
		try {
			this.sleep(20000);
		} catch (Exception e) {}
		while (true) {
			try {
				for(IRCBot bot : XDCCDL.getInstance().bots) {
					if(!bot.isConnected()) {
						bot.connect(bot.getServ().getIp());
						break;
					}
				}
			} catch (Exception e) {}
			try {
				this.sleep(5000);
			} catch (Exception e) {}
		}
	}

}
