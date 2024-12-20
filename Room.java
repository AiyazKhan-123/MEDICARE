package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Room extends JFrame {

    JTable table;

    Room() {

        // Main panel with a soothing light color
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 890, 590);
        panel.setBackground(new Color(245, 245, 245)); // Light gray color for the background
        panel.setLayout(null);
        add(panel);

        // Room image
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/room.png"));
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(600, 200, 200, 200);
        panel.add(label);

        // JTable to display room data
        table = new JTable();
        table.setBounds(10, 40, 500, 400);
        table.setBackground(new Color(255, 255, 255)); // White background for the table for clarity
        table.setForeground(new Color(0, 51, 102)); // Dark blue for text in the table
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(table);

        try {

            conn c = new conn();
            String q = "select * from room";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

            // Modify availability status to show "Occupied" if booked
            for (int i = 0; i < table.getRowCount(); i++) {
                String availability = table.getValueAt(i, 1).toString(); // Assuming column 1 is Availability
                if (availability.equals("Booked")) {
                    table.setValueAt("Occupied", i, 1); // Change to "Occupied"
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Labels for room columns
        JLabel label1 = new JLabel("Room No");
        label1.setBounds(12, 15, 80, 15);
        label1.setFont(new Font("Tahoma", Font.BOLD, 14));
        label1.setForeground(new Color(0, 51, 102)); // Dark blue for labels
        panel.add(label1);

        JLabel label2 = new JLabel("Availability");
        label2.setBounds(140, 15, 100, 15);
        label2.setFont(new Font("Tahoma", Font.BOLD, 14));
        label2.setForeground(new Color(0, 51, 102));
        panel.add(label2);

        JLabel label3 = new JLabel("Price");
        label3.setBounds(290, 15, 80, 15);
        label3.setFont(new Font("Tahoma", Font.BOLD, 14));
        label3.setForeground(new Color(0, 51, 102));
        panel.add(label3);

        JLabel label4 = new JLabel("Bed Type");
        label4.setBounds(400, 15, 80, 15);
        label4.setFont(new Font("Tahoma", Font.BOLD, 14));
        label4.setForeground(new Color(0, 51, 102));
        panel.add(label4);

        // Back Button
        JButton back = new JButton("Back");
        back.setBounds(200, 500, 120, 30);
        back.setBackground(new Color(34, 139, 34)); // Green background for back button
        back.setForeground(Color.white);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // Set frame properties
        setUndecorated(true);
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);  // Centers the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        new Room();
    }
}
