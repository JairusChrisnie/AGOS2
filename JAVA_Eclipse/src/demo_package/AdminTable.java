package demo_package;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;

import java.sql.*;

class AdminTable extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    AdminTable() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 600);
        setTitle("Admin Table - Ferry Schedule");
        setLayout(null);
        getContentPane().setBackground(new Color(0x050a30)); // Dark blue
        setLocationRelativeTo(null);
        setResizable(false);

        // Table setup
        String[] columnHeader = {"Trip ID", "Body No.", "Route", "Location", "ETA", "Seats Available", "Status"};
        tableModel = new DefaultTableModel(columnHeader, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Allow editing of all cells
            }
        };
        table = new JTable(tableModel);
        table.setBackground(new Color(0xf4f6fc)); // Light gray
        table.setForeground(new Color(0x050a30)); // Dark blue text
        table.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 900, 400);
        tableModel.addTableModelListener((TableModelListener) new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent update) {
                if (update.getType() == TableModelEvent.UPDATE) {
                    int filledCol = update.getColumn();
                    int filledRow = update.getFirstRow();

                    if (filledCol == 0) {
                        String tripID = (String) tableModel.getValueAt(filledRow, 0);
                        if (tripID != null && tripID.toUpperCase().startsWith("U")) {
                            tableModel.setValueAt("Upstream", filledRow, 2);
                            
                            
                        } else {
                            tableModel.setValueAt("Downstream", filledRow, 2);
                            
                        }
                    }
                }
            }
        });
        add(scrollPane);

        //method that loads up the data from db to jtable
        mtdLoadDataFromDB();

        // Add Row Button
        JButton addRowButton = new JButton("+ Row");
        addRowButton.setBounds(50, 50, 150, 25);
        addRowButton.setBackground(new Color(0x233dff)); // Bright blue
        addRowButton.setForeground(Color.WHITE); // White text
        addRowButton.addActionListener(e -> {
            Object[] emptyRow = new Object[columnHeader.length];
            tableModel.addRow(emptyRow);
        });
        addRowButton.setFocusable(false);
        add(addRowButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(220, 50, 150, 25);
        deleteButton.setBackground(new Color(0x233dff)); // Bright blue
        deleteButton.setForeground(Color.WHITE); // White text
        deleteButton.addActionListener(e -> mtdDeleteRow());
        deleteButton.setFocusable(false);
        add(deleteButton);

        JButton addPassengerButton = new JButton("Add Passenger");
        addPassengerButton.setBounds(400, 50, 150, 25);
        addPassengerButton.setBackground(new Color(0x233dff)); // Bright blue
        addPassengerButton.setForeground(Color.WHITE); // White text
        addPassengerButton.setFocusable(false);
        add(addPassengerButton);

        String[] statusOptions = {"On Time", "Delayed", "Cancelled"};
		JComboBox<String> statusOptCB = new JComboBox<>(statusOptions);
		table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusOptCB));
		add(statusOptCB);
		
		addPassengerButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		        int selectedRow = table.getSelectedRow(); // Get the selected row
		        if (selectedRow != -1) {
		            String tripID = (String) table.getValueAt(selectedRow, 0); // Get the TripID of the selected row
		            new passengerInfo(tripID); // Pass the TripID to the passengerInfo constructor
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a row to add a passenger.");
		        }
		    }
		});


        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(580, 50, 150, 25);
        saveButton.setBackground(new Color(0x233dff)); // Bright blue
        saveButton.setForeground(Color.WHITE); // White text
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> mtdSaveData());
        saveButton.setFocusable(false);
        add(saveButton);

        setVisible(true);
    }

    private void mtdLoadDataFromDB() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");
            //this is my local host if error, please install one 					"my db"		"user", "password"

            String query = "SELECT * FROM ferryTABLE ORDER BY created_at ASC";;
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("Trip ID"),
                    rs.getString("Body No."),
                    rs.getString("Route"),
                    rs.getString("Location"),
                    rs.getString("ETA"),
                    rs.getString("Seats Available"),
                    rs.getString("Status")
                };
                tableModel.addRow(row);
            }

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mtdDeleteRow(){
        int intSelectedRow = table.getSelectedRow();
            if(intSelectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String strTripID = (String) tableModel.getValueAt(intSelectedRow, 0);
            
            if(strTripID == null || strTripID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The selected row does not have a valid trip ID.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int intConfirmation = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this row?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if(intConfirmation == JOptionPane.YES_OPTION) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");
                    //this is my local host if error, please install one 					"my db"		"user", "password"
                    
                    String strDeleteQuery = "DELETE FROM ferryTABLE WHERE `Trip ID` = ?";
                    PreparedStatement prepstmt = con.prepareStatement(strDeleteQuery);
                    
                    prepstmt.setString(1, strTripID);
                    
                    int intRowsAffected = prepstmt.executeUpdate();
                    
                    con.close();
                    
                    if(intRowsAffected > 0) {
                        tableModel.removeRow(intSelectedRow);
                        JOptionPane.showMessageDialog(null, "Row successfully deleted!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Failed to delete row in database");	
                    }
                }
                catch(ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(intConfirmation == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Deletion cancelled");
            }
    }

    public void mtdSaveData() {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();

        // Check if the table is empty
        if (tblModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty.", "EMPTY", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String strTripID, strRoute, strLocation, strETA, strStatus, strBodyNo, strSeats;

            try {
                // Database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");

                for (int i = 0; i < tblModel.getRowCount(); i++) {
                    // Retrieve values from the table row
                    strTripID = tblModel.getValueAt(i, 0) != null ? tblModel.getValueAt(i, 0).toString() : "";
                    strBodyNo = tblModel.getValueAt(i, 1) != null ? tblModel.getValueAt(i, 1).toString() : "";
                    strRoute = tblModel.getValueAt(i, 2) != null ? tblModel.getValueAt(i, 2).toString() : "";
                    strLocation = tblModel.getValueAt(i, 3) != null ? tblModel.getValueAt(i, 3).toString() : "";
                    strETA = tblModel.getValueAt(i, 4) != null ? tblModel.getValueAt(i, 4).toString() : "";
                    strSeats = tblModel.getValueAt(i, 5) != null ? tblModel.getValueAt(i, 5).toString() : "";
                    strStatus = tblModel.getValueAt(i, 6) != null ? tblModel.getValueAt(i, 6).toString() : "";

                    // Check if Trip ID exists in the database
                    String checkQuery = "SELECT COUNT(*) FROM ferryTABLE WHERE `Trip ID` = ?";
                    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                    checkStmt.setString(1, strTripID);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    if (count == 0) {
                        // Insert new rows
                        String insertQuery = "INSERT INTO ferryTABLE(`Trip ID`, `Body No.`, `Route`, `Location`, `ETA`, `Seats Available`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                        insertStmt.setString(1, strTripID);
                        insertStmt.setString(2, strBodyNo);
                        insertStmt.setString(3, strRoute);
                        insertStmt.setString(4, strLocation);
                        insertStmt.setString(5, strETA);
                        insertStmt.setString(6, strSeats);
                        insertStmt.setString(7, strStatus);
                        insertStmt.executeUpdate();
                    } else {
                        // Update existing rows
                        String updateQuery = "UPDATE ferryTABLE SET `Body No.` = ?, `Route` = ?, `Location` = ?, `ETA` = ?, `Seats Available` = ?, `Status` = ? WHERE `Trip ID` = ?";
                        PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                        updateStmt.setString(1, strBodyNo);
                        updateStmt.setString(2, strRoute);
                        updateStmt.setString(3, strLocation);
                        updateStmt.setString(4, strETA);
                        updateStmt.setString(5, strSeats);
                        updateStmt.setString(6, strStatus);
                        updateStmt.setString(7, strTripID);
                        updateStmt.executeUpdate();
                    }
                }

                con.close();
                JOptionPane.showMessageDialog(null, "Changes saved to the database!");

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new AdminTable();
    }
}