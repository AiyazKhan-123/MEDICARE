package medicare.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;

public class Login extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField jPasswordField;
    JButton b1, b2, signUpButton;

    Login() {
        // Set up the frame
        setTitle("Login - MEDICARE");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));  // Light grey background

        // Heading Label - MEDICARE
        JLabel headingLabel = new JLabel("MEDICARE");
        headingLabel.setBounds(160, 10, 200, 40);
        headingLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        headingLabel.setForeground(new Color(139, 0, 0));  // Dark red color for "MEDICARE"
        add(headingLabel);

        // Username Label
        JLabel namelabel = new JLabel("Username");
        namelabel.setBounds(40, 80, 100, 30);
        namelabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        namelabel.setForeground(Color.BLACK);
        add(namelabel);

        // Password Label
        JLabel password = new JLabel("Password");
        password.setBounds(40, 130, 100, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        password.setForeground(Color.BLACK);
        add(password);

        // Username Text Field
        textField = new JTextField();
        textField.setBounds(150, 80, 220, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When Enter is pressed in the username field, shift focus to password field
                jPasswordField.requestFocus();
            }
        });
        add(textField);

        // Password Field
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(150, 130, 220, 30);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger the login action when Enter is pressed in the password field
                b1.doClick(); // Simulate the login button click
            }
        });
        add(jPasswordField);

        // Login Button
        b1 = new JButton("LogIn");
        b1.setBounds(40, 200, 100, 35);
        b1.setFont(new Font("serif", Font.BOLD, 16));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(0, 102, 204));  // Blue color for Login button
        b1.addActionListener(this);
        add(b1);

        // Cancel Button
        b2 = new JButton("Cancel");
        b2.setBounds(180, 200, 100, 35);
        b2.setFont(new Font("serif", Font.BOLD, 16));
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(255, 99, 71));  // Red color for Cancel button
        b2.addActionListener(this);
        add(b2);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(320, 200, 100, 35);
        signUpButton.setFont(new Font("serif", Font.BOLD, 16));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(34, 139, 34));  // Green color for Sign Up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sign_Up(); // Open the SignUp form
            }
        });
        add(signUpButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = textField.getText();
        String password = new String(jPasswordField.getPassword());

        if (e.getSource() == b1) {
            try {
                // Instantiate the conn class
                conn c = new conn(); // This will establish the connection

                // Get the database connection
                Connection conn = c.getConnection(); // Use the getConnection() method to get the connection

                // SQL query to fetch user data based on username
                String query = "SELECT * FROM SignUp WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");

                    // Compare the entered password (hashed) with the stored password
                    if (storedHashedPassword.equals(hashPassword(password))) {
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        setVisible(false); // Close the login window
                        new Reception(); // Open the Reception window
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Close the connection
                c.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == b2) {
            setVisible(false); // Close the login window
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
