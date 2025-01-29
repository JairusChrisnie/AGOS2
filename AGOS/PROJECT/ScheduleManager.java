package AGOS.PROJECT;

// Abstract class for Schedule Preview

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



public abstract class ScheduleManager {
    protected DefaultTableModel tableModel;
    protected String[] columnNames = {"Trip ID", "Body No.", "Route", "Location", "ETA", "Seats Available", "Status"};

    public ScheduleManager() {
        tableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // By default, cells are not editable
            }
        };
    }

    // Abstract method to load data from the database
    public abstract void loadDataFromDB();

    // Abstract method to save data to the database
    public abstract void saveDataToDB(DefaultTableModel tableModel);

    // Method to display the schedule preview
    public void showSchedulePreview(JPanel contentPanel) {
        contentPanel.removeAll();

        JLabel scheduleLabel = new JLabel("Schedule Preview");
        scheduleLabel.setFont(new Font("Century Gothic", Font.BOLD, 45));
        scheduleLabel.setForeground(Color.WHITE);
        scheduleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        scheduleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(99, 117, 156)); // COLOR_5
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50, 79, 124), 40)); // COLOR_4
        tablePanel.setLayout(new BorderLayout());

        tablePanel.add(scheduleLabel, BorderLayout.NORTH);

        JTable detailsTable = new JTable(tableModel);
        detailsTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        detailsTable.setBackground(new Color(182, 190, 209)); // COLOR_1
        detailsTable.setForeground(new Color(50, 79, 124)); // COLOR_4
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
}