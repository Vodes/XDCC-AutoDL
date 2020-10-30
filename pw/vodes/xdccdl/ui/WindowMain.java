package pw.vodes.xdccdl.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.server.Server;

public class WindowMain {

	public JFrame frmXdccautodl;
	private JTextField textField_1;
	public JTextPane textPane = new JTextPane();
	
	//Download Panel
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel_2 = new JPanel();
	
	//Server Panel
	JScrollPane scrollPane_2 = new JScrollPane();
	JPanel panel_3 = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowMain window = new WindowMain();
					window.frmXdccautodl.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmXdccautodl = new JFrame();
		frmXdccautodl.setTitle("XDCC-AutoDL v" + XDCCDL.getInstance().version);
		frmXdccautodl.setResizable(false);
		frmXdccautodl.setBounds(100, 100, 450, 522);
		frmXdccautodl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXdccautodl.getContentPane().setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 257, 444, 3);
		frmXdccautodl.getContentPane().add(separator);
		
		JLabel lblLogs = new JLabel("Logs:");
		lblLogs.setFont(new Font("Verdana", Font.BOLD, 12));
		lblLogs.setBounds(12, 264, 74, 23);
		frmXdccautodl.getContentPane().add(lblLogs);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 12));
		tabbedPane.setBounds(0, -8, 444, 261);
		frmXdccautodl.getContentPane().add(tabbedPane);
		
		JButton btnAddNew = new JButton("Add new");
		btnAddNew.setFont(new Font("Verdana", Font.BOLD, 12));
		btnAddNew.setBounds(165, 262, 120, 23);
		btnAddNew.setFocusable(false);
		btnAddNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabbedPane.getSelectedIndex() == 0) {
					WindowDownloadAble win = new WindowDownloadAble();
					win.frame.setBounds(XDCCDL.getInstance().window.frmXdccautodl.getX(), XDCCDL.getInstance().window.frmXdccautodl.getY(), win.frame.getWidth(), win.frame.getHeight());
					win.frame.setVisible(true);
				} else {
					WindowServer win = new WindowServer();
					win.frame.setBounds(XDCCDL.getInstance().window.frmXdccautodl.getX(), XDCCDL.getInstance().window.frmXdccautodl.getY(), win.frame.getWidth(), win.frame.getHeight());
					win.frame.setVisible(true);
				}

			}
		});
		frmXdccautodl.getContentPane().add(btnAddNew);
		
		JLabel lblDownloadPath = new JLabel("Download Path");
		lblDownloadPath.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblDownloadPath.setBounds(12, 435, 188, 20);
		frmXdccautodl.getContentPane().add(lblDownloadPath);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setText(XDCCDL.getInstance().optionManager.getString("Download-Path"));
		textField_1.setBounds(12, 461, 345, 23);
		frmXdccautodl.getContentPane().add(textField_1);
		
		JButton btnSel_1 = new JButton("Sel");
		btnSel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				textField_1.setText(f.getSelectedFile().getAbsolutePath());
				XDCCDL.getInstance().optionManager.setOptionValue("Download-Path", f.getSelectedFile().getAbsolutePath());
			}
		});
		btnSel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnSel_1.setBounds(370, 461, 64, 23);
		btnSel_1.setFocusable(false);
		frmXdccautodl.getContentPane().add(btnSel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 299, 422, 124);
		frmXdccautodl.getContentPane().add(scrollPane_1);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setViewportView(textPane);
		
		textPane.setFont(new Font("Verdana", Font.PLAIN, 11));
		textPane.setEditable(false);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Download", null, panel_1, null);
		panel_1.setLayout(null);
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 439, 215);
		panel_1.add(scrollPane);
		
		scrollPane.setViewportView(panel_2);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Servers", null, panel, null);
		panel.setLayout(null);
		
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(0, 0, 439, 215);
		panel.add(scrollPane_2);
		
		scrollPane_2.setViewportView(panel_3);
		panel_3.setLayout(null);
		loadDownloadAbles();
		loadServers();
	}
	
	public void loadServers() {
		int height = 2;
		panel_3.removeAll();
		for(Server serv : XDCCDL.getInstance().serverManager.getServers()) {
			JToggleButton dlaButton = new JToggleButton(serv.getName());
			dlaButton.setFont(new Font("Verdana", Font.BOLD, 13));
			dlaButton.setHorizontalAlignment(SwingConstants.LEFT);
			dlaButton.setBounds(12, height, 420, 26);
			dlaButton.setSelected(serv.isEnabled());
			dlaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					serv.setEnabled(dlaButton.isSelected());
					XDCCDL.getInstance().serverManager.save();
				}
			});
			dlaButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						ServerPopUp pop = new ServerPopUp(serv);
						pop.show(dlaButton, e.getX(), e.getY());
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			panel_3.add(dlaButton);
			height += 30;
		}
		panel_3.setPreferredSize(new Dimension(scrollPane_2.getWidth(), height));
		scrollPane_2.setViewportView(panel_3);
		panel_3.setLayout(null);
		panel_3.repaint();
		scrollPane_2.repaint();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	public void loadDownloadAbles() {
		int height = 2;
		panel_2.removeAll();
		for(DownloadAble dla : XDCCDL.getInstance().dlaManager.getDownloadables()) {
			JToggleButton dlaButton = new JToggleButton(dla.getName());
			dlaButton.setFont(new Font("Verdana", Font.BOLD, 13));
			dlaButton.setHorizontalAlignment(SwingConstants.LEFT);
			dlaButton.setBounds(12, height, 420, 26);
			dlaButton.setSelected(dla.isEnabled());
			dlaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dla.setEnabled(dlaButton.isSelected());
					XDCCDL.getInstance().dlaManager.save();
				}
			});
			dlaButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						DownloadAblePopUp pop = new DownloadAblePopUp(dla);
						pop.show(dlaButton, e.getX(), e.getY());
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			panel_2.add(dlaButton);
			height += 30;
		}
		panel_2.setPreferredSize(new Dimension(scrollPane.getWidth(), height));
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		panel_2.repaint();
		scrollPane.repaint();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
