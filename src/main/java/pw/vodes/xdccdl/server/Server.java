package pw.vodes.xdccdl.server;

public class Server {

	private String name;
	private String ip;
	private String channels;
	private boolean enabled;
	
	public Server(String name, String ip, String channels, boolean enabled) {
		this.name = name;
		this.ip = ip;
		this.channels = channels;
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

}
