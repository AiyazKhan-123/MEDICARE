package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ambulance extends JFrame {
    // JDBC Connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MEDICARE"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASS = "AiyaKhan@0872"; // Database password

    private JTable table;
    private JTextField patientNameField, contactField;

    public Ambulance() {
        // Set up the panel with a new color
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 890, 590);
        panel.setBackground(new Color(54, 57, 63)); // Darker background color for the panel
        panel.setLayout(null);
        add(panel);

        // Set up the table with a new background color
        table = new JTable();
        table.setBounds(10, 40, 900, 450);
        table.setBackground(new Color(35, 39, 42)); // Dark background for the table
        table.setForeground(Color.WHITE); // White text for better contrast
        table.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel.add(table);

        // Create the MySQL connection and fetch all ambulances
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM Ambulance"; // Fetch all ambulances (no filter on availability)
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet)); // Use DbUtils to populate JTable
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set up labels for the table column headers with color change
        JLabel label1 = new JLabel("Name");
        label1.setBounds(31, 11, 100, 14);
        label1.setFont(new Font("Tahoma", Font.BOLD, 14));
        label1.setForeground(new Color(255, 204, 0)); // Golden color for labels
        panel.add(label1);

        JLabel label2 = new JLabel("Gender");
        label2.setBounds(264, 11, 100, 14);
        label2.setFont(new Font("Tahoma", Font.BOLD, 14));
        label2.setForeground(new Color(255, 204, 0)); // Golden color for labels
        panel.add(label2);

        JLabel label3 = new JLabel("Car Name");
        label3.setBounds(366, 11, 100, 14);
        label3.setFont(new Font("Tahoma", Font.BOLD, 14));
        label3.setForeground(new Color(255, 204, 0)); // Golden color for labels
        panel.add(label3);

        JLabel label4 = new JLabel("Available");
        label4.setBounds(520, 11, 100, 14);
        label4.setFont(new Font("Tahoma", Font.BOLD, 14));
        label4.setForeground(new Color(255, 204, 0)); // Golden color for labels
        panel.add(label4);

        JLabel label5 = new JLabel("Location");
        label5.setBounds(750, 11, 100, 14);
        label5.setFont(new Font("Tahoma", Font.BOLD, 14));
        label5.setForeground(new Color(255, 204, 0)); // Golden color for labels
        panel.add(label5);

        // Patient Information Fields with color change
        JLabel patientNameLabel = new JLabel("Patient Name");
        patientNameLabel.setBounds(20, 500, 100, 30);
        patientNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        patientNameLabel.setForeground(Color.WHITE); // White text for better visibility
        panel.add(patientNameLabel);

        patientNameField = new JTextField();
        patientNameField.setBounds(120, 500, 150, 30);
        patientNameField.setBackground(new Color(45, 45, 45)); // Darker background for the text field
        patientNameField.setForeground(Color.WHITE); // White text for input fields
        panel.add(patientNameField);

        JLabel contactLabel = new JLabel("Contact Number");
        contactLabel.setBounds(300, 500, 120, 30);
        contactLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contactLabel.setForeground(Color.WHITE); // White text for better visibility
        panel.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(420, 500, 150, 30);
        contactField.setBackground(new Color(45, 45, 45)); // Darker background for the text field
        contactField.setForeground(Color.WHITE); // White text for input fields
        panel.add(contactField);

        // Set up the BACK button with color change
        JButton backButton = new JButton("BACK");
        backButton.setBounds(750, 510, 120, 30);
        backButton.setBackground(new Color(0, 102, 204)); // Blue background for the button
        backButton.setForeground(Color.WHITE); // White text for better visibility
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close the current window
            }
        });

        // Add the "Book" button with color change
        JButton bookButton = new JButton("Book Ambulance");
        bookButton.setBounds(300, 550, 150, 30);
        bookButton.setBackground(new Color(34, 139, 34)); // Green background for the button
        bookButton.setForeground(Color.WHITE); // White text for better visibility
        panel.add(bookButton);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookAmbulance(); // Call the method to book an ambulance
            }
        });

        // Add the "Cancel Booking" button with color change
        JButton cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.setBounds(500, 550, 150, 30);
        cancelBookingButton.setBackground(new Color(220, 20, 60)); // Red background for the button
        cancelBookingButton.setForeground(Color.WHITE); // White text for better visibility
        panel.add(cancelBookingButton);
        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking(); // Call the method to cancel a booking
            }
        });

        // Center the frame on the screen
        setUndecorated(true);
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null); // This will center the frame
        setVisible(true);
    }

    // Method to handle the booking of the ambulance
    private void bookAmbulance() {
        int row = table.getSelectedRow(); // Get selected row in the table

        // If no row is selected, find the first available ambulance
        if (row == -1) {
            for (int i = 0; i < table.getRowCount(); i++) {
                String availableStatus = table.getValueAt(i, 3).toString(); // Get the available status
                if ("Available".equals(availableStatus)) {
                    row = i; // Select the first available ambulance
                    break;
                }
            }
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "No available ambulances found.");
                return;
            }
        }

        String ambulanceId = table.getValueAt(row, 0).toString(); // Get the id of the selected ambulance
        String availableStatus = table.getValueAt(row, 3).toString(); // Get the available status

        // If the selected ambulance is already booked, show an error
        if ("Not Available".equals(availableStatus)) {
            JOptionPane.showMessageDialog(this, "This ambulance is already booked.");
            return;
        }

        // Get patient details
        String patientName = patientNameField.getText().trim();
        String contactNumber = contactField.getText().trim();

        // Validate input fields
        if (patientName.isEmpty() || contactNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both patient name and contact number.");
            return;
        }

        // Proceed with booking the ambulance
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // Start a transaction

            // Update the ambulance status to "Not Available"
            String updateAmbulanceQuery = "UPDATE Ambulance SET available = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateAmbulanceQuery)) {
                pstmt.setString(1, "Not Available");
                pstmt.setString(2, ambulanceId);
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    conn.rollback(); // Rollback if update fails
                    JOptionPane.showMessageDialog(this, "Error updating ambulance availability.");
                    return;
                }
            }

            // Insert booking details into the Booking table
            String insertBookingQuery = "INSERT INTO Booking (ambulance_id, patient_name, contact_number, booking_status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertBookingQuery)) {
                pstmt.setString(1, ambulanceId);
                pstmt.setString(2, patientName);
                pstmt.setString(3, contactNumber);
                pstmt.setString(4, "Booked");
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit the transaction
            JOptionPane.showMessageDialog(this, "Ambulance booked successfully.");
            refreshTable(); // Refresh the table to show updated status
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error booking ambulance. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to cancel the booking
    private void cancelBooking() {
        int row = table.getSelectedRow(); // Get selected row in the table

        // If no row is selected or the ambulance is already available
        if (row == -1 || "Available".equals(table.getValueAt(row, 3).toString())) {
            JOptionPane.showMessageDialog(this, "Please select a booked ambulance to cancel.");
            return;
        }

        String ambulanceId = table.getValueAt(row, 0).toString(); // Get the id of the selected ambulance
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel the booking?", "Cancel Booking", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.NO_OPTION) {
            return; // If user cancels, do nothing
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // Start a transaction

            // Update the ambulance status to "Available"
            String updateAmbulanceQuery = "UPDATE Ambulance SET available = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateAmbulanceQuery)) {
                pstmt.setString(1, "Available");
                pstmt.setString(2, ambulanceId);
                pstmt.executeUpdate();
            }

            // Remove the booking from the Booking table
            String deleteBookingQuery = "DELETE FROM Booking WHERE ambulance_id = ? AND booking_status = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteBookingQuery)) {
                pstmt.setString(1, ambulanceId);
                pstmt.setString(2, "Booked");
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit the transaction
            JOptionPane.showMessageDialog(this, "Booking canceled successfully.");
            refreshTable(); // Refresh the table to show updated status
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error canceling booking. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresh the table to reflect updated data
    private void refreshTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM Ambulance"; // Fetch all ambulances
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet)); // Use DbUtils to refresh JTable model
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Ambulance(); // Run the Ambulance class when the application starts
    }
}
