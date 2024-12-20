package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ALL_Patient_Info extends JFrame {

    ALL_Patient_Info(){
        // Panel Setup (apply first code design)
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 890, 590);
        panel.setBackground(new Color(45, 52, 54)); // Darker background color (from first code)
        panel.setLayout(null);
        add(panel);

        // JTable Design (apply first code design)
        JTable table = new JTable();
        table.setBounds(10, 40, 860, 450);
        table.setBackground(new Color(255, 255, 255)); // White background for better readability
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Modern font style for better readability
        table.setRowHeight(30); // Row height for better visual clarity
        table.setSelectionBackground(new Color(52, 152, 219)); // Blue selection background
        table.setSelectionForeground(Color.WHITE); // White text when selected
        table.getTableHeader().setBackground(new Color(52, 152, 219)); // Light blue header
        table.getTableHeader().setForeground(Color.WHITE); // White header text
        table.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1)); // Blue border for table cells
        panel.add(table);

        try {
            conn c = new conn();
            String q = "select * from Patient_Info";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Labels Styling (from first code)
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14); // Modern font for labels
        Color labelColor = Color.WHITE; // White color for labels

        // ID Label
        JLabel label1 = new JLabel("ID");
        label1.setBounds(31, 11, 100, 14);
        label1.setFont(labelFont);
        label1.setForeground(labelColor); // White text
        panel.add(label1);

        // Number Label
        JLabel label2 = new JLabel("Number");
        label2.setBounds(150, 11, 100, 14);
        label2.setFont(labelFont);
        label2.setForeground(labelColor); // White text
        panel.add(label2);

        // Name Label
        JLabel label3 = new JLabel("Name");
        label3.setBounds(270, 11, 100, 14);
        label3.setFont(labelFont);
        label3.setForeground(labelColor); // White text
        panel.add(label3);

        // Gender Label
        JLabel label4 = new JLabel("Gender");
        label4.setBounds(360, 11, 100, 14);
        label4.setFont(labelFont);
        label4.setForeground(labelColor); // White text
        panel.add(label4);

        // Disease Label
        JLabel label5 = new JLabel("Disease");
        label5.setBounds(480, 11, 100, 14);
        label5.setFont(labelFont);
        label5.setForeground(labelColor); // White text
        panel.add(label5);

        // Room Label
        JLabel label6 = new JLabel("Room");
        label6.setBounds(600, 11, 100, 14);
        label6.setFont(labelFont);
        label6.setForeground(labelColor); // White text
        panel.add(label6);

        // Time Label
        JLabel label7 = new JLabel("Time");
        label7.setBounds(700, 11, 100, 14);
        label7.setFont(labelFont);
        label7.setForeground(labelColor); // White text
        panel.add(label7);

        // Deposit Label
        JLabel label8 = new JLabel("Deposit");
        label8.setBounds(800, 11, 100, 14);
        label8.setFont(labelFont);
        label8.setForeground(labelColor); // White text
        panel.add(label8);

        // BACK Button Design (from first code)
        JButton button = new JButton("BACK");
        button.setBounds(370, 510, 150, 40);
        button.setBackground(new Color(52, 152, 219)); // Blue background
        button.setForeground(Color.WHITE); // White text color
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Modern and larger font size
        button.setFocusPainted(false); // Remove border when clicked
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        button.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2)); // Blue border
        panel.add(button);

        // ActionListener for BACK button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide the frame when the button is clicked
            }
        });

        // Frame Settings (apply first code design)
        setUndecorated(true); // Remove title bar for a clean look
        setSize(900, 600); // Set the frame size

        // Dynamically center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen size
        int x = (screenSize.width - getWidth()) / 2; // Calculate the X position
        int y = (screenSize.height - getHeight()) / 2; // Calculate the Y position
        setLocation(x, y); // Set frame location to center
        setLayout(null); // No layout manager
        setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        new ALL_Patient_Info(); // Create and display the Patient Info window
    }
}
