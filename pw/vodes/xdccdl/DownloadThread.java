package pw.vodes.xdccdl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import pw.vodes.xdccdl.util.CommandLineUtil;
import pw.vodes.xdccdl.util.OS;
import pw.vodes.xdccdl.util.Sys;

public class DownloadThread extends Thread {
		
	private String botname, channel, server, pack;
	
	public DownloadThread(String botname, String pack, String channel, String server) {
		this.botname = botname;
		this.channel = channel;
		this.server = server;
		this.pack = pack;
	}
	
	@Override
	public void run() {
		String downloadPath = "\"" + XDCCDL.getInstance().optionManager.getString("Download-Path") + "\"";
		String command = String.format("xdcc --server %s --channel %s --nickname %s %s send %s",
				server,
				"'" + (channel.startsWith("#") ? channel : "#" + channel) + "'",
				"XdlClient-" + new Random().nextInt(99999),
				"'" + botname + "'",
				"'" + pack + "'");
		Sys.out(command);
		try {
			ArrayList<String> commands = new ArrayList<>();
			commands.add(command);
			CommandLineUtil.runCommand(commands);
		} catch (Exception e) {
			Sys.out("Thread related Error in the DownloadThread! (See the logs)", "Error");
			e.printStackTrace();
		}
		super.run();
	}

}
