package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import javax.swing.table.JTableHeader;

public class FlightInfo extends JFrame {

    public FlightInfo() {

        setTitle("✈ Flight Information - AeroVista Command ✈");
        setLayout(new BorderLayout());

        // Top Heading
        JLabel heading = new JLabel("FLIGHT INFORMATION", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        heading.setOpaque(true);
        heading.setBackground(new Color(44, 62, 80)); // Dark blueish
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(heading, BorderLayout.NORTH);

        // Table to display data
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setGridColor(new Color(189, 195, 199));
        table.setForeground(new Color(44, 62, 80));
        table.setBackground(new Color(245, 245, 245));
        table.setSelectionBackground(new Color(93, 173, 226));
        table.setSelectionForeground(Color.WHITE);

        // Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(new Color(52, 152, 219)); // Nice blue
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery(
                "SELECT f.f_code AS 'Flight Code', f.f_name AS 'Flight Name', " +
                "f.source AS 'Source', f.destination AS 'Destination', fare.price AS 'Fare (₹)' " +
                "FROM flight f LEFT JOIN fare ON f.f_code = fare.f_code"
            );
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        add(jsp, BorderLayout.CENTER);

        // Frame Settings
        getContentPane().setBackground(Color.WHITE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
