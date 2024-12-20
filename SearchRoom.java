package medicare.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class SearchRoom extends JFrame {

    Choice choice;
    JTable table;

    SearchRoom() {
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 690, 490);
        panel.setBackground(new Color(45, 52, 54)); // Darker background color
        panel.setLayout(null);
        add(panel);

        JLabel For = new JLabel("Search For Room");
        For.setBounds(250, 11, 186, 31);
        For.setForeground(Color.white); // White text color for the title
        For.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(For);

        JLabel status = new JLabel("Status :");
        status.setBounds(70, 70, 80, 20);
        status.setForeground(Color.white); // White text color for labels
        status.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(status);

        choice = new Choice();
        choice.setBounds(170, 70, 120, 20);
        choice.add("Available");
        choice.add("Occupied");
        panel.add(choice);

        table = new JTable();
        table.setBounds(0, 187, 700, 210);
        table.setBackground(new Color(236, 240, 241)); // Light gray table background
        table.setForeground(Color.black); // Black text color for better contrast
        panel.add(table);

        try {
            conn c = new conn();
            String q = "select * from room";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel Roomno = new JLabel("Room Number");
        Roomno.setBounds(23, 162, 150, 20);
        Roomno.setForeground(Color.white); // White text color for labels
        Roomno.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(Roomno);

        JLabel available = new JLabel("Availability");
        available.setBounds(175, 162, 150, 20);
        available.setForeground(Color.white); // White text color for labels
        available.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(available);

        JLabel price = new JLabel("Price");
        price.setBounds(458, 162, 150, 20);
        price.setForeground(Color.white); // White text color for labels
        price.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(price);

        JLabel Bed = new JLabel("Bed Type");
        Bed.setBounds(580, 162, 150, 20);
        Bed.setForeground(Color.white); // White text color for labels
        Bed.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(Bed);

        JButton Search = new JButton("Search");
        Search.setBounds(200, 420, 120, 25);
        Search.setBackground(new Color(52, 152, 219)); // Light blue button background
        Search.setForeground(Color.white); // White text color for the button
        panel.add(Search);
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statusSelected = choice.getSelectedItem();
                String q = "select * from Room where Availability = '" + statusSelected + "'";

                try {
                    conn c = new conn();
                    ResultSet resultSet = c.statement.executeQuery(q);
                    table.setModel(DbUtils.resultSetToTableModel(resultSet));
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        JButton Back = new JButton("Back");
        Back.setBounds(380, 420, 120, 25);
        Back.setBackground(new Color(41, 128, 185)); // Soft blue button background for Back
        Back.setForeground(Color.white); // White text color for the button
        panel.add(Back);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // Center the frame on the screen
        setUndecorated(true);
        setSize(700, 500);
        setLocationRelativeTo(null); // This will center the frame on the screen
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SearchRoom();
    }
}
