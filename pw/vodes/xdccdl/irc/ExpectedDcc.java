package pw.vodes.xdccdl.irc;

public class ExpectedDcc {
	
	private String botname;
	private long time;
	
	public ExpectedDcc(String botname, long time) {
		this.botname = botname;
		this.time = time;
	}
	
	public String getBotname() {
		return botname;
	}
	
	public long getTime() {
		return time;
	}

}
