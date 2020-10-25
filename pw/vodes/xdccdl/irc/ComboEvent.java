package pw.vodes.xdccdl.irc;

public class ComboEvent {
	
	private String user;
	private String channel;
	private String message;
	
	public ComboEvent(String user, String channel, String message) {
		this.user = user;
		this.channel = channel;
		this.message = message;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUser() {
		return user;
	}

}
