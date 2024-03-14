package client;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import Service.UyeolService;

import Service.GirisyapServices;
import dto.UyeolRequest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;


/**
 * @author busrayral
 */




public class Kullanici extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Kullanici frame = new Kullanici();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Kullanici() {
    	
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        setBounds(450, 200, 500, 312); //düzenlendi
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 160));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(212, 212, 212));
        panel.setBounds(10, 11, 464, 118);
        contentPane.add(panel);
        panel.setLayout(null);

        JButton btn_uye = new JButton("ÜYE OL");
        btn_uye.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_uye.setForeground(new Color(74, 74, 74));
        btn_uye.setBackground(new Color(228, 228, 228));
        btn_uye.addActionListener(new UyeolListener().listen()); 
        btn_uye.setBounds(151, 30, 161, 57);
        panel.add(btn_uye);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(212, 212, 212));
        panel_1.setBounds(10, 140, 464, 122);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JButton btn_giris = new JButton("GİRİŞ YAP");
        btn_giris.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_giris.setForeground(new Color(74, 74, 74));
        btn_giris.setBackground(new Color(228, 228, 228));
        btn_giris.addActionListener(new GirisListener().listen()); 
        btn_giris.setBounds(155, 31, 161, 58);
        panel_1.add(btn_giris);
    }

    private static class UyeolListener {

        private final UyeolService uyeolService = new UyeolService();

        public ActionListener listen() {
            return e -> {

                String name = JOptionPane.showInputDialog("Adınızı giriniz");
                String surname = JOptionPane.showInputDialog("Soyadınızı giriniz");
                String email = JOptionPane.showInputDialog("Emailinizi giriniz");
                String password = JOptionPane.showInputDialog("Şifrenizi giriniz");
                String bakiyestr=JOptionPane.showInputDialog ("Bakiyenizi giriniz");
                Double bakiye= Double.valueOf(bakiyestr);
                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || bakiyestr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz");
                    
                } 
                
                else { 
                    try {
                    	
						uyeolService.uyeol(new UyeolRequest.Builder()
                                .name(name)
                                .surname(surname)
                                .email(email)
                                .password(password)
                                .bakiye(bakiye)
                                .build());
                        System.out.println("dsfsf");
                        

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "HATA OLUŞTU");
                        ex.printStackTrace();
                        JFrame frame = new Kullanici();
                        frame.setVisible(true);
                    }
                    JOptionPane.showMessageDialog(null, "Kayıt başarılı");
                    JFrame frame = new Kullanici();
                    frame.setVisible(true);
 
                   
                }
            };

        }
    }
    

    private static class GirisListener {

        private final GirisyapServices girisyapService = new GirisyapServices();

        public ActionListener listen() {
            return e -> {
            	String email = JOptionPane.showInputDialog("Emailinizi giriniz");
                String password = JOptionPane.showInputDialog("Şifrenizi giriniz");
                try {
					girisyapService.girisyap(email, password);
					Kullanici kullanici = new Kullanici();
				} catch (Exception e1) {
					
					e1.printStackTrace();
					
				}
               //JFrame frame = new DashBoard();
                
              //  frame.setVisible(true);;
                
            };

        } 
    }
    }
    
    
    
    
    
    
