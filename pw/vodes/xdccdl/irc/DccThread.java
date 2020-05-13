package pw.vodes.xdccdl.irc;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import pw.vodes.xdccdl.util.Sys;

public class DccThread extends Thread {
	
	public CopyOnWriteArrayList<ExpectedDcc> expecteds = new CopyOnWriteArrayList<>();
	
	@Override
	public void run() {
		while(true) {
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!expecteds.isEmpty()) {
				for(ExpectedDcc dcc : expecteds) {
					if((System.currentTimeMillis() - dcc.getTime()) > 30000) {
						expecteds.remove(dcc);
						Sys.out("XDCC Request hasn't been answered.");
					}
				}
			}

		}
	}

}
