// Add this file in your project
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode, labelpnr, labelfare;
    JButton bookflight, fetchButton, flight, fetchFare;
    Choice source, destination;
    JDateChooser dcdate;

    public BookFlight() {
        getContentPane().setBackground(new Color(220, 240, 255));
        setLayout(null);

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(40, 40, 500, 630);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        add(card);

        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(150, 10, 500, 35);
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.BLUE);
        card.add(heading);

        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(30, 60, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(180, 60, 150, 25);
        card.add(tfaadhar);

        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(340, 60, 120, 25);
        fetchButton.addActionListener(this);
        card.add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(30, 100, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblname);

        tfname = new JLabel();
        tfname.setBounds(180, 100, 200, 25);
        card.add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(30, 140, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(180, 140, 200, 25);
        card.add(tfnationality);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(30, 180, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lbladdress);

        tfaddress = new JLabel();
        tfaddress.setBounds(180, 180, 200, 25);
        card.add(tfaddress);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(30, 220, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblgender);

        labelgender = new JLabel();
        labelgender.setBounds(180, 220, 200, 25);
        card.add(labelgender);

        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(30, 260, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblsource);

        source = new Choice();
        source.setBounds(180, 260, 150, 25);
        card.add(source);

        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(30, 300, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lbldest);

        destination = new Choice();
        destination.setBounds(180, 300, 150, 25);
        card.add(destination);

        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(340, 300, 120, 25);
        flight.addActionListener(this);
        card.add(flight);

        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(30, 340, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(180, 340, 200, 25);
        card.add(labelfname);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(30, 380, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(180, 380, 200, 25);
        card.add(labelfcode);

        JLabel lblfare = new JLabel("Flight Fare");
        lblfare.setBounds(30, 420, 150, 25);
        lblfare.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblfare);

        labelfare = new JLabel();
        labelfare.setForeground(new Color(0, 153, 0));
        labelfare.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelfare.setBounds(180, 420, 200, 25);
        card.add(labelfare);

        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(30, 460, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(180, 460, 150, 25);
        card.add(dcdate);

        JLabel lblpnr = new JLabel("PNR Number");
        lblpnr.setBounds(30, 540, 150, 25);
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        card.add(lblpnr);

        labelpnr = new JLabel();
        labelpnr.setBounds(180, 540, 250, 25);
        labelpnr.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelpnr.setForeground(Color.RED);
        card.add(labelpnr);

        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(180, 500, 150, 25);
        bookflight.addActionListener(this);
        card.add(bookflight);

        setSize(600, 750);
        setLocation(400, 30);
        setVisible(true);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from flight");
            while (rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading flight data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText();
            try {
                Conn conn = new Conn();
                String query = "select * from passenger where aadhar = '" + aadhar + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct aadhar");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fetch error: " + e.getMessage());
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM flight WHERE source = '" + src + "' AND destination = '" + dest + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    String fcode = rs.getString("f_code");
                    labelfname.setText(rs.getString("f_name"));
                    labelfcode.setText(fcode);

                    String fareQuery = "SELECT fare FROM flight WHERE f_code = '" + fcode + "'";
                    ResultSet fareRs = conn.s.executeQuery(fareQuery);
                    if (fareRs.next()) {
                        labelfare.setText("₹ " + fareRs.getString("fare"));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Flight Fetch Error: " + e.getMessage());
            }
        } else if (ae.getSource() == bookflight) {
            showPaymentPopup();
        }
    }

    public void showPaymentPopup() {
    JDialog paymentDialog = new JDialog(this, "Payment Gateway", true);
    paymentDialog.setSize(350, 220);
    paymentDialog.setLayout(null);
    paymentDialog.setLocationRelativeTo(this);

    JLabel label = new JLabel("Amount to Pay:");
    label.setBounds(30, 30, 200, 25);
    paymentDialog.add(label);

    JLabel fareLabel = new JLabel(labelfare.getText()); // Get fare from label
    fareLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    fareLabel.setForeground(new Color(0, 128, 0));
    fareLabel.setBounds(30, 60, 200, 25);
    paymentDialog.add(fareLabel);

    JButton payButton = new JButton("Confirm Payment");
    payButton.setBounds(30, 110, 150, 30);
    paymentDialog.add(payButton);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setBounds(190, 110, 100, 30);
    paymentDialog.add(cancelButton);

    payButton.addActionListener(e -> {
        paymentDialog.dispose();
        performBooking(); // Directly proceed with booking
    });

    cancelButton.addActionListener(e -> paymentDialog.dispose());

    paymentDialog.setVisible(true);
}


    public void performBooking() {
        Random random = new Random();
        String pnr = "PNR-" + random.nextInt(1000000);

        String aadhar = tfaadhar.getText();
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String flightname = labelfname.getText();
        String flightcode = labelfcode.getText();
        String src = source.getSelectedItem();
        String des = destination.getSelectedItem();
        String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();

        try {
            Conn conn = new Conn();
            String query = "insert into reservation values('" + pnr + "', 'TIC-" + random.nextInt(10000) + "', '" + aadhar + "', '" + name + "', '" + nationality + "', '" + flightname + "', '" + flightcode + "', '" + src + "', '" + des + "', '" + ddate + "')";
            conn.s.executeUpdate(query);

            labelpnr.setText(pnr);
            showTicketPopup(name, des, ddate, pnr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Booking Error: " + e.getMessage());
        }
    }
     

    public void showTicketPopup(String name, String destination, String date, String pnr) {
        JDialog dialog = new JDialog(this, "Ticket Details", true);
        dialog.setLayout(null);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JLabel lbl1 = new JLabel("Name: " + name);
        lbl1.setBounds(30, 30, 300, 25);
        dialog.add(lbl1);

        JLabel lbl2 = new JLabel("Destination: " + destination);
        lbl2.setBounds(30, 60, 300, 25);
        dialog.add(lbl2);

        JLabel lbl3 = new JLabel("Date: " + date);
        lbl3.setBounds(30, 90, 300, 25);
        dialog.add(lbl3);

        JLabel lbl4 = new JLabel("PNR: " + pnr);
        lbl4.setBounds(30, 120, 300, 25);
        dialog.add(lbl4);

        JButton copyBtn = new JButton("Copy PNR");
        copyBtn.setBounds(30, 160, 120, 25);
        dialog.add(copyBtn);

        copyBtn.addActionListener(e -> {
            StringSelection selection = new StringSelection(pnr);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            JOptionPane.showMessageDialog(dialog, "PNR Copied to Clipboard");
        });

        JButton okBtn = new JButton("OK");
        okBtn.setBounds(250, 200, 80, 30);
        dialog.add(okBtn);

        okBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
    

    public static void main(String[] args) {
        new BookFlight();
    }
}
