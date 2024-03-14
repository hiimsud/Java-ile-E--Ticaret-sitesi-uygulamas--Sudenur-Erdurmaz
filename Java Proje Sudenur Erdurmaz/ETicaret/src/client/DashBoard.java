package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.Client;

import Service.SepetService;
import dbconnet.DatabaseManager;
import dto.UrunDto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

/**
 * @author busrayral
 */



public class DashBoard extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	  private CuzdanClient cuzdanClient;
	  private String kullaniciAdi;


	

	/**
	 * @author busrayral
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashBoard frame = new DashBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	


	
	public DashBoard() {
		 cuzdanClient = new CuzdanClient();
		 
		 
			// Buton oluşturuldu geri yapmak için
		 final LogOutButton backButton = new LogOutButton();
	        final JMenuBar jMenuBar = backButton.menuBar2;
	        setJMenuBar(jMenuBar);
	        backButton.initializeMenubar2();


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 500, 312); //düzenlendi
		contentPane = new JPanel(); 
		contentPane.setBackground(new Color(0, 0, 160));
		contentPane.setForeground(new Color(0, 0, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(212, 212, 212));
		panel.setBounds(29, 24, 121, 214);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btn_urunler = new JButton("ÜRÜNLER");
		btn_urunler.setForeground(new Color(23, 23, 23));
		btn_urunler.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_urunler.setBackground(new Color(228, 228, 228));
		btn_urunler.setBounds(10, 22, 101, 170);
		panel.add(btn_urunler);
		
		btn_urunler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.UrunListeleme frame= new client.UrunListeleme();
                frame.setVisible(true);
			}
		
	});
	
		
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(212, 212, 212));
		panel_1.setBounds(184, 24, 121, 214);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		
		
		JButton btn_sepet = new JButton("SEPET");

		btn_sepet.setForeground(new Color(23, 23, 23));
		btn_sepet.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_sepet.setBackground(new Color(228, 228, 228));
		btn_sepet.addActionListener(new SepetListener().listen());
		btn_sepet.setBounds(10, 22, 101, 170);
		panel_1.add(btn_sepet);
		
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(212, 212, 212));
		panel_2.setBounds(336, 24, 121, 214);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btn_cuzdan = new JButton("CÜZDAN");
		btn_cuzdan.setForeground(new Color(23, 23, 23));
		btn_cuzdan.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_cuzdan.setBackground(new Color(228, 228, 228));
		btn_cuzdan.setBounds(10, 22, 101, 170);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
       
		panel_2.add(btn_cuzdan);
		
		  btn_cuzdan.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try (Connection connection = DatabaseManager.connect()) {
	                    String query = "SELECT * FROM tbl_kullanici WHERE ID = ? ";
	                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                        preparedStatement.setLong(1, SepetClient.userID);

	                        ResultSet resultSet = preparedStatement.executeQuery();
	                        if (resultSet.next()) {
	                        	
	                        	System.out.println("afdgsfhdgjfh");
	                        	
	                            String ad = resultSet.getString("name");
	                            String soyad = resultSet.getString("surname");
	                            String mail = resultSet.getString("email");
	                            Double bakiye = resultSet.getDouble("bakiye");
	                           
	                            cuzdanClient.getadField().setText(ad);
	                            cuzdanClient.getsoyadField().setText(soyad);
	                            cuzdanClient.getmailField().setText(mail);
	                            cuzdanClient.getTextField().setText(String.valueOf(bakiye));
	                            

	                        } else {
	                            cuzdanClient.getTextField().setText("Bakiye bulunamadı");
	                        }
	                    }
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	                cuzdanClient.setVisible(true);
	            }
	        });

		  
		  
		  
		  
		  
		  
		  

	}
	
	 
	

		
		
		
		
		
	

	

	public class SepetListener {
        private SepetService sepetService = new SepetService();

            public ActionListener listen() {
                    return e-> {

                
                SepetClient sepetClient = new SepetClient();

                List<UrunDto> sepetItems = sepetService.getUrunList();

                sepetClient.updateTable(sepetItems);
        
                 
                sepetClient.setVisible(true);
            };
        }
    }
	
     
	public void  DashboardFrame() {
        

        JButton sepetButton = new JButton("Sepet");
        
        SepetListener sepetListener = new SepetListener();
        sepetButton.addActionListener(sepetListener.listen());

        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
