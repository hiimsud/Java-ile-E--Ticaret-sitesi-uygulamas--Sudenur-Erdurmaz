// AdminPanelApp.java
package Administrator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbconnet.DatabaseManager;

public class adminpanel extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public adminpanel() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (AdminLoginFrame.checkAdminLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    //giriş başarılıysa adminmenü'ye giriş yap
                    adminmenu frameadminmenu= new adminmenu();
                    frameadminmenu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public static class AdminLoginFrame {
        public static boolean checkAdminLogin(String admin_ad, String admin_sifre) {
            try (Connection connection = DatabaseManager.connect()) {
                String sql = "SELECT * FROM admin WHERE admin_ad = ? AND admin_sifre = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, admin_ad);
                    statement.setString(2, admin_sifre);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Giriş başarılı");
                            return true;
                        } else {
                            System.out.println("Kullanıcı adı veya şifre hatalı");
                            return false;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new adminpanel();
            }
        });
    }
}