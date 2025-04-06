package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate;
    JButton fetchButton, printButton;

    public BoardingPass() {
        getContentPane().setBackground(new Color(245, 250, 255));
        setLayout(null);

        JLabel heading = new JLabel("AEROVISTA COMMAND");
        heading.setBounds(340, 10, 500, 35);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setForeground(new Color(25, 55, 109));
        add(heading);

        JLabel subheading = new JLabel("\u2708 Boarding Pass");
        subheading.setBounds(380, 50, 300, 30);
        subheading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        subheading.setForeground(new Color(0, 102, 204));
        add(subheading);

        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        tfpnr.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(tfpnr);

        fetchButton = new JButton("Get Details");
        fetchButton.setBackground(new Color(25, 55, 109));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fetchButton.setBounds(390, 100, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        String[] labels = {"NAME", "NATIONALITY", "SRC", "DEST", "Flight Name", "Flight Code", "Date"};
        int[] yPos = {140, 180, 220, 220, 260, 260, 300};
        int[] xLabel = {60, 60, 60, 380, 60, 380, 60};
        int[] xValue = {220, 220, 220, 540, 220, 540, 220};

        JLabel[] staticLabels = new JLabel[labels.length];
        JLabel[] dynamicValues = {tfname = new JLabel(), tfnationality = new JLabel(), lblsrc = new JLabel(),
                lbldest = new JLabel(), labelfname = new JLabel(), labelfcode = new JLabel(),
                labeldate = new JLabel()};

        for (int i = 0; i < labels.length; i++) {
            staticLabels[i] = new JLabel(labels[i]);
            staticLabels[i].setBounds(xLabel[i], yPos[i], 150, 25);
            staticLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            add(staticLabels[i]);

            dynamicValues[i].setBounds(xValue[i], yPos[i], 200, 25);
            dynamicValues[i].setFont(new Font("Segoe UI", Font.BOLD, 15));
            dynamicValues[i].setForeground(new Color(50, 50, 50));
            add(dynamicValues[i]);
        }

        JLabel logoLabel = new JLabel("\u2708 AEROVISTA");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(new Color(25, 55, 109));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBounds(670, 50, 280, 60);
        logoLabel.setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 2, 5));
        add(logoLabel);

        // Print Button
        printButton = new JButton("Download PDF");
        printButton.setBounds(390, 350, 150, 30);
        printButton.setBackground(new Color(0, 123, 255));
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(printButton);
        
       printButton.addActionListener(e -> {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Boarding Pass PDF");
        fileChooser.setSelectedFile(new java.io.File("BoardingPass.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // PDF Creation
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            // ✅ heading name change kiya
            com.lowagie.text.Font headingFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 20, com.lowagie.text.Font.BOLD, new Color(0, 102, 204));
            Paragraph pdfHeading = new Paragraph("✈ BOARDING PASS - AEROVISTA\n\n", headingFont);
            pdfHeading.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(pdfHeading);

            // Table
            com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Color headerColor = new Color(25, 55, 109);
            com.lowagie.text.Font headerFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.WHITE);
            com.lowagie.text.Font cellFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.NORMAL, Color.BLACK);

            String[][] data = {
                {"PNR", tfpnr.getText()},
                {"Name", tfname.getText()},
                {"Nationality", tfnationality.getText()},
                {"From", lblsrc.getText()},
                {"To", lbldest.getText()},
                {"Flight Name", labelfname.getText()},
                {"Flight Code", labelfcode.getText()},
                {"Date", labeldate.getText()}
            };

            for (String[] row : data) {
                com.lowagie.text.pdf.PdfPCell cell1 = new com.lowagie.text.pdf.PdfPCell(new Paragraph(row[0], headerFont));
                cell1.setBackgroundColor(headerColor);
                cell1.setPadding(10);

                com.lowagie.text.pdf.PdfPCell cell2 = new com.lowagie.text.pdf.PdfPCell(new Paragraph(row[1], cellFont));
                cell2.setPadding(10);

                table.addCell(cell1);
                table.addCell(cell2);
            }

            doc.add(table);
            doc.close();

            JOptionPane.showMessageDialog(null, "PDF saved successfully at:\n" + filePath);
        }
    } catch (Exception ex) {
        ex.printStackTrace(); // Console mein full error aayega
        JOptionPane.showMessageDialog(null, "Error while saving PDF:\n" + ex.getMessage());
    }
});





        setSize(1000, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String pnr = tfpnr.getText();

        try {
            Conn conn = new Conn();
            String query = "select * from reservation where PNR = '" + pnr + "'";
            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfnationality.setText(rs.getString("nationality"));
                lblsrc.setText(rs.getString("src"));
                lbldest.setText(rs.getString("des"));
                labelfname.setText(rs.getString("flightname"));
                labelfcode.setText(rs.getString("flightcode"));
                labeldate.setText(rs.getString("ddate"));
            } else {
                JOptionPane.showMessageDialog(null, "Please enter correct PNR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
