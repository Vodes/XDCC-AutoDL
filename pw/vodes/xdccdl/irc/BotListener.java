package pw.vodes.xdccdl.irc;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.IncomingFileTransferEvent;
import org.pircbotx.hooks.events.MessageEvent;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.util.Sys;

public class BotListener extends ListenerAdapter { 
	
	@Override
	public void onConnect(ConnectEvent event) throws Exception {
		Sys.out("IRC-Bot connected!");
		super.onConnect(event);
	}
	
	@Override
	public void onDisconnect(DisconnectEvent event) throws Exception {
		Sys.out("IRC-Bot disconnected!", "warn");
		super.onDisconnect(event);
	}
	
	@Override
	public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) throws Exception {
		Sys.out("IRC-Bot connection failed!", "error");
		super.onConnectAttemptFailed(event);
	}
	
	@Override
	public void onIncomingFileTransfer(IncomingFileTransferEvent event) throws Exception {
		if(!XDCCDL.getInstance().dccT.expecteds.isEmpty()) {
			XDCCDL.getInstance().dccT.expecteds.remove(0);
			File downloaddirectory = new File(XDCCDL.getInstance().optionManager.getString("Download-Path"));
			event.accept(new File(downloaddirectory, event.getRawFilename()));
		}
		super.onIncomingFileTransfer(event);
	}
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		super.onMessage(event);
		if(isFromBot(event) && isXDCCNotification(event)) {
			if(isWantedXDCC(event)) {
				String afterMSG = event.getMessage().split("(?i)/msg")[1];
				String afterSend = afterMSG.split("(?i)xdcc send")[1].trim();
				String xdccNumber = afterSend.replaceAll("\\D+", "");
				String finalMessage = "/msg " + event.getUser().getNick() + " xdcc send #" + xdccNumber;
				Sys.out("Valid XDCC detected. | " + event.getUser().getNick() + " | " + xdccNumber);
				XDCCDL.getInstance().dccT.expecteds.add(new ExpectedDcc("", System.currentTimeMillis()));
				XDCCDL.getInstance().bot.sendIRC().message(event.getUser().getNick(), "xdcc send #" + xdccNumber);
			}
		}
	}
	
	private boolean isWantedXDCC(MessageEvent event) {
		if(XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
			return false;
		}
		String uncontained = "";
		for(DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if(dla.isEnabled()) {
				String containment = containsAllNeededStrings(event.getMessage(), dla.getContainments().split(","));
				if(containment == "") {
					return true;
				}
			}

		}
		return false;
	}
	
	private String containsAllNeededStrings(String s, String[] needed) {
		for(String need : needed) {
			if(!StringUtils.containsIgnoreCase(s, need)) {
				return need;
			}
		}
		return "";
	}
	
	private boolean isXDCCNotification(MessageEvent event) {
		if(StringUtils.containsIgnoreCase(event.getMessage(), "/msg ") && StringUtils.containsIgnoreCase(event.getMessage(), "xdcc send")) {
			return true;
		}
		return false;
	}
	
	private boolean isFromBot(MessageEvent event) {
		if(XDCCDL.getInstance().dlaManager.getDownloadables().isEmpty()) {
			return false;
		}
		for(DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			if(dla.isEnabled()) {
				if(event.getUser().getNick().equalsIgnoreCase(dla.getBot())) {
					return true;
				}
			}

		}
		return false;
	}

}
