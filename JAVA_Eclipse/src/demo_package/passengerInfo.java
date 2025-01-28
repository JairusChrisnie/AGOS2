package demo_package;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class passengerInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtContact;
	private JTextField txtAddress;
	private String strTripID;
	
	JComboBox<String> cmbLocation;
	
	public passengerInfo(String strTripID) {
		this.strTripID = strTripID;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Passenger Information");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(124, 36, 194, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblName.setBounds(63, 112, 45, 13);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(124, 110, 194, 19);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAge.setBounds(63, 146, 45, 13);
		contentPane.add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(124, 144, 194, 19);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contact #");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(63, 184, 68, 13);
		contentPane.add(lblNewLabel_1);
		
		txtContact = new JTextField();
		txtContact.setBounds(124, 182, 194, 19);
		contentPane.add(txtContact);
		txtContact.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Address");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(63, 218, 45, 13);
		contentPane.add(lblNewLabel_2);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(124, 216, 194, 19);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Location");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(63, 253, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		String[] arrLocation = {"Select Destination", "Pinagbuhatan", "San Joaquin", "Maybunga", "Kalawaan", "Guadalupe", "Valenzuela", "Hulo",
				"Lambingan", "Sta. Ana", "PUP", "Lawton", "Escolta", "Quinta"
		};
		cmbLocation = new JComboBox<>(arrLocation);
		cmbLocation.setBounds(124, 250, 194, 21);
		contentPane.add(cmbLocation);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(120, 318, 85, 21);
		btnSave.addActionListener(e -> getPassengerData());
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(233, 318, 85, 21);
		btnCancel.addActionListener(e->{
			dispose();
		});
		contentPane.add(btnCancel);
		
		setVisible(true);
	}
	
	public void setPassengerData(String strTripID, String strName, String strAge, String strContact, String strLocation, String strAddress) {
		this.strTripID = strTripID;
		txtName.setText(strName);
		txtAge.setText(strAge);
		txtAddress.setText(strAddress);
		txtContact.setText(strContact);
		
		String[] arrLocation = {"Select Destination", "Pinagbuhatan", "San Joaquin", "Maybunga", "Kalawaan", "Guadalupe", "Valenzuela", "Hulo",
				"Lambingan", "Sta. Ana", "PUP", "Lawton", "Escolta", "Quinta"
		};
		JComboBox<String> cmbLocation = new JComboBox<>(arrLocation);
		cmbLocation.setSelectedItem(arrLocation);
	}
	
	public void getPassengerData() {
		String getName = txtName.getText();
		String getAge = txtAge.getText();
		String getAddress = txtAddress.getText();
		String getContact = txtContact.getText();
		String getLocation = (String) cmbLocation.getSelectedItem();
		
		mtdSavePassengerData(strTripID, getName, getAge, getAddress, getContact, getLocation);
		dispose();
	}
	
	public void mtdSavePassengerData(String strTripID, String getName, String getAge, String getAddress, String getContact, String getLocation) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root")){
			String query = "INSERT INTO passengerTABLE (`Trip ID`, `Name`, `Age`, `Address`, `Contact #`, `Location`) VALUES (?, ?, ?, ?, ?, ?)";
			
			try(PreparedStatement insertStmt = con.prepareStatement(query)){
				insertStmt.setString(1, strTripID);
				insertStmt.setString(2, getName);
				insertStmt.setString(3,  getAge);
				insertStmt.setString(4,  getContact);
				insertStmt.setString(5,  getAddress);
				insertStmt.setString(6, getLocation);
				
				insertStmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "Passenger data saved successfully!");
				
			}
			catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error saving passenger data.");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
