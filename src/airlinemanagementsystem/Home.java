package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Home extends JFrame implements ActionListener {

    public Home() {
        setTitle("Airline Management System - Home");
        setLayout(null);

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1600, 800);
        add(image);

        // Heading (centered)
        JLabel heading = new JLabel("✈ AEROVISTA COMMAND WELCOMES YOU ✈", JLabel.CENTER);
        heading.setBounds(300, 80, 1000, 60);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 38));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 0, 0, 120));
        heading.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        image.add(heading);

        // NavBar Panel
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        navbar.setBackground(new Color(25, 42, 86));
        navbar.setBounds(0, 0, 1600, 60);
        image.add(navbar);

        String[] navItems = {
            "Flight Details",
            "Add Customer Details",
            "Book Flight",
            "Journey Details",
            "Cancel Ticket",
            "Boarding Pass"
        };

        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(52, 73, 94));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(150, 35));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            btn.setContentAreaFilled(false);
            btn.setUI(new RoundedButtonUI());

            // Hover effect
            Color defaultColor = new Color(52, 73, 94);
            Color hoverColor = new Color(93, 173, 226);

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(hoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(defaultColor);
                }
            });

            btn.addActionListener(this);
            navbar.add(btn);
        }

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Flight Details")) {
            new FlightInfo();
        } else if (text.equals("Book Flight")) {
            new BookFlight();
        } else if (text.equals("Journey Details")) {
            new JourneyDetails();
        } else if (text.equals("Cancel Ticket")) {
            new Cancel();
        } else if (text.equals("Boarding Pass")) {
            new BoardingPass();  
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}

// RoundedButtonUI class for rounded corners
class RoundedButtonUI extends BasicButtonUI {
    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        c.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 20, 20);
    }
}
