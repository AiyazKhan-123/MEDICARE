package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Department extends JFrame {

    private JTable departmentTable;
    private JTextField searchField;  // Text field to enter search term

    Department() {
        // Create connection and ensure table exists
        conn c = new conn();

        // Set up the frame
        setTitle("Department Management");
        setSize(700, 500);  // Adjusted the height for simplicity
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add the panel
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 690, 490);
        panel.setLayout(null);
        panel.setBackground(new Color(90, 156, 163));
        add(panel);

        // Table to display department data
        departmentTable = new JTable();
        departmentTable.setBackground(new Color(90, 156, 163));
        departmentTable.setFont(new Font("Tahoma", Font.BOLD, 14));

        // Add the table inside a JScrollPane for scrolling functionality
        JScrollPane scrollPane = new JScrollPane(departmentTable);
        scrollPane.setBounds(0, 100, 690, 250); // Adjusting position and size
        panel.add(scrollPane);

        // Load data into the table
        loadDepartmentData();

        // Search field and button in the header section
        JLabel searchLabel = new JLabel("Search Department:");
        searchLabel.setBounds(30, 10, 150, 20);
        searchLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(180, 10, 200, 25);
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(400, 10, 100, 30);
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        panel.add(searchButton);

        // Add ActionListener to handle search functionality
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDepartment();
            }
        });

        // Button to go back
        JButton backButton = new JButton("BACK");
        backButton.setBounds(400, 410, 130, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close the department window
            }
        });

        // Make the frame visible
        setVisible(true);

        // Center the frame on the screen
        setLocationRelativeTo(null);  // This will center the window
    }

    // Method to load department data into the JTable
    private void loadDepartmentData() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM department";
            Connection conn = c.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            departmentTable.setModel(DbUtils.resultSetToTableModel(rs)); // Display data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for departments based on department name
    private void searchDepartment() {
        String searchText = searchField.getText();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
            return;
        }

        try {
            conn c = new conn();
            String query = "SELECT * FROM department WHERE department_name LIKE ?";
            Connection conn = c.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");  // Searching by department name

            ResultSet rs = stmt.executeQuery();
            departmentTable.setModel(DbUtils.resultSetToTableModel(rs)); // Update the table with search results
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Department();
    }
}
