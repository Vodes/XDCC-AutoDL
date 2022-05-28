package pw.vodes.xdccdl.ui;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.SwingUtilities;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.util.Sys;

public class TrayIconUtil {
	
	public TrayIcon create() {
		if(SystemTray.isSupported()) {
			PopupMenu popup = new PopupMenu();
			popup.add("XDCC-AutoDL v" + XDCCDL.getInstance().version);
			MenuItem gitItem = new MenuItem("By Vodes (Git)");
			gitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().browse(new URI("https://github.com/Vodes/XDCC-AutoDL/"));
					}catch(Exception ex) {
						Sys.out("Can't open URI", "error");
						ex.printStackTrace();
					}
				}
			});
			popup.add(gitItem);
			popup.add("--------------------");
			MenuItem configItem = new MenuItem("Open Configuration");
			configItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(XDCCDL.getInstance().window == null) {
						XDCCDL.getInstance().window = new WindowMain();
						XDCCDL.getInstance().window.frmXdccautodl.setVisible(true);
						XDCCDL.getInstance().window.frmXdccautodl.toFront();
						XDCCDL.getInstance().window.updateLog();
					} else {
						XDCCDL.getInstance().window.frmXdccautodl.toFront();
					}

				}
			});
			MenuItem exitItem = new MenuItem("Exit");
			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			popup.add(configItem);
			popup.add(exitItem);
			try {
				TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("xdccdl.png")), "XDCC-AutoDL", popup);
				trayIcon.setImageAutoSize(true);
				trayIcon.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseClicked(MouseEvent e) {
						if(SwingUtilities.isLeftMouseButton(e)) {
							if (XDCCDL.getInstance().window == null) {
								XDCCDL.getInstance().window = new WindowMain();
								XDCCDL.getInstance().window.frmXdccautodl.setVisible(true);
								XDCCDL.getInstance().window.frmXdccautodl.toFront();
								XDCCDL.getInstance().window.updateLog();
							} else {
								XDCCDL.getInstance().window.frmXdccautodl.dispose();
								XDCCDL.getInstance().window = null;
							}
						}
					}
				});
				SystemTray.getSystemTray().add(trayIcon);
				return trayIcon;
			} catch (Exception e2) {
				Sys.out("Couldn't add trayicon.");
				e2.printStackTrace();
			}
		}
		return null;
	}

}
