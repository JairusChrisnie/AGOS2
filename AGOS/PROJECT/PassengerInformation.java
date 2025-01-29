package AGOS.PROJECT;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class PassengerInformation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtAddress;
	private JTextField txtContact;
	private ButtonGroup genderButton;
	private String strTripID;
	
	JComboBox<String>  cmbLocation;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnOthers;
	
	public PassengerInformation(String strTripID) {
		this.strTripID = strTripID;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Passenger Information");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 28));
		lblNewLabel.setBounds(84, 29, 322, 63);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Age:");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1.setBounds(58, 166, 83, 26);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name:");
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(58, 130, 83, 26);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Gender:");
		lblNewLabel_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(58, 202, 83, 26);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Address:");
		lblNewLabel_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_3.setBounds(58, 238, 83, 26);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Contact #:");
		lblNewLabel_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_4.setBounds(58, 274, 83, 26);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Destination:");
		lblNewLabel_1_5.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_5.setBounds(58, 310, 99, 26);
		contentPane.add(lblNewLabel_1_5);
		
		txtName = new JTextField();
		txtName.setBounds(151, 130, 217, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setColumns(10);
		txtAge.setBounds(151, 166, 217, 26);
		contentPane.add(txtAge);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(151, 238, 217, 26);
		contentPane.add(txtAddress);
		
		txtContact = new JTextField();
		txtContact.setColumns(10);
		txtContact.setBounds(151, 274, 217, 26);
		contentPane.add(txtContact);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMale.setBounds(147, 202, 55, 21);
		contentPane.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFemale.setBounds(218, 202, 68, 21);
		contentPane.add(rdbtnFemale);
		
		rdbtnOthers = new JRadioButton("Others");
		rdbtnOthers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnOthers.setBounds(300, 202, 68, 21);
		contentPane.add(rdbtnOthers);
		
		genderButton = new ButtonGroup();
		
		genderButton.add(rdbtnMale);
		genderButton.add(rdbtnFemale);
		genderButton.add(rdbtnOthers);
		
		
		
		String[] arrRoute = {"Select Destination", "Quinta", "Lawton", "Escolta", "Sta. Ana", "Lambingan", "Valenzuela", "Hulo", "Guadalupe", "San Joaquin", "Kalawaan"};
		cmbLocation = new JComboBox<>(arrRoute);
		cmbLocation.setBounds(151,310,217,27);
		contentPane.add(cmbLocation);
;			
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnSave) {
					getPassengerData();
				}
			}
		});
		btnSave.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnSave.setBounds(113, 378, 107, 29);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnCancel.setBounds(261, 378, 107, 29);
		btnCancel.addActionListener(e ->{
			dispose();
		});
		contentPane.add(btnCancel);
		
		setVisible(true);
	}
	
	public void setPassengerData(String strTripID, String strName, String strAge, String strGender, String strContact, String strDestination, String strAddress) {
	    this.strTripID = strTripID;
	    txtName.setText(strName);
	    txtAge.setText(strAge);
	    txtAddress.setText(strAddress);
	    txtContact.setText(strContact);

	    // Initialize cmbLocation only if null
	    if (cmbLocation == null) {
	        String[] arrLocation = {"Select Destination", "Pinagbuhatan", "San Joaquin", "Maybunga", "Kalawaan", "Guadalupe", "Valenzuela", "Hulo",
	                                "Lambingan", "Sta. Ana", "PUP", "Lawton", "Escolta", "Quinta"};
	        cmbLocation = new JComboBox<>(arrLocation);
	    }
	    cmbLocation.setSelectedItem(strDestination);
	}

	public void getPassengerData() {
	    String getName = txtName.getText();
	    String getAge = "";
	    int intAge = Integer.parseInt(txtAge.getText());
	    
	    try {
	    	if(intAge <= 0) {
	    		throw new NumberFormatException();
	    	}
	    } catch (NumberFormatException e) {
	    	JOptionPane.showMessageDialog(this, "Input Error", "Input Error", JOptionPane.ERROR_MESSAGE); 
	    	return; 
	    }
	    
	    getAge = "" + intAge;
	    
	    // Determine gender based on the selected radio button
	    String getGender = ""; 
	    if (rdbtnMale.isSelected()) {
	        getGender = "Male";
	    } else if (rdbtnFemale.isSelected()) {
	        getGender = "Female";
	    } else if (rdbtnOthers.isSelected()) {
	        getGender = "Others";
	    }
	    
	    String getAddress = txtAddress.getText();
	    String getContact = txtContact.getText();
	    String getLocation = (String) cmbLocation.getSelectedItem();

	    mtdSavePassengerData(strTripID, getName, getAge, getGender, getAddress, getContact, getLocation);
	    dispose();
	}

	
	public void mtdSavePassengerData(String strTripID, String getName, String getAge, String getGender, String getAddress, String getContact, String getLocation) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root")){
			String query = "INSERT INTO passengerTABLE (`Trip ID`, `Name`, `Age`, `GENDER`, `Address`, `Contact #`, `Destination`) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			try(PreparedStatement insertStmt = con.prepareStatement(query)){
				insertStmt.setString(1, strTripID);
				insertStmt.setString(2, getName);
				insertStmt.setString(3,  getAge);
				insertStmt.setString(4, getGender);
				insertStmt.setString(5,  getContact);
				insertStmt.setString(6,  getAddress);
				insertStmt.setString(7, getLocation);
				
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
