package pw.vodes.xdccdl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;

public class DownloadAblePopUp extends JPopupMenu {
	
	public DownloadAblePopUp(DownloadAble dla) {
		JMenuItem setSeen = new JMenuItem("Edit");
		setSeen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowDownloadAble win = new WindowDownloadAble(dla);
				win.frame.setBounds(XDCCDL.getInstance().window.frmXdccautodl.getX(), XDCCDL.getInstance().window.frmXdccautodl.getY(), win.frame.getWidth(), win.frame.getHeight());
				win.frame.setVisible(true);
			}
		});
		add(setSeen);
		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XDCCDL.getInstance().dlaManager.getDownloadables().remove(dla);
				XDCCDL.getInstance().dlaManager.save();
				XDCCDL.getInstance().window.loadDownloadAbles();
			}
		});
		add(delete);
	}

}
