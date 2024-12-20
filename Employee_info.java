package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee_info extends JFrame {

    // Constructor to set up the JFrame and components
    Employee_info() {
        // Panel Setup
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 990, 590);
        panel.setBackground(new Color(45, 45, 48)); // Darker background for modern look
        panel.setLayout(null);
        add(panel);

        // JTable Setup
        JTable table = new JTable();
        table.setBounds(10, 34, 980, 450);
        table.setBackground(new Color(233, 236, 239)); // Light background for table for contrast
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Modern font style for readability
        table.setRowHeight(30); // Set row height for better visibility of data

        // Set Table Header Font
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(255, 255, 255)); // White header text
        table.getTableHeader().setBackground(new Color(0, 122, 255)); // Blue background for the header

        // Wrap the table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 34, 980, 450);
        panel.add(scrollPane);

        // Fetch and display data from the database
        try {
            conn c = new conn(); // Make sure the conn class is implemented correctly
            String query = "SELECT * FROM employee_info"; // Use correct table name
            ResultSet resultSet = c.statement.executeQuery(query);
            // Populate JTable with the result from the database
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Labels Styling
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color labelColor = new Color(255, 255, 255); // White color for labels

        // Name Label
        JLabel label1 = new JLabel("Name");
        label1.setBounds(41, 9, 70, 20);
        label1.setFont(labelFont);
        label1.setForeground(labelColor);
        panel.add(label1);

        // Age Label
        JLabel label2 = new JLabel("Age");
        label2.setBounds(180, 9, 70, 20);
        label2.setFont(labelFont);
        label2.setForeground(labelColor);
        panel.add(label2);

        // Phone Number Label
        JLabel label3 = new JLabel("Phone Number");
        label3.setBounds(350, 9, 150, 20);
        label3.setFont(labelFont);
        label3.setForeground(labelColor);
        panel.add(label3);

        // Salary Label
        JLabel label4 = new JLabel("Salary");
        label4.setBounds(550, 9, 150, 20);
        label4.setFont(labelFont);
        label4.setForeground(labelColor);
        panel.add(label4);

        // Gmail Label
        JLabel label5 = new JLabel("Gmail");
        label5.setBounds(730, 9, 150, 20);
        label5.setFont(labelFont);
        label5.setForeground(labelColor);
        panel.add(label5);

        // Aadhar Number Label
        JLabel label6 = new JLabel("Aadhar Number");
        label6.setBounds(830, 9, 150, 20);
        label6.setFont(labelFont);
        label6.setForeground(labelColor);
        panel.add(label6);

        // BACK Button styling
        JButton button = new JButton("BACK");
        button.setBounds(350, 500, 120, 30);
        button.setBackground(new Color(255, 85, 85)); // Red color for button to attract attention
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set a modern font style
        button.setFocusPainted(false); // Remove focus border on button click
        panel.add(button);

        // Action listener for the BACK button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide the frame when the button is clicked
            }
        });

        // Frame Settings
        setUndecorated(true); // Remove title bar
        setSize(1000, 600);  // Set frame size

        // Center the frame on the screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y); // Set the frame location to the center of the screen

        setLayout(null);  // No layout manager
        setVisible(true); // Make the frame visible
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new Employee_info();  // Create and display the Employee Info window
    }
}
