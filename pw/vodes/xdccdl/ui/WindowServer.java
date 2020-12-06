package pw.vodes.xdccdl.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;
import pw.vodes.xdccdl.server.Server;
import pw.vodes.xdccdl.util.Sys;

public class WindowServer {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblName_2;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowServer window = new WindowServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowServer() {
		initialize(null);
	}
	
	public WindowServer(Server serv) {
		initialize(serv);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Server serv) {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setTitle(serv == null ? "Creating new Server" : "Editing: " + serv.getName());
		frame.setBounds(100, 100, 350, 258);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblName.setBounds(10, 0, 74, 23);
		frame.getContentPane().add(lblName);
		
		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.PLAIN, 12));
		textField.setBounds(10, 23, 322, 26);
		if(serv != null) {
			textField.setText(serv.getName());
		}
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblName_1 = new JLabel("Server-IP: ");
		lblName_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblName_1.setBounds(10, 61, 86, 23);
		frame.getContentPane().add(lblName_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		textField_1.setColumns(10);
		if(serv != null) {
			textField_1.setText(serv.getIp());
		}
		textField_1.setBounds(10, 84, 322, 26);
		frame.getContentPane().add(textField_1);
		
		lblName_2 = new JLabel("Channels (start with '#' & split by ,):");
		lblName_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblName_2.setBounds(10, 122, 265, 23);
		frame.getContentPane().add(lblName_2);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		textField_2.setColumns(10);
		if(serv != null) {
			textField_2.setText(serv.getChannels());
		}
		textField_2.setBounds(10, 145, 322, 26);
		frame.getContentPane().add(textField_2);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Verdana", Font.BOLD, 14));
		btnSave.setBounds(10, 183, 322, 39);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty() || textField_1.getText().isEmpty() || textField_2.getText().isEmpty()) {
					return;
				}
				Server toAdd = new Server(textField.getText(), textField_1.getText(), textField_2.getText(), true);
				if(serv != null) {
					XDCCDL.getInstance().serverManager.replaceServer(serv, toAdd);
				} else {
					XDCCDL.getInstance().serverManager.addServer(toAdd);
				}
				frame.dispose();
				XDCCDL.getInstance().window.loadServers();
				XDCCDL.getInstance().window.frmXdccautodl.toFront();
			}
		});
		frame.getContentPane().add(btnSave);
	}
}