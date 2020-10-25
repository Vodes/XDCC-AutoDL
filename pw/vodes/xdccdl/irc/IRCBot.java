package pw.vodes.xdccdl.irc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.pircbotx.dcc.ReceiveFileTransfer;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.io.Files;

import pw.vodes.xdccdl.DownloadThread;
import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class IRCBot extends PircBot {
	
	private Server serv;
	private int lastNumber = -1;
	
	public IRCBot(Server serv) {
		this.setName("XDL-" + new Random().nextInt(9999999));
		this.setLogin("XDCC-DL by Vodes");
		this.setVersion("XDCC-DL by Vodes");
		this.setDccInetAddress(XDCCDL.getInstance().inet);
		this.serv = serv;
	}
	
	@Override
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		ComboEvent event = new ComboEvent(sender, sender, message);
		messageResponse(event);
		super.onPrivateMessage(sender, login, hostname, message);
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		ComboEvent event = new ComboEvent(sender, channel, message);
		messageResponse(event);
		super.onMessage(channel, sender, login, hostname, message);
	}
	
	private void messageResponse(ComboEvent event) {
		if (isFromBot(event) && isXDCCNotification(event) && serv.isEnabled()) {
			if (isWantedXDCC(event)) {
				String afterMSG = event.getMessage().split("(?i)/msg")[1];
				String afterSend = afterMSG.split("(?i)xdcc send")[1].trim();
				String xdccNumber = afterSend.replaceAll("\\D+", "");
				Sys.out("Valid XDCC on '" + serv.getName() + "' in '" + event.getChannel() + "' detected.");
				new DownloadThread(event.getUser(), xdccNumber, event.getChannel(), serv.getIp()).start();
			}
		}
	}
	
	@Override
	protected void onDisconnect() {
		Sys.out("IRC-Bot (" + serv.getName() + ") disconnected!", "warn");
		super.onDisconnect();
	}
	
	@Override
	protected void onConnect() {
		Sys.out("IRC-Bot (" + serv.getName() + ") connected!");
		super.onConnect();
		for(String channel : serv.getChannels().split(",")) {
			this.joinChannel(channel);
		}
	}
	
	@Override
	protected void onIncomingFileTransfer(DccFileTransfer transfer) {
		super.onIncomingFileTransfer(transfer);
	}
	
	@Override
	protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		if(e != null) {
			e.printStackTrace();
			Sys.out("Download for '" + transfer.getFile().getName() + "' has failed.", "error");
			File downloadedFile = new File(XDCCDL.getInstance().directory, transfer.getFile().getName());
			if(downloadedFile.exists()) {
				downloadedFile.delete();
			}
		} else {
			Sys.out("Download for '" + transfer.getFile().getName() + "' has finished.");
			File downloadedFile = new File(XDCCDL.getInstance().directory, transfer.getFile().getName());
			if(downloadedFile.exists()) {
				try {
					Files.move(downloadedFile, new File(XDCCDL.getInstance().optionManager.getString("Download-Path"), transfer.getFile().getName()));
				} catch (IOException e1) {
					Sys.out("Moving file '" + transfer.getFile().getName() + "' has failed.", "error");
					e1.printStackTrace();
					downloadedFile.delete();
				}
			}
		}
		super.onFileTransferFinished(transfer, e);
	}
	
	public Server getServ() {
		return serv;
	}
	
	private boolean isWantedChannel(ComboEvent event) {
		for(String channel : serv.getChannels().split(",")) {
			if(channel.equalsIgnoreCase(event.getChannel())) {
				return true;
			}
		}
		return false;
	}
	

	private boolean isWantedXDCC(ComboEvent event) {
		if (XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
			return false;
		}
		String uncontained = "";
		for (DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if (dla.isEnabled()) {
				String containment = containsAllNeededStrings(event.getMessage(), dla.getContainments().split(","));
				if (containment == "") {
					return true;
				}
			}

		}
		return false;
	}

	private String containsAllNeededStrings(String s, String[] needed) {
		for (String need : needed) {
			if (!StringUtils.containsIgnoreCase(s, need)) {
				return need;
			}
		}
		return "";
	}

	private boolean isXDCCNotification(ComboEvent event) {
		if (StringUtils.containsIgnoreCase(event.getMessage(), "/msg ")
				&& StringUtils.containsIgnoreCase(event.getMessage(), "xdcc send")) {
			return true;
		}
		return false;
	}

	private boolean isFromBot(ComboEvent event) {
//		if (XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
//			return false;
//		}
		for (DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if (dla.isEnabled()) {
				if (event.getUser().equalsIgnoreCase(dla.getBot())) {
					return true;
				}
			}

		}
		return false;
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
