
//package AGOS.PROJECT;

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

public class passengerDashboard extends JFrame implements ActionListener {

    private JPanel mainPanel, headerPanel, sidebarPanel, contentPanel;
    private JButton scheduleButton, ferryStationButton, routeMapButton, helpButton;
    private JLabel logoLabel, titleLabel;
    private JTable detailsTable;
    private JScrollPane scrollPane;

     private ScheduleManager scheduleManager;
    
    // Table model and data
    private DefaultTableModel tableModel;
    private String[] columnNames = {"Trip ID", "Body No.", "Route", "Location", "ETA", "Seats Available", "Status"};
    private Object[][] data = {};

    // Color theme
    private final Color COLOR_1 = new Color(182, 190, 209); // #b6bed1
    private final Color COLOR_4 = new Color(50, 79, 124);  // #324f7c
    private final Color COLOR_5 = new Color(99, 117, 156); // #63759c
    private final Color COLOR_2 = new Color(0, 0, 102); // #000066
    private final Color COLOR_3 = new Color(137, 217, 250); // #89d9fa
    private final Color COLOR_6 = new Color(78, 128, 193); // #4e80c1
    private final Color COLOR_7 = new Color(151, 180, 220); // #97b4dc
    

    public passengerDashboard() {
        
        tableModel = new DefaultTableModel(data, columnNames);

        ImageIcon imgLOGO = new ImageIcon("agosLogo.png");
        setIconImage(imgLOGO.getImage());

        // Frame setup
        setTitle("Passenger Dashboard - Pasig Ferry Information System");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(COLOR_4);
        add(mainPanel, BorderLayout.CENTER);

        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_4);
        headerPanel.setPreferredSize(new Dimension(1000, 150));
        headerPanel.setLayout(null);

