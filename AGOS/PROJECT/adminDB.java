package AGOS.PROJECT;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class adminDB extends JFrame implements ActionListener{

    // Components for the Admin Dashboard
    private JPanel mainPanel, headerPanel, sidebarPanel, contentPanel;
    private JButton scheduleButton, addEditButton, logoutButton;
    private JLabel logoLabel, titleLabel;
    private JTable detailsTable;
    private JScrollPane scrollPane;

    // Table model and data
    private DefaultTableModel tableModel;
    private String[] columnNames = {"Trip ID", "Body No.", "Route", "Location", "ETA", "Seats Available", "Status"};
    private Object[][] data = {};
    private JComboBox<String> comboBox;
    private int intSeatsAvailable = 0;

    // Color theme
    private final Color COLOR_1 = new Color(182, 190, 209); // #b6bed1
    private final Color COLOR_2 = new Color(0, 0, 102); // #000066
    private final Color COLOR_3 = new Color(137, 217, 250); // #89d9fa
    private final Color COLOR_4 = new Color(50, 79, 124);  // #324f7c
    private final Color COLOR_5 = new Color(99, 117, 156); // #63759c
    private final Color COLOR_6 = new Color(78, 128, 193); // #4e80c1
    private final Color COLOR_7 = new Color(151, 180, 220); // #97b4dc
    
    // Schedule Manager
    private ScheduleManager scheduleManager;

    public adminDB(JPanel mainPanel, JPanel headerPanel, JPanel sidebarPanel, JPanel contentPanel, JButton scheduleButton, JButton addEditButton, JButton logoutButton, JLabel logoLabel, JLabel titleLabel, JTable detailsTable, JScrollPane scrollPane, DefaultTableModel tableModel) throws HeadlessException {
        this.mainPanel = mainPanel;
        this.headerPanel = headerPanel;
        this.sidebarPanel = sidebarPanel;
        this.contentPanel = contentPanel;
        this.scheduleButton = scheduleButton;
        this.addEditButton = addEditButton;
        this.logoutButton = logoutButton;
        this.logoLabel = logoLabel;
        this.titleLabel = titleLabel;
        this.detailsTable = detailsTable;
        this.scrollPane = scrollPane;
        this.tableModel = tableModel;
    }
    
    private int upstreamCounter = 1;
    private int downstreamCounter = 1; 

    private boolean isEditingEnabled = false;

    public adminDB() {
        tableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return isEditingEnabled && column != 0; // Editable only when editing is enabled and column is not Trip ID
            }
        };

        setTitle("Admin Dashboard - Pasig Ferry Information System");
        setSize(1100,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        ImageIcon imgLOGO = new ImageIcon("agosLogo.png");
        setIconImage(imgLOGO.getImage());

  
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(COLOR_4);
        add(mainPanel, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(); 
        headerPanel.setBackground(COLOR_4); 
        headerPanel.setPreferredSize(new Dimension(1000, 125));
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("AGOS"); 
        titleLabel.setBounds (90, -5, 600, 150); 
        titleLabel.setFont(new Font("Franklin Gothic Heavy", Font. BOLD, 150)); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setHorizontalAlignment(JLabel.CENTER); 
        headerPanel.add(titleLabel); 

        JLabel lblNewLabel = new JLabel("Pasig Ferry");
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
        lblNewLabel.setForeground(Color.WHITE); 
        lblNewLabel.setBounds(600, 35, 186, 85);
        headerPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Information System");
        lblNewLabel_1.setForeground(Color.WHITE); 
        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 30));
        lblNewLabel_1.setBounds(600, 65, 352, 85);
        headerPanel.add(lblNewLabel_1);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        ImageIcon boat = new ImageIcon("agos_boat.png");
        JLabel boatL = new JLabel();
        boatL.setBounds(935, -99, 640, 360);
        boatL.setIcon(boat);
        headerPanel.add(boatL);
        Image imgBoat = boat.getImage().getScaledInstance(640, 360, Image.SCALE_SMOOTH);
        boatL.setIcon(new ImageIcon(imgBoat));

        ImageIcon logo = new ImageIcon("agosLOGO.png");
        JLabel lblNewLabel_2 = new JLabel();
        lblNewLabel_2.setBounds(15, -30, 200, 200);
        lblNewLabel_2.setIcon(logo);
        headerPanel.add(lblNewLabel_2);
        Image img = logo.getImage().getScaledInstance(140, 102, Image.SCALE_SMOOTH);
        lblNewLabel_2.setIcon(new ImageIcon(img));

        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(COLOR_1);
        sidebarPanel.setPreferredSize(new Dimension(150, 55));
        sidebarPanel.setLayout(new BorderLayout(10, 10)); 
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(COLOR_1);
        buttonPanel.setLayout(new GridLayout(8, 7, 0, 6)); 

        scheduleButton = createSidebarButton("Schedule");
        scheduleButton.setBackground(COLOR_4); 
        addEditButton = createSidebarButton("Add/Edit ");
        addEditButton.setBackground(COLOR_2);

        buttonPanel.add(scheduleButton);
        buttonPanel.add(addEditButton);

        logoutButton = createSidebarButton("Logout");
        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setBackground(COLOR_1);
        logoutButton.setBackground(COLOR_1);
        logoutButton.setForeground(COLOR_2);
        logoutPanel.add(logoutButton, BorderLayout.SOUTH);
        logoutButton.setPreferredSize(new Dimension(180, 40));
        
        sidebarPanel.add(buttonPanel, BorderLayout.CENTER);
        sidebarPanel.add(logoutPanel, BorderLayout.SOUTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(COLOR_1);
        contentPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        scheduleManager = new ScheduleManager() {
        @Override
        public void loadDataFromDB() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");

                String query = "SELECT * FROM ferryTABLE ORDER BY created_at ASC";
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
            
        @Override
        public void saveDataToDB(DefaultTableModel tableModel) {    
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty.", "EMPTY", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String strTripID, strRoute, strLocation, strETA, strStatus, strBodyNo, strSeats;
            try (
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");
            ){
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    strTripID = (String) tableModel.getValueAt(i, 0);
                    strBodyNo = (String) tableModel.getValueAt(i, 1);
                    strRoute = (String) tableModel.getValueAt(i, 2);
                    strLocation = (String) tableModel.getValueAt(i, 3);
                    strETA = (String) tableModel.getValueAt(i, 4);
                    strSeats = (String) tableModel.getValueAt(i, 5);
                    strStatus = (String) tableModel.getValueAt(i, 6);

                    // Check if Trip ID exists in the database
                    String checkQuery = "SELECT COUNT(*) FROM ferryTABLE WHERE `Trip ID` = ?";
                    try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, strTripID);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);

                        if (count == 0) {
                            // Insert new row
                            String insertQuery = "INSERT INTO ferryTABLE(`Trip ID`, `Body No.`, `Route`, `Location`, `ETA`, `Seats Available`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                                insertStmt.setString(1, strTripID);
                                insertStmt.setString(2, strBodyNo);
                                insertStmt.setString(3, strRoute);
                                insertStmt.setString(4, strLocation);
                                insertStmt.setString(5, strETA);
                                insertStmt.setString(6, strSeats);
                                insertStmt.setString(7, strStatus);
                                insertStmt.executeUpdate();
                            }
                        } else {
                            // Update existing row
                            String updateQuery = "UPDATE ferryTABLE SET `Body No.` = ?, `Route` = ?, `Location` = ?, `ETA` = ?, `Seats Available` = ?, `Status` = ? WHERE `Trip ID` = ?";
                            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
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
                    }
                }

                JOptionPane.showMessageDialog(null, "Data saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    };

        mtdLoadDataFromDB();

        scheduleManager.loadDataFromDB();
 
        scheduleManager.showSchedulePreview(contentPanel);

        setVisible(true);
    }

    // Method to resize ImageIcon
    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLOR_2);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 22));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 55));        
        button.addActionListener(this);
        return button;
    }

    private void showSchedulePreview() {
        contentPanel.removeAll();
        JLabel scheduleLabel = new JLabel("Schedule (Pre-view)");
        scheduleLabel.setFont(new Font("Century Gothic", Font.BOLD, 45));
        scheduleLabel.setForeground(Color.WHITE);
        scheduleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        scheduleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(COLOR_5);
        tablePanel.setBorder(BorderFactory.createLineBorder(COLOR_4, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scheduleLabel, BorderLayout.NORTH);

        detailsTable = new JTable(tableModel);
        detailsTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        detailsTable.setBackground(COLOR_1);
        detailsTable.setForeground(COLOR_4);
        detailsTable.setRowHeight(30);
        JTableHeader tableHeader = detailsTable.getTableHeader();
        tableHeader.setFont(new Font("Century Gothic", Font.BOLD, 16));
        tableHeader.setPreferredSize(new Dimension(100, 30));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < detailsTable.getColumnCount(); i++) {
            detailsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        detailsTable.getTableHeader().setReorderingAllowed(false);

        detailsTable.setEnabled(false); 

        JScrollPane tableScrollPane = new JScrollPane(detailsTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAddEditDetails() {
        contentPanel.removeAll();
        JLabel detailsLabel = new JLabel("Add/Edit Details");
        detailsLabel.setFont(new Font("Century Gothic", Font.BOLD, 45));
        detailsLabel.setForeground(Color.WHITE);
        detailsLabel.setHorizontalAlignment(JLabel.CENTER);
        detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(COLOR_5);
        tablePanel.setBorder(BorderFactory.createLineBorder(COLOR_2, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(detailsLabel, BorderLayout.NORTH);

        detailsTable = new JTable(tableModel);
        detailsTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        detailsTable.setBackground(COLOR_1);
        detailsTable.setForeground(COLOR_4);
        detailsTable.setRowHeight(30);
        JTableHeader tableHeader = detailsTable.getTableHeader();
        tableHeader.setFont(new Font("Century Gothic", Font.BOLD, 16));
        tableHeader.setPreferredSize(new Dimension(100, 30));   

        // Center text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < detailsTable.getColumnCount(); i++) {
            detailsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        detailsTable.getTableHeader().setReorderingAllowed(false);

        detailsTable.getColumnModel().getColumn(2).setCellEditor(new RouteComboBoxEditor());
        detailsTable.getColumnModel().getColumn(3).setCellEditor(new LocationComboBoxEditor());
        detailsTable.getColumnModel().getColumn(6).setCellEditor(new StatusComboBoxEditor());

        JScrollPane tableScrollPane = new JScrollPane(detailsTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(COLOR_1);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addRowButton = createActionButton("Add Row");
        JButton deleteButton = createActionButton("Delete");
        JButton editButton = createActionButton("Edit");
        JButton addPassengerButton = createActionButton("Add Passenger");
        JButton saveButton = createActionButton("Save");

        buttonPanel.add(addRowButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(addPassengerButton);
        buttonPanel.add(saveButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        addRowButton.addActionListener(e -> {
            tableModel.addRow(new Object[]{"", "", "", "", "", "", ""}); 
            isEditingEnabled = true; 
            tableModel.fireTableDataChanged(); 
        });

        deleteButton.addActionListener(e -> mtdDeleteRow());

        editButton.addActionListener(e -> {
            int selectedRow = detailsTable.getSelectedRow();
            if (selectedRow != -1) {
                // Enable editing for the selected row
                isEditingEnabled = true;
                tableModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(this, "Editing enabled for the selected row!", "Edit", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addPassengerButton.addActionListener(e -> {
        	int selectedRow = detailsTable.getSelectedRow();
            if (selectedRow != -1) {
            	String tripID = (String) detailsTable.getValueAt(selectedRow, 0); 
	            new PassengerInformation(tripID);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to add passengers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            // Save the table data to a file
            mtdSaveData();
            isEditingEnabled = false; // Disable editing after saving
            tableModel.fireTableDataChanged(); // Refresh the table to reflect changes
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
        });

        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public void mtdSaveData() {
        DefaultTableModel tblModel = (DefaultTableModel) detailsTable.getModel();

        if (tblModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "The table is empty.", "EMPTY", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String strTripID, strRoute, strLocation, strETA, strStatus, strBodyNo, strSeats;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");

            for (int i = 0; i < tblModel.getRowCount(); i++) {
                String tripID = tblModel.getValueAt(i, 0).toString();
                    if (tripID.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Trip ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                strTripID = tblModel.getValueAt(i, 0) != null ? tblModel.getValueAt(i, 0).toString() : "";
                strBodyNo = tblModel.getValueAt(i, 1) != null ? tblModel.getValueAt(i, 1).toString() : "";
                strRoute = tblModel.getValueAt(i, 2) != null ? tblModel.getValueAt(i, 2).toString() : "";
                strLocation = tblModel.getValueAt(i, 3) != null ? tblModel.getValueAt(i, 3).toString() : "";
                strETA = tblModel.getValueAt(i, 4) != null ? tblModel.getValueAt(i, 4).toString() : "";
                strSeats = tblModel.getValueAt(i, 5) != null ? tblModel.getValueAt(i, 5).toString() : "";
                strStatus = tblModel.getValueAt(i, 6) != null ? tblModel.getValueAt(i, 6).toString() : "";

                String checkQuery = "SELECT COUNT(*) FROM ferryTABLE WHERE `Trip ID` = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setString(1, strTripID);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
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

                try {
                    int seats = Integer.parseInt(strSeats);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Seats Available must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int seats = Integer.parseInt(strSeats);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Seats Available must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            con.close();
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void mtdLoadDataFromDB() {
        tableModel.setRowCount(0); // Clear the table before loading new data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");
                 PreparedStatement stmt = con.prepareStatement("SELECT * FROM ferryTABLE ORDER BY created_at ASC");
                 ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("Trip ID"),
                    rs.getString("Body No."),
                    rs.getString("Route"),
                    rs.getString("Location"),
                    rs.getString("ETA"),
                    rs.getString("Seats Available"),
                    rs.getString("Status")};
                    tableModel.addRow(row);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mtdDeleteRow() {
    int intSelectedRow = detailsTable.getSelectedRow();
    if (intSelectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row to delete.", "ERROR!", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String strTripID = (String) tableModel.getValueAt(intSelectedRow, 0);
    if (strTripID == null || strTripID.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Invalid Trip ID.", "ERROR!", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int confirmation = JOptionPane.showConfirmDialog(null, 
        "Are you sure you want to delete this row?", 
        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

    if (confirmation == JOptionPane.YES_OPTION) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root")) {
            con.setAutoCommit(false); 
                
            try (PreparedStatement moveStmt = con.prepareStatement(
                "INSERT INTO deletedPassengerData(`Trip ID`, `Name`, `Age`, `Gender`, `Address`, `Contact #`, `Destination`) " +
                "SELECT `Trip ID`, `Name`, `Age`, `Gender`, `Address`, `Contact #`, `Destination` " +
                "FROM passengerTABLE WHERE `Trip ID` = ?")) {
                moveStmt.setString(1, strTripID);
                moveStmt.executeUpdate();
                }

            try (PreparedStatement deleteStmt = con.prepareStatement(
                "DELETE FROM passengerTABLE WHERE `Trip ID` = ?")) {
                deleteStmt.setString(1, strTripID);
                deleteStmt.executeUpdate();
                }

            try (PreparedStatement insertStmt = con.prepareStatement(
                "INSERT INTO ferryDeletedTrip(`Trip ID`, `Body No.`, `Route`, `Location`, `ETA`, `Seats Available`, `Status`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                
                insertStmt.setString(1, strTripID);
                insertStmt.setString(2, (String) tableModel.getValueAt(intSelectedRow, 1));
                insertStmt.setString(3, (String) tableModel.getValueAt(intSelectedRow, 2));
                insertStmt.setString(4, (String) tableModel.getValueAt(intSelectedRow, 3));
                insertStmt.setString(5, (String) tableModel.getValueAt(intSelectedRow, 4));
                insertStmt.setString(6, (String) tableModel.getValueAt(intSelectedRow, 5));
                insertStmt.setString(7, (String) tableModel.getValueAt(intSelectedRow, 6));
                insertStmt.executeUpdate();
                }

            try (PreparedStatement deleteTripStmt = con.prepareStatement(
                "DELETE FROM ferryTABLE WHERE `Trip ID` = ?")) {
                deleteTripStmt.setString(1, strTripID);
                int rowsAffected = deleteTripStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    con.commit(); 
                    tableModel.removeRow(intSelectedRow);
                    JOptionPane.showMessageDialog(null, "Row deleted successfully!");
                } else {
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "No rows affected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database Error: " + e.getMessage(), 
                "Critical Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled");
        }
    }

    // Method to create styled action buttons
    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLOR_2);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 20));
        button.setBorder(BorderFactory.createLineBorder(COLOR_1, 3)); 
        button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); 
        button.setBorderPainted(true);
    
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scheduleButton) {
            showSchedulePreview();
        } else if (e.getSource() == addEditButton) {
            showAddEditDetails();
        } else if (e.getSource() == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new titleFrame();
                dispose(); // Closing the dashboard
            }
        }
    }

    // Custom ComboBox Editor for Route column
    private class RouteComboBoxEditor extends DefaultCellEditor {

        public RouteComboBoxEditor() {
            super(new JComboBox<>(new String[]{"", "Upstream", "Downstream"}));
            comboBox = (JComboBox<String>) getComponent();
            comboBox.addActionListener(e -> {
                int selectedRow = detailsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedRoute = (String) comboBox.getSelectedItem();
                    if ("Upstream".equals(selectedRoute)) {
                        String tripID = generateUniqueTripID("U-");
                        tableModel.setValueAt(tripID, selectedRow, 0);
                    } else if ("Downstream".equals(selectedRoute)) {
                        String tripID = generateUniqueTripID("D-");
                        tableModel.setValueAt(tripID, selectedRow, 0);
                    }

                    // Update the Location column based on the selected Route
                    updateLocationComboBox(selectedRow, selectedRoute);
                }
            });
        }
    }
    
    // Generates a unique Trip ID by checking the database if the counter is already present or not.
    private String generateUniqueTripID(String prefix) {
    String query = "SELECT MAX(CAST(SUBSTRING(`Trip ID`, 3) AS UNSIGNED)) FROM ferryTABLE WHERE `Trip ID` LIKE ?";
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");
         PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setString(1, prefix + "%");
        ResultSet rs = pstmt.executeQuery();
        int maxID = 0;
        if (rs.next()) {
            maxID = rs.getInt(1);
        }

        int nextID = maxID + 1;
        return prefix + String.format("%04d", nextID);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return null;
        }
    }
    
    // Custom ComboBox Editor for Location column
    private class LocationComboBoxEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;

        public LocationComboBoxEditor() {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
        }

        public void setLocations(String[] locations) {
            comboBox.removeAllItems();
            for (String location : locations) {
                comboBox.addItem(location);
            }
        }
    }

    // Custom ComboBox Editor for Status column
    private class StatusComboBoxEditor extends DefaultCellEditor {
        public StatusComboBoxEditor() {
            super(new JComboBox<>(new String[]{"On-time", "Cancel", "Delayed"}));
        }
    }

    // Method to update the Location column's ComboBox based on the selected Route
    private void updateLocationComboBox(int row, String route) {
    LocationComboBoxEditor locationEditor = (LocationComboBoxEditor) detailsTable.getColumnModel().getColumn(3).getCellEditor();
    if ("Upstream".equals(route)) {
        locationEditor.setLocations(new String[]{"", "Quinta", "Lawton", "Escolta"});
    } else if ("Downstream".equals(route)) {
        locationEditor.setLocations(new String[]{"", "Sta. Ana", "Lambingan", "Valenzuela", "Hulo", "Guadalupe", "San Joaquin", "Kalawaan"});
    }
        detailsTable.repaint(); // repaint refreshes the whole UI
    }
}