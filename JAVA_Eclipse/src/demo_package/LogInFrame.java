package demo_package;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class LogInFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	
	private final String strCorrectUsername = "adminPUP";
	private final String strCorrectPassword = "passwordPUP";
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LogInFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 149, 176));
		panel.setBounds(-12, 0, 492, 475);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitleLabel = new JLabel("Admin Log-In");
		lblTitleLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
		lblTitleLabel.setBounds(628, 92, 163, 49);
		contentPane.add(lblTitleLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblUsername.setBounds(545, 188, 97, 19);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(633, 185, 195, 29);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel.setBounds(545, 240, 78, 29);
		contentPane.add(lblNewLabel);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(633, 240, 195, 29);
		txtPassword.setEchoChar('*');
		contentPane.add(txtPassword);
		
		RoundedBorder btnNewButton = new RoundedBorder("Log-In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strUsername = txtUsername.getText();
				char[] charPassword = txtPassword.getPassword();
				
				String strPassword = new String(charPassword);
				
				if(strUsername.equals(strCorrectUsername) && strPassword.equals(strCorrectPassword)) {
					JOptionPane.showMessageDialog(null, "You have successfully logged in!", "Log-In", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "You have entered an incorrect account!", "Log-in Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBounds(655, 301, 123, 29);
		btnNewButton.setFocusable(false);
		contentPane.add(btnNewButton);
		
		JLabel lblNote = new JLabel("Note: Please refer for the admin account in your designated Ferry Station.");
		lblNote.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblNote.setBounds(532, 439, 344, 13);
		contentPane.add(lblNote);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(654, 351, 45, 13);
		contentPane.add(lblMessage);
		
		JLabel lblNewLabel_3 = new JLabel("Not an Admin?");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_3.setBounds(659, 351, 77, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblGoBack = new JLabel("Go back.");
		lblGoBack.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblGoBack.setBounds(733, 351, 45, 13);
		lblGoBack.setForeground(Color.RED);
		contentPane.add(lblGoBack);
		
		lblGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				new titleFrame();
				dispose();
			}
		});
		
		setVisible(true);
	}
}
