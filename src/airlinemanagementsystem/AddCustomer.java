package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;

    public AddCustomer() {
        setTitle("Add Customer - AeroVista Command");
        setLayout(null);
        getContentPane().setBackground(new Color(220, 230, 241)); // Background soft blue

        // 🔷 Rounded Form Panel
        JPanel formPanel = new RoundedPanel(30, new Color(245, 245, 245));
        formPanel.setLayout(null);
        formPanel.setBounds(40, 40, 400, 480);
        add(formPanel);

        JLabel heading = new JLabel("Add Customer Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(33, 47, 61));
        heading.setBounds(60, 10, 300, 40);
        formPanel.add(heading);

        String[] labels = {"Name", "Nationality", "Aadhar No.", "Address", "Gender", "Phone"};
        int y = 70;

        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lbl.setBounds(30, y, 120, 25);
            formPanel.add(lbl);
            y += 50;
        }

        tfname = createRoundedField(160, 70);
        formPanel.add(tfname);

        tfnationality = createRoundedField(160, 120);
        formPanel.add(tfnationality);

        tfaadhar = createRoundedField(160, 170);
        formPanel.add(tfaadhar);

        tfaddress = createRoundedField(160, 220);
        formPanel.add(tfaddress);

        // Gender
        rbmale = new JRadioButton("Male");
        rbmale.setBounds(160, 270, 70, 25);
        rbmale.setBackground(new Color(245, 245, 245));
        formPanel.add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(240, 270, 100, 25);
        rbfemale.setBackground(new Color(245, 245, 245));
        formPanel.add(rbfemale);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbmale);
        genderGroup.add(rbfemale);

        tfphone = createRoundedField(160, 320);
        formPanel.add(tfphone);

        // 🔘 Rounded Save Button
        JButton save = new JButton("Save") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        save.setBounds(125, 390, 150, 45);
        save.setBackground(new Color(40, 116, 166));
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Segoe UI", Font.BOLD, 16));
        save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        save.setFocusPainted(false);
        save.setContentAreaFilled(false);
        save.setOpaque(false);
        save.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        save.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                save.setBackground(new Color(30, 100, 140));
                save.repaint();
            }

            public void mouseExited(MouseEvent e) {
                save.setBackground(new Color(40, 116, 166));
                save.repaint();
            }
        });

        save.addActionListener(this);
        formPanel.add(save);

        // Image/Icon on Right
        ImageIcon rightImg = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/passanger.png"));
        Image i4 = rightImg.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        JLabel lblimage = new JLabel(new ImageIcon(i4));
        lblimage.setBounds(480, 100, 350, 400);
        add(lblimage);

        setSize(900, 600);
        setLocation(300, 150);
        setVisible(true);
    }

    // Utility: Rounded Input Field
    private JTextField createRoundedField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 180, 30);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    // Insert data on click
public void actionPerformed(ActionEvent ae) {
    String name = tfname.getText();
    String nationality = tfnationality.getText();
    String phone = tfphone.getText();
    String address = tfaddress.getText();
    String aadhar = tfaadhar.getText();
    String gender = rbmale.isSelected() ? "Male" : rbfemale.isSelected() ? "Female" : "";

    try {
        // ✅ Validation: Name length check
        if (name.length() > 100) {
            JOptionPane.showMessageDialog(null, "Name is too long. Please limit it to 100 characters.");
            return;
        }

        Conn conn = new Conn();
        String query = "insert into passenger(name, nationality, phone, address, aadhar, gender) values('" + name + "', '" + nationality + "', '" + phone + "', '" + address + "', '" + aadhar + "', '" + gender + "')";
        conn.s.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
        setVisible(false);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        new AddCustomer();
    }

    // 🔘 RoundedPanel Class
    class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }
    }
}
