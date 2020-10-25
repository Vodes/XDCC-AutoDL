package pw.vodes.xdccdl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.server.Server;

public class ServerPopUp extends JPopupMenu {
	
	public ServerPopUp(Server serv) {
		JMenuItem setSeen = new JMenuItem("Edit");
		setSeen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowServer win = new WindowServer(serv);
				win.frame.setBounds(XDCCDL.getInstance().window.frmXdccautodl.getX(), XDCCDL.getInstance().window.frmXdccautodl.getY(), win.frame.getWidth(), win.frame.getHeight());
				win.frame.setVisible(true);
			}
		});
		add(setSeen);
		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XDCCDL.getInstance().serverManager.removeServer(serv);
				XDCCDL.getInstance().window.loadServers();
			}
		});
		add(delete);
	}

}
