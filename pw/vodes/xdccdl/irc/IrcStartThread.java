package pw.vodes.xdccdl.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import pw.vodes.xdccdl.XDCCDL;

public class IrcStartThread extends Thread {
	
	@Override
	public void run() {
		try {
			XDCCDL.getInstance().bot = new PircBotX(getConfig());
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		try {
			XDCCDL.getInstance().bot.startBot();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
		super.run();
	}
	
	public static Configuration getConfig() throws Exception {
		return new Configuration.Builder()
				.setName("XDL-" + new Random().nextInt(999999))
				.setRealName("XDCC-AutoDL by Vodes")
				.addServer("irc.rizon.net")
				.addAutoJoinChannel("#HorribleSubs")
				.addAutoJoinChannel("##XDL-Testing")
				.setLogin("XDL")
				.setDccPublicAddress(InetAddress.getByName(getIP()))
				.addListener(new BotListener())
				.buildConfiguration();
	}
	
	private static String getIP() throws Exception {
		URL u = new URL("http://icanhazip.com");
		BufferedReader connection = new BufferedReader(new InputStreamReader(u.openStream()));
		String line; 
		while ((line = connection.readLine()) != null) {
			return line;
		}
		connection.close();
		return "127.0.0.1";
	}

}
