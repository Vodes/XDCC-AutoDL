package pw.vodes.xdccdl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.option.OptionManager;
import pw.vodes.xdccdl.util.CommandLineUtil;
import pw.vodes.xdccdl.util.OS;
import pw.vodes.xdccdl.util.Sys;

public class DownloadThread extends Thread {
		
	private String botname, channel, server, pack;
	private DownloadAble dla;
	
	public DownloadThread(String botname, String pack, String channel, String server, DownloadAble dla) {
		this.botname = botname;
		this.channel = channel;
		this.server = server;
		this.pack = pack;
		this.dla = dla;
	}
	
	@Override
	public void run() {
		String downloadPath = "\"" + XDCCDL.getInstance().optionManager.getString("Download-Path") + "\"";
		String command = String.format("xdcc --server %s --channel %s --nickname %s %s send %s",
				server,
				"'" + (channel.startsWith("#") ? channel : "#" + channel) + "'",
				"\"" + XDCCDL.getInstance().getRandomName(true) + "\"",
				"\"" + botname + "\"",
				"'" + pack + "'");
		if(XDCCDL.getInstance().optionManager.getBoolean("Use-xdccJS")) {
			command = String.format("xdccJS -q --host %s --bot %s --download %s --nickname %s --chan %s --path %s --no-randomize"
					, "" + server + "",
					"\"" + botname + "\"",
					"" + pack,
					"\"" + XDCCDL.getInstance().getRandomName(true) + "\"",
					"\"" + (channel.startsWith("#") ? channel : "#" + channel) + "\"",
					"\"" + dla.getDownloadDir() + "\"");
		}
		Sys.out(command);
		try {
			ArrayList<String> commands = new ArrayList<>();
			commands.add(command);
			CommandLineUtil.runCommand(commands, dla);
		} catch (Exception e) {
			Sys.out("Thread related Error in the DownloadThread! (See the logs)", "Error");
			e.printStackTrace();
		}
		if(XDCCDL.getInstance().threadQueue.running == this){
			XDCCDL.getInstance().threadQueue.running = null;
		}
		super.run();
	}

}
