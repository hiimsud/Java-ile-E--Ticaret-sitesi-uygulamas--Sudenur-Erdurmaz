package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbconnet.DatabaseManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * @author busrayral
 */



public class CuzdanClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField ad;
	private JTextField soyad;
	private JTextField mail;
	private JTextField bakiye;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
    private CuzdanClient cuzdanClient;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CuzdanClient frame = new CuzdanClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
   

	 public JTextField getTextField() {
	        return textField;
	    }
	 
	 public JTextField getadField() {
	        return ad;
	    }
	 public JTextField getsoyadField() {
	        return soyad;
	    }
	 public JTextField getmailField() {
	        return mail;
	    }
	 

	/**
	 * Create the frame.
	 */
	public CuzdanClient() {
		
		
		// Buton oluşturuldu geri yapmak için
				 final BackButton backButton = new BackButton();
			        final JMenuBar jMenuBar = backButton.menuBar;
			        setJMenuBar(jMenuBar);
			        backButton.initializeMenubar();
		
		 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 500, 312); //düzenlendi
		contentPane = new JPanel();
		contentPane.setBackground(new Color(212, 212, 212));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(66, 195, 142, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Bakiye :");
		lblNewLabel.setForeground(new Color(23, 23, 23));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 191, 64, 27);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 160));
		panel.setBounds(0, 0, 484, 61);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Hesap Bilgilerim");
		lblNewLabel_1.setForeground(new Color(23, 23, 23));
		lblNewLabel_1.setBounds(117, 11, 317, 46);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 35));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Ad     : ");
		lblNewLabel_2.setForeground(new Color(23, 23, 23));
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 104, 49, 14);
		contentPane.add(lblNewLabel_2);
		
		ad = new JTextField();
		ad.setBounds(66, 102, 142, 20);
		contentPane.add(ad);
		ad.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Soyad :");
		lblNewLabel_3.setForeground(new Color(23, 23, 23));
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 135, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		soyad = new JTextField();
		soyad.setBackground(new Color(255, 255, 255));
		soyad.setBounds(66, 133, 142, 20);
		contentPane.add(soyad);
		soyad.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Mail   :");
		lblNewLabel_4.setForeground(new Color(23, 23, 23));
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_4.setBounds(10, 166, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		mail = new JTextField();
		mail.setBounds(66, 164, 142, 20);
		contentPane.add(mail);
		mail.setColumns(10);
		
		JButton btn_guncelle = new JButton("GUNCELLE");
		
		btn_guncelle.setBackground(new Color(228, 228, 228));
		btn_guncelle.setForeground(new Color(23, 23, 23));
		btn_guncelle.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_guncelle.setBounds(252, 175, 137, 59);
		contentPane.add(btn_guncelle);
		btn_guncelle.addActionListener(new ActionListener() {
			
			 public void actionPerformed(ActionEvent e) {
			     
			        try (Connection connection = DatabaseManager.connect()) {
			            
			            String updateQuery = "UPDATE tbl_kullanici SET name = ?, surname = ?, email = ? WHERE ID = ?";
			            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
			                
			                String yeniAd = ad.getText();
			                String yeniSoyad = soyad.getText();
			                String yeniMail = mail.getText();
			                double mevcutBakiye = getCurrrentBakiyeFromDatabase(connection, SepetClient.userID);
			                double yeniBakiye= Double.valueOf(textField.getText());
                            
			                if (mevcutBakiye != yeniBakiye) {
			                    JOptionPane.showMessageDialog(null, "Bakiye güncellenemez");
			                    return; // Bakiye güncellenemez,
			                }
			          
			                updateStatement.setString(1, yeniAd);
			                updateStatement.setString(2, yeniSoyad);
			                updateStatement.setString(3, yeniMail);
			                updateStatement.setLong(4, SepetClient.userID);

			              
			                int affectedRows = updateStatement.executeUpdate();

			                if (affectedRows > 0) {
			                    System.out.println("Güncelleme başarılı");
			                   
			                    updateKullaniciBilgileri();
			                } else {
			                    System.out.println("Güncelleme başarısız");
			                }
			            } 
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			        }
			    }
			
			
		}
		);
		
		
		
		
	
		
		textField_1 = new JTextField();
		textField_1.setBackground(new Color(0, 255, 0));
		textField_1.setBounds(0, 61, 484, 7);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 254, 424, 0);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBackground(new Color(0, 255, 0));
		textField_3.setBounds(0, 245, 484, 26);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBackground(new Color(255, 0, 128));
		textField_4.setBounds(324, 89, 160, 7);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBackground(new Color(128, 0, 128));
		textField_5.setBounds(347, 108, 137, 7);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBackground(new Color(255, 128, 0));
		textField_6.setBounds(382, 126, 102, 7);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBackground(new Color(255, 255, 0));
		textField_7.setBounds(412, 144, 72, 7);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
	}
	
	private void updateKullaniciBilgileri() {
	    try (Connection connection = DatabaseManager.connect()) {
	        String query = "SELECT name, surname, email, bakiye FROM tbl_kullanici WHERE ID = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setLong(1, SepetClient.userID);

	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                String ad = resultSet.getString("name");
	                String soyad = resultSet.getString("surname");
	                String mail = resultSet.getString("email");
	                double bakiye = resultSet.getDouble("bakiye");

	      
	                textField.setText(String.valueOf(bakiye));

	                cuzdanClient= new CuzdanClient();
					cuzdanClient.getTextField().setText(String.valueOf(bakiye));
                    cuzdanClient.getadField().setText(ad);
                    cuzdanClient.getsoyadField().setText(soyad);
                    cuzdanClient.getmailField().setText(mail);

	           setVisible(true);
	           JOptionPane.showMessageDialog(null,"Bilgileriniz Güncellenmiştir.");
	               
	              
	            } else {
	               
	                textField.setText("Kullanıcı bulunamadı");
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private double getCurrrentBakiyeFromDatabase(Connection connection, long userID) throws SQLException {
	    String query = "SELECT bakiye FROM tbl_kullanici WHERE ID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setLong(1, userID);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getDouble("bakiye");
	            }
	        }
	    }
	    return 0; // Varsayılan değer, hata durumunda
	}
	
	
}
