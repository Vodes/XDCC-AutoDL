package pw.vodes.xdccdl.irc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;

import pw.vodes.xdccdl.DownloadThread;
import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class IRCBot extends PircBot {
	
	private Server serv;
	private int lastNumber = -1;
	private String name;
	
	public IRCBot(Server serv) {
		name = XDCCDL.getInstance().getRandomName(false);
		this.setName(name);
		this.setLogin(name.split("-")[0]);
		this.setVersion(name.split("-")[0]);
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
	protected void onUnknown(String s) {
		Sys.out(s, "error");
		super.onUnknown(s);
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		ComboEvent event = new ComboEvent(sender, channel, message);
		messageResponse(event);
		super.onMessage(channel, sender, login, hostname, message);
	}
	
	private void messageResponse(ComboEvent event) {
		if (isFromBot(event) && isXDCCNotification(event) && serv.isEnabled()) {
			DownloadAble dla = isWantedXDCC(event.getMessage(), event.getUser());
			if (dla != null) {
				String afterMSG = event.getMessage().split("(?i)/msg")[1];
				String afterSend = afterMSG.split("(?i)xdcc send")[1].trim();
				String xdccNumber = afterSend.replaceAll("\\D+", "");
				Sys.out("Valid XDCC on '" + serv.getName() + "' in '" + event.getChannel() + "' detected.");
				this.sendMessage(event.getUser(), "xdcc send #" + xdccNumber);
				//XDCCDL.getInstance().threadQueue.queue.add(new DownloadThread(event.getUser(), xdccNumber, event.getChannel(), serv.getIp(), dla));
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
		if(serv.getChannels().contains(",")) {
			for(String channel : serv.getChannels().split(",")) {
				this.joinChannel(channel);
			}
		} else {
			this.joinChannel(serv.getChannels());
		}

	}

	@Override
	protected void onIncomingFileTransfer(DccFileTransfer transfer) {
		DownloadAble dla = isWantedXDCC(transfer.getFile().getName(), transfer.getNick());
		if(dla != null){
			Sys.out("Started: " + transfer.getFile().getName());
			transfer.receive(transfer.getFile(), true);
			new ProgressThread(transfer, dla).start();
		}
		super.onIncomingFileTransfer(transfer);
	}
	
	@Override
	protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		if(e != null) {
			e.printStackTrace();
			Sys.out("'" + transfer.getFile().getName() + "' has failed.", "error");
			File downloadedFile = new File(XDCCDL.getInstance().directory, transfer.getFile().getName());
			if(downloadedFile.exists()) {
				downloadedFile.delete();
			}
		} else {
			Sys.out("'" + transfer.getFile().getName() + "' has finished.");
			File downloadedFile = transfer.getFile();
			if(downloadedFile.exists()) {
				try {
					DownloadAble dla = isWantedXDCC(transfer.getFile().getName(), "");
					if(dla != null){
						File downloadDir = new File(dla.getDownloadDir());
						downloadDir.mkdirs();
						Files.move(downloadedFile.toPath(), new File(downloadDir, transfer.getFile().getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
						Sys.out("Moved file!");
					} else {
						Files.move(downloadedFile.toPath(), new File(XDCCDL.getInstance().defaultDownloadPath, transfer.getFile().getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
						Sys.out("Moved file to default path!", "warn");
					}
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
	

	private DownloadAble isWantedXDCC(String message, String user) {
		if (XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
			return null;
		}
		String uncontained = "";
		for (DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if (dla.isEnabled()) {
				String containment = containsAllNeededStrings(message, dla.getContainments());
				if(user.isEmpty()){
					if(containment == ""){
						return dla;
					}
				} else {
					if (containment == "" && user.trim().equalsIgnoreCase(dla.getBot().trim())) {
						return dla;
					}
				}

			}

		}
		return null;
	}

	private String containsAllNeededStrings(String s, String needed) {
		if(needed.contains(",")){
			for (String need : needed.split(",")) {
				if (!StringUtils.containsIgnoreCase(s, need.trim())) {
					return need.trim();
				}
			}
		} else {
			if (!StringUtils.containsIgnoreCase(s, needed.trim())) {
				return needed.trim();
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

	private class ProgressThread extends Thread {

		DccFileTransfer transfer;
		DownloadAble dla;

		public ProgressThread(DccFileTransfer transfer, DownloadAble dla){
			this.transfer = transfer;
			this.dla = dla;
		}

		@Override
		public void run() {
			while(!transfer.isDone){
				double transferRate = transfer.getTransferRate() / (1024 * 1024);
				System.out.println("DL '" + transfer.getFile().getName() + "': " + (int)transfer.getProgressPercentage() + "% / " + new BigDecimal(transferRate).setScale(3, RoundingMode.CEILING).doubleValue() + " MB/s");
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			super.run();
		}
	}
}
