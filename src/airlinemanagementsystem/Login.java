package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, close, togglePasswordBtn;
    JTextField tfusername;
    JPasswordField tfpassword;
    boolean isPasswordVisible = false;

    public Login() {
        setTitle("Airline Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 430);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(225, 240, 255)); // Soft light blue

        // Company Title
        JLabel companyLabel = new JLabel("✈ AEROVISTA COMMAND", JLabel.CENTER);
        companyLabel.setBounds(40, 10, 380, 40);
        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        companyLabel.setOpaque(true);
        companyLabel.setBackground(new Color(0, 102, 204)); // Navy blue
        companyLabel.setForeground(Color.WHITE);
        companyLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(companyLabel);

        // Rounded white panel (card look)
        JPanel cardPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        cardPanel.setLayout(null);
        cardPanel.setBounds(50, 60, 340, 320);
        cardPanel.setOpaque(false);
        add(cardPanel);

        JLabel heading = new JLabel("Login Portal", JLabel.CENTER);
        heading.setBounds(70, 10, 200, 30);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(new Color(0, 51, 102));
        cardPanel.add(heading);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(20, 60, 100, 25);
        lblusername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblusername.setForeground(new Color(0, 51, 102));
        cardPanel.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(120, 60, 180, 30);
        tfusername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfusername.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        cardPanel.add(tfusername);

        JLabel lblpassword = new JLabel("Password:");
        lblpassword.setBounds(20, 110, 100, 25);
        lblpassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblpassword.setForeground(new Color(0, 51, 102));
        cardPanel.add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(120, 110, 140, 30);
        tfpassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfpassword.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        cardPanel.add(tfpassword);

        togglePasswordBtn = new JButton("Show");
        togglePasswordBtn.setBounds(265, 110, 60, 30);
        togglePasswordBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        togglePasswordBtn.setFocusPainted(false);
        togglePasswordBtn.setBackground(new Color(230, 230, 255));
        togglePasswordBtn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 255)));
        togglePasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordBtn.addActionListener(e -> {
            if (isPasswordVisible) {
                tfpassword.setEchoChar('•');
                togglePasswordBtn.setText("Show");
            } else {
                tfpassword.setEchoChar((char) 0);
                togglePasswordBtn.setText("Hide");
            }
            isPasswordVisible = !isPasswordVisible;
        });
        cardPanel.add(togglePasswordBtn);

        // Buttons
        reset = new JButton("Reset");
        reset.setBounds(10, 200, 90, 35);
        styleRoundedButton(reset, new Color(0, 153, 153), new Color(0, 180, 180));
        reset.addActionListener(this);
        cardPanel.add(reset);

        submit = new JButton("Login");
        submit.setBounds(120, 200, 90, 35);
        styleRoundedButton(submit, new Color(0, 123, 255), new Color(30, 144, 255));
        submit.addActionListener(this);
        cardPanel.add(submit);

        close = new JButton("Close");
        close.setBounds(230, 200, 90, 35);
        styleRoundedButton(close, new Color(204, 0, 0), new Color(255, 0, 0));
        close.addActionListener(this);
        cardPanel.add(close);

        setVisible(true);
    }

    // Button style with hover
    private void styleRoundedButton(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 20, 20);
                super.paint(g, c);
            }
        });

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = tfpassword.getText();

            try {
                Conn c = new Conn();
                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
