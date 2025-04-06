package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show, download;

    public JourneyDetails() {
        // Background and layout
        getContentPane().setBackground(new Color(240, 248, 255));
        setLayout(null);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 800, 60);
        headerPanel.setBackground(new Color(0, 102, 204));
        add(headerPanel);

        JLabel heading = new JLabel("✈ AeroVista - Journey Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        heading.setBounds(20, 15, 400, 30);
        headerPanel.add(heading);

        // Card-style panel
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBounds(50, 80, 700, 180);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        card.setOpaque(true);
        add(card);

        // Label + Input
        JLabel lblpnr = new JLabel("Enter PNR:");
        lblpnr.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblpnr.setBounds(30, 30, 100, 25);
        card.add(lblpnr);

        pnr = new JTextField();
        pnr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnr.setBounds(140, 30, 160, 30);
        pnr.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        card.add(pnr);

        // Show Button
        show = new JButton("Show Details");
        show.setBounds(320, 30, 130, 30);
        show.setBackground(new Color(0, 153, 76));
        show.setForeground(Color.WHITE);
        show.setFocusPainted(false);
        show.setFont(new Font("Segoe UI", Font.BOLD, 13));
        show.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        show.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 50), 1));
        show.addActionListener(this);
        card.add(show);

        // Download Button
        download = new JButton("Download Ticket");
        download.setBounds(470, 30, 160, 30);
        download.setBackground(new Color(51, 153, 255));
        download.setForeground(Color.WHITE);
        download.setFocusPainted(false);
        download.setFont(new Font("Segoe UI", Font.BOLD, 13));
        download.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        download.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        download.addActionListener(this);
        card.add(download);

        // Table
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 280, 700, 200);
        jsp.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        add(jsp);

        // Frame settings
        setSize(800, 550);
        setLocation(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == show) {
            try {
                Conn conn = new Conn();
                ResultSet rs = conn.s.executeQuery("select * from reservation where PNR = '" + pnr.getText() + "'");
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No Information Found");
                    return;
                }
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == download) {
            try {
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No data to download.");
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Ticket As");
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToSave = fileChooser.getSelectedFile();
                    java.io.FileWriter fw = new java.io.FileWriter(fileToSave + ".txt");

                    for (int i = 0; i < table.getColumnCount(); i++) {
                        fw.write(table.getColumnName(i) + "\t");
                    }
                    fw.write("\n");

                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            fw.write(table.getValueAt(i, j).toString() + "\t");
                        }
                        fw.write("\n");
                    }

                    fw.close();
                    JOptionPane.showMessageDialog(null, "Ticket downloaded successfully!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
