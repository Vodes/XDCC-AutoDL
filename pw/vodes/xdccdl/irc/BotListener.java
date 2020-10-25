package pw.vodes.xdccdl.irc;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.dcc.ReceiveFileTransfer;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.FileTransferCompleteEvent;
import org.pircbotx.hooks.events.IncomingFileTransferEvent;
import org.pircbotx.hooks.events.MessageEvent;

import pw.vodes.xdccdl.DownloadThread;
import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class BotListener extends ListenerAdapter {

	private Server serv;
	private int lastNumber = -1;
	
	public BotListener(Server serv) {
		this.serv = serv;
	}

	@Override
	public void onConnect(ConnectEvent event) throws Exception {
		Sys.out("IRC-Bot (" + serv.getName() + ") connected!");
		super.onConnect(event);
	}

	@Override
	public void onDisconnect(DisconnectEvent event) throws Exception {
		Sys.out("IRC-Bot (" + serv.getName() + ") disconnected!", "warn");
		super.onDisconnect(event);
	}

	@Override
	public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) throws Exception {
		Sys.out("IRC-Bot (" + serv.getName() + ") connection failed!", "error");
		super.onConnectAttemptFailed(event);
	}

	@Override
	public void onIncomingFileTransfer(IncomingFileTransferEvent event) throws Exception {
		super.onIncomingFileTransfer(event);
		if (!XDCCDL.getInstance().dccT.expecteds.isEmpty() && serv.isEnabled()) {
			XDCCDL.getInstance().dccT.expecteds.remove(0);
			ReceiveFileTransfer fileTransfer = event
					.accept(new File(XDCCDL.getInstance().directory, event.getRawFilename().replaceAll("\"", "")));
			;
			Sys.out("Incoming File: " + event.getRawFilename());
			while (!fileTransfer.getFileTransferStatus().isFinished()) {
				if(fileTransfer.getFileTransferStatus().getException() != null) {
					fileTransfer.getFileTransferStatus().getException().printStackTrace();
				}
				DecimalFormat f = new DecimalFormat("##.00");
				System.out.println(event.getRawFilename() + ": " + f.format(fileTransfer.getFileTransferStatus().getPercentageComplete()) + "%");
				Thread.sleep(250);
			}
		}
	}

	@Override
	public void onFileTransferComplete(FileTransferCompleteEvent event) throws Exception {
//		Sys.out("" + event.getFileName() + " has been downloaded.");
//		String fileNameWithoutQoutes = event.getFileName().replaceAll("\"", "");
//		File goalDirectory = new File(XDCCDL.getInstance().optionManager.getString("Download-Path"));
//		FileUtils.moveFile(new File(XDCCDL.getInstance().directory, fileNameWithoutQoutes),
//				new File(goalDirectory, fileNameWithoutQoutes));
//		if(event.getTransferStatus().getException() != null) {
//			event.getTransferStatus().getException().printStackTrace();
//		}
		super.onFileTransferComplete(event);
	}

	@Override
	public void onMessage(MessageEvent event) throws Exception {
		super.onMessage(event);
		if (isFromBot(event) && isXDCCNotification(event) && serv.isEnabled()) {
			if (isWantedXDCC(event)) {
				String afterMSG = event.getMessage().split("(?i)/msg")[1];
				String afterSend = afterMSG.split("(?i)xdcc send")[1].trim();
				String xdccNumber = afterSend.replaceAll("\\D+", "");
				try {
					int parsedNumber = Integer.parseInt(xdccNumber);
					if(lastNumber == parsedNumber) {
						return;
					} else {
						lastNumber = parsedNumber;
					}
				} catch(Exception e) {}
				Sys.out("Valid XDCC on '" + serv.getName() + "' in '" + event.getChannel().getName() + "' detected.");
				new DownloadThread(event.getUser().getNick(), xdccNumber, event.getChannel().getName(), serv.getIp()).start();
			}
		}
	}

	private boolean isWantedXDCC(MessageEvent event) {
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

	private boolean isXDCCNotification(MessageEvent event) {
		if (StringUtils.containsIgnoreCase(event.getMessage(), "/msg ")
				&& StringUtils.containsIgnoreCase(event.getMessage(), "xdcc send")) {
			return true;
		}
		return false;
	}

	private boolean isFromBot(MessageEvent event) {
		if (XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
			return false;
		}
		for (DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if (dla.isEnabled()) {
				if (event.getUser().getNick().equalsIgnoreCase(dla.getBot())) {
					return true;
				}
			}

		}
		return false;
	}

}
