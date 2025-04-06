package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lbldateoftravel;
    JButton fetchButton, flight;

    public Cancel() {
        getContentPane().setBackground(new Color(230, 240, 255));
        setLayout(null);

        Random random = new Random();

        JLabel heading = new JLabel("✈ AeroVista - Cancel Ticket");
        heading.setBounds(180, 20, 450, 35);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(0, 51, 102));
        add(heading);

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(30, 70, 700, 300);
        card.setBackground(new Color(255, 255, 255, 240));
        card.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 240), 2));
        add(card);

        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(30, 20, 150, 25);
        lblaadhar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(160, 20, 150, 25);
        tfpnr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfpnr.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102)));
        card.add(tfpnr);

        fetchButton = new JButton("Show Details");
        fetchButton.setBounds(330, 20, 130, 25);
        fetchButton.setBackground(new Color(0, 102, 204));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        fetchButton.setFocusPainted(false);
        fetchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fetchButton.addActionListener(this);
        card.add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(30, 70, 100, 25);
        lblname.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lblname);

        tfname = new JLabel();
        tfname.setBounds(160, 70, 150, 25);
        card.add(tfname);

        JLabel lblnationality = new JLabel("Cancellation No");
        lblnationality.setBounds(30, 110, 120, 25);
        lblnationality.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lblnationality);

        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(160, 110, 150, 25);
        card.add(cancellationno);

        JLabel lbladdress = new JLabel("Flight Code");
        lbladdress.setBounds(30, 150, 100, 25);
        lbladdress.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lbladdress);

        lblfcode = new JLabel();
        lblfcode.setBounds(160, 150, 150, 25);
        card.add(lblfcode);

        JLabel lblgender = new JLabel("Date");
        lblgender.setBounds(30, 190, 100, 25);
        lblgender.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lblgender);

        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(160, 190, 150, 25);
        card.add(lbldateoftravel);

        flight = new JButton("Cancel Ticket");
        flight.setBounds(160, 240, 140, 30);
        flight.setBackground(new Color(220, 53, 69));
        flight.setForeground(Color.WHITE);
        flight.setFont(new Font("Segoe UI", Font.BOLD, 13));
        flight.setFocusPainted(false);
        flight.setCursor(new Cursor(Cursor.HAND_CURSOR));
        flight.addActionListener(this);
        card.add(flight);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/cancel1.png"));
        Image i2 = i1.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(520, 110, 220, 220);
        add(image);

        setSize(800, 450);
        setLocation(350, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText();

            try {
                Conn conn = new Conn();
                String query = "select * from reservation where PNR = '" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    lblfcode.setText(rs.getString("flightcode"));
                    lbldateoftravel.setText(rs.getString("ddate"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct PNR");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == flight) {
            String name = tfname.getText();
            String pnr = tfpnr.getText();
            String cancelno = cancellationno.getText();
            String fcode = lblfcode.getText();
            String date = lbldateoftravel.getText();

            try {
                Conn conn = new Conn();

                String query = "insert into cancel values('" + pnr + "', '" + name + "', '" + cancelno + "', '" + fcode + "', '" + date + "')";
                conn.s.executeUpdate(query);
                conn.s.executeUpdate("delete from reservation where PNR = '" + pnr + "'");

                JOptionPane.showMessageDialog(null, "Ticket Cancelled");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
