package demo_package;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class titleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public titleFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 911, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Welcome to AGOS");
		lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
		lblTitle.setBounds(80, 49, 248, 95);
		contentPane.add(lblTitle);
		
		JLabel lblSubtitle = new JLabel("Who are you?");
		lblSubtitle.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblSubtitle.setBounds(142, 140, 120, 48);
		contentPane.add(lblSubtitle);
		
		RoundedBorder btnAdmin = new RoundedBorder("ADMIN");
		btnAdmin.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnAdmin.setBounds(125, 215, 137, 68);
		btnAdmin.setFocusable(false);
		contentPane.add(btnAdmin);
		
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == btnAdmin) {
					new LogInFrame();
					dispose();
				}
			}
		};
		
		btnAdmin.addActionListener(action);
		
		RoundedBorder btnPassenger = new RoundedBorder("PASSENGER");
		btnPassenger.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnPassenger.setBounds(125, 325, 137, 68);
		btnPassenger.setFocusable(false);
		contentPane.add(btnPassenger);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 149, 176));
		panel.setBounds(406, 0, 508, 475);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\acer\\git\\repository\\JAVA_Eclipse\\src\\demo_package\\titleFrame_IMG1Resized.png"));
		lblNewLabel.setBounds(0, 61, 498, 324);
		panel.add(lblNewLabel);
		
		setVisible(true);
		
	}
}
