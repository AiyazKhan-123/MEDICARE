package medicare.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_Up extends JFrame implements ActionListener {
    JTextField usernameField, emailField;
    JPasswordField passwordField, confirmPasswordField;
    JButton signUpButton, backButton;

    // Constructor to set up the frame and components
    Sign_Up() {
        // Set up the frame
        setTitle("Sign Up");
        setSize(400, 350);  // Increased size to accommodate email field
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 245, 238)); // Light peach color for the background

        // Create and add components
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(0, 51, 102)); // Dark blue for text
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 200, 30);
        usernameField.setBackground(new Color(255, 255, 255)); // White background for text field
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.addActionListener(this);  // Adds listener for Enter key press
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(new Color(0, 51, 102));
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 200, 30);
        emailField.setBackground(new Color(255, 255, 255)); // White background for text field
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.addActionListener(this);  // Adds listener for Enter key press
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(0, 51, 102)); // Dark blue for text
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        passwordField.setBackground(new Color(255, 255, 255)); // White background for password field
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.addActionListener(this);  // Adds listener for Enter key press
        add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 200, 150, 30);
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(new Color(0, 51, 102)); // Dark blue for text
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 200, 200, 30); // Increased width to 250
        confirmPasswordField.setBackground(new Color(255, 255, 255)); // White background for confirm password field
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.addActionListener(this);  // Adds listener for Enter key press
        add(confirmPasswordField);

        // Sign Up button with updated color
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(50, 250, 100, 30);
        signUpButton.setBackground(new Color(34, 139, 34)); // Green background
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.addActionListener(this);
        add(signUpButton);

        // Back button with updated color
        backButton = new JButton("Back");
        backButton.setBounds(200, 250, 100, 30);
        backButton.setBackground(new Color(255, 69, 0)); // Red-orange background
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        add(backButton);

        // Set the window visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String username = usernameField.getText();
            String email = emailField.getText();  // Get email from the text field
            String password = new String(passwordField.getPassword());  // Correct password retrieval
            String confirmPassword = new String(confirmPasswordField.getPassword());  // Correct confirm password retrieval

            // Validate empty fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            // Check if email is valid
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format!");
                return;
            }

            // Check if passwords match
            if (password.equals(confirmPassword)) {
                // Hash the password using SHA-256
                String hashedPassword = hashPassword(password);

                if (hashedPassword != null) {
                    try {
                        // Create a database connection (replace with your actual credentials)
                        String url = "jdbc:mysql://localhost:3306/MEDICARE"; // Use your actual database name
                        String dbUser = "root"; // Your database username
                        String dbPassword = "AiyaKhan@0872"; // Your database password
                        Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);

                        // SQL query to insert user into SignUp table
                        String query = "INSERT INTO SignUp (username, password, email) VALUES (?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, hashedPassword);
                        stmt.setString(3, email);

                        int result = stmt.executeUpdate();

                        if (result > 0) {
                            JOptionPane.showMessageDialog(null, "Sign Up Successful!");
                            setVisible(false); // Close the sign-up window
                            new Login(); // Open the Login window automatically
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Could not save the user data!");
                        }

                        // Close connection
                        conn.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Password hashing failed!");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
            }
        } else if (e.getSource() == backButton) {
            // Close the sign-up window when the Back button is clicked
            setVisible(false); // Optionally, you could open the previous screen instead
        }
    }

    // Hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Email validation using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        new Sign_Up();
    }
}
