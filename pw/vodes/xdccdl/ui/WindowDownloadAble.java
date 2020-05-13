package pw.vodes.xdccdl.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.download.DownloadAble;

import javax.swing.JButton;

public class WindowDownloadAble {

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
					WindowDownloadAble window = new WindowDownloadAble();
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
	public WindowDownloadAble() {
		initialize(null);
	}
	
	public WindowDownloadAble(DownloadAble dla) {
		initialize(dla);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(DownloadAble dla) {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setTitle(dla == null ? "Creating new Downloadable" : "Editing: " + dla.getName());
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
		if(dla != null) {
			textField.setText(dla.getName());
		}
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblName_1 = new JLabel("Bot-Name:");
		lblName_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblName_1.setBounds(10, 61, 86, 23);
		frame.getContentPane().add(lblName_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		textField_1.setColumns(10);
		if(dla != null) {
			textField_1.setText(dla.getBot());
		}
		textField_1.setBounds(10, 84, 322, 26);
		frame.getContentPane().add(textField_1);
		
		lblName_2 = new JLabel("Must contain (split by ,):");
		lblName_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblName_2.setBounds(10, 122, 183, 23);
		frame.getContentPane().add(lblName_2);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		textField_2.setColumns(10);
		if(dla != null) {
			textField_2.setText(dla.getContainments());
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
				if(dla != null) {
					XDCCDL.getInstance().dlaManager.getDownloadables().set(XDCCDL.getInstance().dlaManager.getDownloadables().indexOf(dla), new DownloadAble(textField.getText(), textField_1.getText(), textField_2.getText(), dla.isEnabled()));
				} else {
					XDCCDL.getInstance().dlaManager.getDownloadables().add(new DownloadAble(textField.getText(), textField_1.getText(), textField_2.getText(), true));
				}
				XDCCDL.getInstance().dlaManager.save();
				frame.dispose();
				XDCCDL.getInstance().window.loadDownloadAbles();
				XDCCDL.getInstance().window.frmXdccautodl.toFront();
			}
		});
		frame.getContentPane().add(btnSave);
	}
}
