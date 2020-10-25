package pw.vodes.xdccdl.irc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

import org.pircbotx.Configuration;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class Config {
	
	public static Configuration getConfigForServer(Server serv) throws Exception {
		Configuration.Builder toReturn = new Configuration.Builder()				
				.setName("XDL-" + new Random().nextInt(999999))
				.setRealName("XDCC-AutoDL (v" + XDCCDL.getInstance().version + ") by Vodes")
				.setLogin("XDL")
				.setDccPublicAddress(InetAddress.getByName(getIP()))
				.setDccFilenameQuotes(false)
				.addListener(new BotListener(serv));
		toReturn.addServer(serv.getIp());
		if(serv.getChannels().contains(",")) {
			for(String s : serv.getChannels().split(",")) {
				toReturn.addAutoJoinChannel(s);
			}
		} else {
			toReturn.addAutoJoinChannel(serv.getChannels());
		}
		return toReturn.buildConfiguration();
	}
	
	private static String getIP() throws Exception {
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
