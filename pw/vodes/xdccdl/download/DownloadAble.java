package pw.vodes.xdccdl.download;

public class DownloadAble {
	
	private String name;
	private String bot;
	private String containments;
	private boolean enabled;
	
	public DownloadAble(String name, String bot, String containments, boolean enabled) {
		this.name = name;
		this.bot = bot;
		this.containments = containments;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBot() {
		return bot;
	}

	public void setBot(String bot) {
		this.bot = bot;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getContainments() {
		return containments;
	}

	public void setContainments(String containments) {
		this.containments = containments;
	}

}
