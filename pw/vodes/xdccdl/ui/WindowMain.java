package pw.vodes.xdccdl.ui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class WindowMain {

	public JFrame frmXdccautodl;
	private JTextField textField_1;
	public JPanel panel = new JPanel();
	public JScrollPane scrollPane = new JScrollPane();
	public JTextPane textPane = new JTextPane();

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
		frmXdccautodl.setBounds(100, 100, 450, 437);
		frmXdccautodl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXdccautodl.getContentPane().setLayout(null);

		scrollPane.setBounds(0, 0, 444, 170);
		frmXdccautodl.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 172, 444, 3);
		frmXdccautodl.getContentPane().add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(12, 215, 422, 124);
		frmXdccautodl.getContentPane().add(scrollPane_1);
		
		textPane.setFont(new Font("Verdana", Font.PLAIN, 11));
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);
		
		JLabel lblLogs = new JLabel("Logs:");
		lblLogs.setFont(new Font("Verdana", Font.BOLD, 12));
		lblLogs.setBounds(10, 187, 74, 23);
		frmXdccautodl.getContentPane().add(lblLogs);
		
		JButton btnAddNew = new JButton("Add new");
		btnAddNew.setFont(new Font("Verdana", Font.BOLD, 12));
		btnAddNew.setBounds(164, 182, 120, 23);
		btnAddNew.setFocusable(false);
		btnAddNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowDownloadAble win = new WindowDownloadAble();
				win.frame.setBounds(XDCCDL.getInstance().window.frmXdccautodl.getX(), XDCCDL.getInstance().window.frmXdccautodl.getY(), win.frame.getWidth(), win.frame.getHeight());
				win.frame.setVisible(true);
			}
		});
		frmXdccautodl.getContentPane().add(btnAddNew);
		
		JLabel lblDownloadPath = new JLabel("Download Path");
		lblDownloadPath.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblDownloadPath.setBounds(12, 351, 188, 20);
		frmXdccautodl.getContentPane().add(lblDownloadPath);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setText(XDCCDL.getInstance().optionManager.getString("Download-Path"));
		textField_1.setBounds(12, 373, 345, 23);
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
		btnSel_1.setBounds(369, 373, 64, 23);
		btnSel_1.setFocusable(false);
		frmXdccautodl.getContentPane().add(btnSel_1);
		loadDownloadAbles();
	}
	
	public void loadDownloadAbles() {
		int height = 2;
		panel.removeAll();
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
			panel.add(dlaButton);
			height += 30;
		}
		panel.setPreferredSize(new Dimension(scrollPane.getWidth(), height));
		scrollPane.setViewportView(panel);
		panel.repaint();
		scrollPane.repaint();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
