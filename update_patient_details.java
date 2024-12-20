package medicare.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class update_patient_details extends JFrame {

    update_patient_details() {

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 940, 490);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/updated.png"));
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(500, 60, 300, 300);
        panel.add(label);

        JLabel label1 = new JLabel("Update Patient Details");
        label1.setBounds(124, 11, 260, 25);
        label1.setFont(new Font("Tahoma", Font.BOLD, 20));
        label1.setForeground(Color.white);
        panel.add(label1);

        JLabel label2 = new JLabel("Name :");
        label2.setBounds(25, 88, 100, 14);
        label2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label2.setForeground(Color.white);
        panel.add(label2);

        Choice choice = new Choice();
        choice.setBounds(248, 85, 140, 25);
        panel.add(choice);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from Patient_Info");
            while (resultSet.next()) {
                choice.add(resultSet.getString("Name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel label3 = new JLabel("Room Number :");
        label3.setBounds(25, 129, 100, 14);
        label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label3.setForeground(Color.white);
        panel.add(label3);

        JTextField textFieldR = new JTextField();
        textFieldR.setBounds(248, 129, 140, 20);
        panel.add(textFieldR);

        JLabel label4 = new JLabel("In-Time  :");
        label4.setBounds(25, 174, 100, 14);
        label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label4.setForeground(Color.white);
        panel.add(label4);

        // JTextField to show the time (non-editable)
        JTextField textFieldINTIme = new JTextField();
        textFieldINTIme.setBounds(248, 174, 140, 20);
        textFieldINTIme.setEditable(false); // make it non-editable
        panel.add(textFieldINTIme);

        // Automatically get the current time in the format of "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());

        // Button to get the current time when clicking "CHECK"
        JButton check = new JButton("CHECK");
        check.setBounds(281, 378, 89, 23);
        check.setBackground(Color.black);
        check.setForeground(Color.white);
        panel.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = choice.getSelectedItem();
                String q = "select * from Patient_Info where Name = '" + id + "'";
                try {
                    conn c = new conn();
                    ResultSet resultSet = c.statement.executeQuery(q);
                    while (resultSet.next()) {
                        textFieldR.setText(resultSet.getString("Room_Number"));
                        textFieldINTIme.setText(currentTime); // Automatically set current time in In-Time field
                    }

                    // Save the current time to the database
                    String inTime = textFieldINTIme.getText(); // Get the current time in the text field
                    String updateQuery = "UPDATE Patient_Info SET Time = '" + inTime + "' WHERE Name = '" + id + "'";
                    c.statement.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(null, "In-Time Updated Automatically");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel label5 = new JLabel("Amount Paid (Rs) :");
        label5.setBounds(25, 216, 150, 14);
        label5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label5.setForeground(Color.white);
        panel.add(label5);

        JTextField textFieldAmount = new JTextField();
        textFieldAmount.setBounds(248, 216, 140, 20);
        panel.add(textFieldAmount);

        JLabel label6 = new JLabel("Pending Amount (Rs) :");
        label6.setBounds(25, 261, 150, 14);
        label6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label6.setForeground(Color.white);
        panel.add(label6);

        JTextField textFieldPending = new JTextField();
        textFieldPending.setBounds(248, 261, 140, 20);
        panel.add(textFieldPending);

        JButton update = new JButton("UPDATE");
        update.setBounds(56, 378, 89, 23);
        update.setBackground(Color.black);
        update.setForeground(Color.white);
        panel.add(update);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn c = new conn();
                    String q = choice.getSelectedItem();
                    String room = textFieldR.getText();
                    String time = textFieldINTIme.getText(); // The time should be updated automatically
                    String amount = textFieldAmount.getText();
                    c.statement.executeUpdate("update Patient_Info set Room_Number = '" + room + "', Time = '" + time + "', Deposite = '" + amount + "' where name = '" + q + "'");

                    JOptionPane.showMessageDialog(null, "Updated Successfully");
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton back = new JButton("BACK");
        back.setBounds(168, 378, 89, 23);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // Center the frame on the screen
        setUndecorated(true);
        setSize(950, 500);
        setLayout(null);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        new update_patient_details();
    }
}