        titleLabel = new JLabel("AGOS");
        titleLabel.setBounds(90, 10, 600, 130);
        titleLabel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 150));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel);

        JLabel lblNewLabel = new JLabel("Pasig Ferry");
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(600, 35, 186, 85);
        headerPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Information  System");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 30));
        lblNewLabel_1.setBounds(600, 65, 352, 85);
        headerPanel.add(lblNewLabel_1);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        ImageIcon logo = new ImageIcon("agosLogo.png");
        JLabel lblNewLabel_2 = new JLabel();
        lblNewLabel_2.setBounds(15, -30, 200, 200);
        lblNewLabel_2.setIcon(logo);
        headerPanel.add(lblNewLabel_2);
        Image img = logo.getImage().getScaledInstance(140, 102, Image.SCALE_SMOOTH);
        lblNewLabel_2.setIcon(new ImageIcon(img));

        ImageIcon boat = new ImageIcon("agos_boat.png");
        JLabel boatL = new JLabel();
        boatL.setBounds(935, -99, 640, 360);
        boatL.setIcon(logo);
        headerPanel.add(boatL);
        Image imgBoat = boat.getImage().getScaledInstance(640, 360, Image.SCALE_SMOOTH);
        boatL.setIcon(new ImageIcon(imgBoat));

        // Sidebar Panel
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(COLOR_1);
        sidebarPanel.setPreferredSize(new Dimension(150, 60));
        sidebarPanel.setLayout(new BorderLayout(10, 10)); // Use BorderLayout for sidebar
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        // Buttons for sidebar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(COLOR_1);
        buttonPanel.setLayout(new GridLayout(6, 1, 0, 6)); // 2 rows for buttons
        //buttonPanel.setPreferredSize(new Dimension(200, 500)); // size for sidebar buttons
        
        scheduleButton = createSidebarButton("Schedule");
        scheduleButton.setBackground(COLOR_2); // Highlight the default button
        ferryStationButton = createSidebarButton("Stations");
        ferryStationButton.setBackground(COLOR_6);
        helpButton = createSidebarButton("Help");
        helpButton.setBackground(COLOR_5);
        buttonPanel.add(scheduleButton);
        buttonPanel.add(ferryStationButton);
        buttonPanel.add(helpButton);

        sidebarPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setBackground(COLOR_1);
        contentPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Initialize the schedule manager
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
                // Not needed for passenger dashboard
            }
        };

        mtdLoadDataFromDB();

        // Show schedule preview
        scheduleManager.showSchedulePreview(contentPanel);

        // Default content (Schedule Preview)
        showScheduleView();
        
        

        // Make the frame visible
        setVisible(true);

    }

    // Method to create styled sidebar buttons
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLOR_5);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 55)); // size for sidebar buttons
        button.addActionListener(this);
        return button;
    }

    // Method to show Schedule 
    private void showScheduleView() {
        contentPanel.removeAll();

        JPanel greetingPanel = new JPanel();
        greetingPanel.setBackground(COLOR_2);
        greetingPanel.setLayout(new BorderLayout());    

        JLabel greetingsL = new JLabel("    Welcome to AGOS! Ferry Passenger!");
        greetingsL.setFont(new Font("Century Gothic", Font.BOLD, 30));
        greetingsL.setForeground(Color.WHITE);
        greetingsL.setBackground(COLOR_2);
       
        greetingsL.setHorizontalAlignment(JLabel.LEFT);

        greetingPanel.add(greetingsL, BorderLayout.NORTH);

        JLabel scheduleLabel = new JLabel("Schedule");
        scheduleLabel.setFont(new Font("Century Gothic", Font.BOLD, 45));
        scheduleLabel.setForeground(Color.WHITE);
        scheduleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        scheduleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Panel behind the schedule with an outline
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(COLOR_6);
        tablePanel.setBorder(BorderFactory.createLineBorder(COLOR_2, 40));
        tablePanel.setLayout(new BorderLayout());

        tablePanel.add(scheduleLabel, BorderLayout.NORTH);

        contentPanel.add(greetingPanel, BorderLayout.NORTH);

        // Display the table
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

        // Disable row reordering
        detailsTable.getTableHeader().setReorderingAllowed(false);

        // Disable editing in Schedule Preview
        detailsTable.setEnabled(false); // Disable interaction entirely

        JScrollPane tableScrollPane = new JScrollPane(detailsTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    //Method to show Ferry Stations
    private void showFerryStations() {
        contentPanel.removeAll();
        JLabel stationsLabel = new JLabel(" F e r r y  S t a t i o n s");
        stationsLabel.setFont(new Font("Century Gothic", Font.BOLD, 50));
        stationsLabel.setForeground(Color.WHITE);
        stationsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        stationsLabel.setHorizontalAlignment(JLabel.LEFT);

        // Panel behind the stations with an outline
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(COLOR_6);
        tablePanel.setBorder(BorderFactory.createLineBorder(COLOR_6, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(stationsLabel, BorderLayout.NORTH);

        // Display the Picture
        ImageIcon stations = new ImageIcon("image.png");
        JLabel stationsL = new JLabel();
        stationsL.setBounds(15, -30, 200, 200);
        stationsL.setBorder(BorderFactory.createLineBorder(COLOR_2, 10));
        stationsL.setIcon(stations);
        
        Image imgStation = stations.getImage().getScaledInstance(1520, 500, Image.SCALE_SMOOTH);
        stationsL.setIcon(new ImageIcon(imgStation));
        
        tablePanel.add(stationsL, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        
    }

    //Method to show Help
    private void showHelp() {        
        contentPanel.removeAll();
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(null);
        helpPanel.setBackground(COLOR_5);

        JPanel whitePanel1 = new JPanel();
        whitePanel1.setLayout(null);
        whitePanel1.setBackground(Color.WHITE);
        whitePanel1.setBounds(75, 30, 450, 130);
        helpPanel.add(whitePanel1);

        JPanel whitePanel2 = new JPanel();
        whitePanel2.setLayout(null);
        whitePanel2.setBackground(Color.WHITE);
        whitePanel2.setBounds(75, 180, 450, 400);
        helpPanel.add(whitePanel2);

        // Adding the image to the helpPanel
        ImageIcon imageIcon = new ImageIcon("wiggy.png"); // Replace with your image file path
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 625, 1400, 50); // Adjust the position and size
        helpPanel.add(imageLabel);
        
        // Help title label
        JLabel helpLabel = new JLabel("Help & Contact Information");
        helpLabel.setFont(new Font("Glacial Indifference", Font.BOLD, 24));
        helpLabel.setForeground(COLOR_4);
        helpLabel.setBounds(50, 20, 400, 30);
        
        // MMDA Hotline section
        JLabel mmdaLabel = new JLabel("MMDA Hotline:");
        mmdaLabel.setFont(new Font("Arimo", Font.BOLD, 18));
        mmdaLabel.setForeground(COLOR_4);
        mmdaLabel.setBounds(110, 60, 200, 30);
       
        JTextField mmdaField = new JTextField("(02) 882 4151 to 77");
        mmdaField.setFont(new Font("Arimo", Font.PLAIN, 16));
        mmdaField.setForeground(COLOR_4);
        mmdaField.setEditable(false);
        mmdaField.setOpaque(false);
        mmdaField.setBorder(null);
        mmdaField.setBounds(110, 90, 300, 30);

        ImageIcon imageMMDA = new ImageIcon("admm.png"); // Replace with your image file path
        JLabel imageLabel1 = new JLabel(imageMMDA); // Consistent variable name
        imageLabel1.setBounds(50, 65, 50, 50); // Correct variable usage
        whitePanel1.add(imageLabel1); // Add to panel
                
        // Emergency Contacts section title
        JLabel emergencyLabel = new JLabel("Emergency Contacts");
        emergencyLabel.setFont(new Font("Arimo", Font.BOLD, 24));
        emergencyLabel.setForeground(COLOR_4);
        emergencyLabel.setBounds(50, 20, 400, 30);
        
        // PNP contact
        JLabel pnpLabel = new JLabel("Philippine National Police (PNP):");
        pnpLabel.setFont(new Font("Arimo", Font.BOLD, 18));
        pnpLabel.setForeground(COLOR_4);
        pnpLabel.setBounds(110, 70, 300, 30);
        
        JTextField pnpField = new JTextField("117 or (02) 8722-0650");
        pnpField.setFont(new Font("Arimo", Font.PLAIN, 16));
        pnpField.setForeground(COLOR_4);
        pnpField.setEditable(false);
        pnpField.setOpaque(false);
        pnpField.setBorder(null);
        pnpField.setBounds(110, 90, 300, 30);

        ImageIcon imagePNP = new ImageIcon("allPNP.png"); // Replace with your image file path
        JLabel imageLabel2 = new JLabel(imagePNP); // Consistent variable name
        imageLabel2.setBounds(50, 70, 50, 50); // Correct variable usage
        whitePanel2.add(imageLabel2); // Add to panel
        
        // BFP contact
        JLabel bfpLabel = new JLabel("Bureau of Fire Protection (BFP):");
        bfpLabel.setFont(new Font("Arimo", Font.BOLD, 18));
        bfpLabel.setForeground(COLOR_4);
        bfpLabel.setBounds(110, 140, 300, 30);
        
        JTextField bfpField = new JTextField("(02) 8426-0219 or (02) 8426-0246");
        bfpField.setFont(new Font("Arimo", Font.PLAIN, 16));
        bfpField.setForeground(COLOR_4);
        bfpField.setEditable(false);
        bfpField.setOpaque(false);
        bfpField.setBorder(null);
        bfpField.setBounds(110, 160, 300, 30);

        ImageIcon imageBFP = new ImageIcon("BFP.png"); // Replace with your image file path
        JLabel imageLabel3 = new JLabel(imageBFP); // Consistent variable name
        imageLabel3.setBounds(50, 140, 50, 50); // Correct variable usage
        whitePanel2.add(imageLabel3); // Add to panel
        
        // Medical emergency contact
        JLabel medicalLabel = new JLabel("Medical Emergencies:");
        medicalLabel.setFont(new Font("Arimo", Font.BOLD, 18));
        medicalLabel.setForeground(COLOR_4);
        medicalLabel.setBounds(110, 210, 200, 30);
        
        JTextField medicalField = new JTextField("Call 911");
        medicalField.setFont(new Font("Arimo", Font.PLAIN, 16));
        medicalField.setForeground(COLOR_4);
        medicalField.setEditable(false);
        medicalField.setOpaque(false);
        medicalField.setBorder(null);
        medicalField.setBounds(110, 230, 300, 30);

        ImageIcon image911 = new ImageIcon("911.png"); // Replace with your image file path
        JLabel imageLabel4 = new JLabel(image911); // Consistent variable name
        imageLabel4.setBounds(50, 210, 50, 50); // Correct variable usage
        whitePanel2.add(imageLabel4); // Add to panel

        JLabel redCrossLabel = new JLabel("Philippine Red Cross:");
        redCrossLabel.setFont(new Font("Arimo", Font.BOLD, 18));
        redCrossLabel.setForeground(COLOR_4);
        redCrossLabel.setBounds(110, 280, 200, 30);
        
        JTextField redCrossField = new JTextField("(02) 527-0000");
        redCrossField.setFont(new Font("Arimo", Font.PLAIN, 16));
        redCrossField.setForeground(COLOR_4);
        redCrossField.setEditable(false);
        redCrossField.setOpaque(false);
        redCrossField.setBorder(null);
        redCrossField.setBounds(110, 310, 300, 30);

        ImageIcon imageRC = new ImageIcon("RC.png"); // Replace with your image file path
        JLabel imageLabel5 = new JLabel(imageRC); // Consistent variable name
        imageLabel5.setBounds(50, 280, 50, 50); // Correct variable usage
        whitePanel2.add(imageLabel5); // Add to panel
        
        
        // PRFS Information Section
        JLabel prfsInfoLabel = new JLabel("About us:");
        prfsInfoLabel.setFont(new Font("Arimo", Font.BOLD, 24));
        prfsInfoLabel.setForeground(Color.WHITE);
        prfsInfoLabel.setBounds(600, 40, 400, 30);
        
        JTextArea prfsInfoArea = new JTextArea(
        "The Pasig River Ferry Service (PRFS) is a public water bus service in Metro Manila, "
        + "Philippines, designed to provide an alternative transportation option along the Pasig River. "
        + "It was first inaugurated on February 14, 2007. The service was revived on April 28, 2014, by "
        + "the Metropolitan Manila Development Authority (MMDA) and has since been operational with improvements.");
                
        prfsInfoArea.setFont(new Font("Arimo", Font.PLAIN, 16));
        prfsInfoArea.setForeground(Color.WHITE);
        prfsInfoArea.setOpaque(false);
        prfsInfoArea.setLineWrap(true);
        prfsInfoArea.setWrapStyleWord(true);
        prfsInfoArea.setEditable(false);
        prfsInfoArea.setBounds(600, 85, 600, 120);
       
        JTextArea prfsInfoAreaTagalog = new JTextArea(
        "Ang Pasig River Ferry Service (PRFS) ay isang pampublikong sasakyan sa Metro Manila, " +
        "na nilikha upang magbigay ng alternatibong transportasyon sa kahabaan ng Ilog Pasig. Bagamat unang inilunsad " +
        "noong Pebrero 14, 2007, nakaranas ang serbisyo ng mga hamon sa operasyon at ipinagpaliban noong 2011 dahil sa mga " +
        "pagkalugi. Ito ay pormal na muling ginamit noong Abril 28, 2014, ng Metropolitan Manila Development " +
        "Authority (MMDA).");

        prfsInfoAreaTagalog.setFont(new Font("Arimo", Font.ITALIC, 16));
        prfsInfoAreaTagalog.setForeground(Color.WHITE);
        prfsInfoAreaTagalog.setOpaque(false);
        prfsInfoAreaTagalog.setLineWrap(true);
        prfsInfoAreaTagalog.setWrapStyleWord(true);
        prfsInfoAreaTagalog.setEditable(false);
        prfsInfoAreaTagalog.setBounds(600, 210, 600, 150);

        ImageIcon imageBarko = new ImageIcon("pasig.jpg"); // Replace with your image file path
        JLabel imageLabel6 = new JLabel(imageBarko); // Consistent variable name
        imageLabel6.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        imageLabel6.setBounds(600, 350, 600, 230); // Correct variable usage
        helpPanel.add(imageLabel6); // Add to panel

        
        // Adding components to the panel
        whitePanel1.add(helpLabel);
        whitePanel1.add(mmdaLabel);
        whitePanel1.add(mmdaField);
        whitePanel2.add(emergencyLabel);
        whitePanel2.add(pnpLabel);
        whitePanel2.add(pnpField);
        whitePanel2.add(bfpLabel);
        whitePanel2.add(bfpField);
        whitePanel2.add(medicalLabel);
        whitePanel2.add(medicalField);
        whitePanel2.add(redCrossLabel);
        whitePanel2.add(redCrossField);
        helpPanel.add(prfsInfoLabel);
        helpPanel.add(prfsInfoAreaTagalog);
        helpPanel.add(prfsInfoArea);
        
        contentPanel.add(helpPanel, BorderLayout.CENTER);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scheduleButton) {
            showScheduleView();
        } else if (e.getSource() == ferryStationButton) {
            showFerryStations();
        } else if (e.getSource() == helpButton) {
            showHelp();   
        }
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

//    public static void main(String[] args) {
//        new passengerDashboard();
//    }
}
