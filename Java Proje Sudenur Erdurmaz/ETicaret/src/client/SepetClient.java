package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Service.SepetService;
import dbconnet.DatabaseManager;
import dto.UrunDto;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SepetClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	public static int userID;

	
	/**
	 * @author busrayral
	 */
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SepetClient frame = new SepetClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public JTable getTable() {
        return this.table;
    }

    public JTextField getTextField() {
        return this.textField;
    }
    
    private DefaultTableModel tableModel;
    private JTextField textField_1;

	
	public SepetClient() {
		
		 final BackButton backButton = new BackButton();
	        final JMenuBar jMenuBar = backButton.menuBar;
	        setJMenuBar(jMenuBar);
	        backButton.initializeMenubar();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 500, 312); //düzenlendi
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBackground(new Color(212,212, 212));
		table.setBounds(10, 11, 477, 116);
		contentPane.add(table);
		
	
		
		
		textField = new JTextField();
		textField.setBackground(new Color(228, 228, 228));
		textField.setBounds(312, 210, 148, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("TUTAR =");
		lblNewLabel.setForeground(new Color(228, 228, 228));
		lblNewLabel.setBackground(new Color(0, 0, 160));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(230, 208, 76, 34);
		contentPane.add(lblNewLabel);
		
		 tableModel = new DefaultTableModel();
	        tableModel.addColumn("Ürün Türü");
	        tableModel.addColumn("Ürün Adı");
	        tableModel.addColumn("Ürün Miktar");
	        tableModel.addColumn("Ürün Birim Fiyat");
	        tableModel.addColumn("Ürün ID");

	        
		
			this.getTable().setModel(tableModel);

		 JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(10, 24, 462, 134);
	        contentPane.add(scrollPane);
	        
	        JButton btn_sil = new JButton("ÜRÜNÜ TEMİZLE");
	        btn_sil.setForeground(new Color(255, 0, 0));
	        btn_sil.setBackground(new Color(255, 193, 193));
	        btn_sil.setBounds(89, 169, 135, 23);
	        contentPane.add(btn_sil);
	        
	        JButton btn_odeme = new JButton("ÖDEMEYİ TAMAMLA");
	        btn_odeme.setForeground(new Color(0, 64, 0));
	        btn_odeme.setBackground(new Color(53, 255, 53));
	        btn_odeme.setBounds(268, 169, 142, 23);
	        contentPane.add(btn_odeme);
	        
	        textField_1 = new JTextField();
	        textField_1.setBounds(99, 210, 100, 34);
	        contentPane.add(textField_1);
	        textField_1.setColumns(10);
	        
	        JLabel lblNewLabel_1 = new JLabel("Ürün ID =");
	        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
	        lblNewLabel_1.setForeground(new Color(228, 228, 228));
	        lblNewLabel_1.setBounds(17, 215, 86, 24);
	        contentPane.add(lblNewLabel_1);
	        
	        
	        btn_odeme.addActionListener(e ->{
	        	double totalPrice = calculateTotalPrice(SepetService.getUrunList());
	        	
	        	try (Connection connection = DatabaseManager.connect()) {
	                String sql = "SELECT * FROM tbl_kullanici WHERE ID = ? ";
	                try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                    statement.setInt(1, userID);
	            

	                    try (ResultSet resultSet = statement.executeQuery()) {
	                        if (resultSet.next()) {
	                        	Double bakiye = Double.valueOf(resultSet.getDouble("bakiye"));
	                        	
	                        	boolean enoughBakiye = (bakiye != null && bakiye > totalPrice);
	                        	
	                        	if(enoughBakiye) {
	                        		
	                        		
	                                double newBakiye = bakiye - totalPrice; //ödeme yapıldıktan sonraki bakiye
	                                
	                                String updateBakiyeSql = "UPDATE tbl_kullanici SET bakiye = ? WHERE ID = ?";
	                                
	                                try (PreparedStatement updateStatement = connection.prepareStatement(updateBakiyeSql)) {
	                                    updateStatement.setDouble(1, newBakiye);
	                                    updateStatement.setInt(2, userID);
	                                    updateStatement.executeUpdate();
	                                }
	                                
	                                SepetService.delete(SepetService.getUrunList());
	                                
	                	        	SepetClient sepetClient = new SepetClient();
	                	        	sepetClient.updateTable(SepetService.getUrunList());
	                                JOptionPane.showMessageDialog(SepetClient.this, "Ödeme başarıyla gerçekleştirildi.");

	                        	}
	                        	else  {
		                            JOptionPane.showMessageDialog(SepetClient.this, "Yetersiz bakiye. Lütfen bakiyenizi kontrol edin.");
		                            throw new Exception("Yetersiz Bakiye !");
	                            
	                        } 
	                        }
	                    }
	                
	                }
	        	} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        
	        	
	        	List<UrunDto> sepetItems = SepetService.getUrunList();
    		
	            SepetClient sepetClient = new SepetClient();
    		    sepetClient.updateTable(sepetItems);
    		    sepetClient.setVisible(true);
	     
	        	
	        });
	        
	        
	        
	        btn_sil.addActionListener(e -> {
	            String idText = textField_1.getText().trim(); // idtex değişkene atadık texfild gelen ıd.

	            if (idText.isEmpty()) {
	                System.out.println("Lütfen bir ID girin.");
	                return;
	            }

	            try {
	                int id = Integer.parseInt(idText); //int çevirdik textfild gelen textmetinsel..

	                boolean success = silUrun(id);

	                if (success) {
	                    System.out.println("Ürün başarıyla silindi.");
	                    JOptionPane.showMessageDialog(new JFrame(), "ürün silindi");
	                } else {
	                    System.out.println("Ürün silinemedi. Belirtilen ID bulunamadı.");
	                }
	            } catch (NumberFormatException ex) {
	                System.out.println("Geçersiz ID formatı.");
	            }
	        });
	        
	        
	        
		
	}
	
	public void updateTable(List<UrunDto> sepetItems) {
        SepetService sepetService = new SepetService();
        tableModel.setRowCount(0);

        for (UrunDto uyeDto : sepetItems) {
            List<Object> row = new ArrayList<>();

            row.add(uyeDto.getUrunAdi());
            row.add(uyeDto.getUrunTuru());
            row.add(uyeDto.getUrunMiktar());
            row.add(uyeDto.getUrunBirimFiyat());
            row.add(uyeDto.getId());

            int rowCount = tableModel.getRowCount();
            double birimFiyat = Double.parseDouble(uyeDto.getUrunBirimFiyat());
            int miktar = Integer.parseInt(uyeDto.getUrunMiktar());
            double rowTutar = birimFiyat * miktar;
            row.add(rowTutar);

            tableModel.addRow(row.toArray());
        }

        double tutar = SepetClient.calculateTotalPrice(sepetItems);
        sepetService.updateTotalPriceTextField(textField, tutar);
    }

	

    public static double calculateTotalPrice(List<UrunDto> sepetItems) {
        double totalPrice = 0.0;

        for (UrunDto urun : sepetItems) {
            double birimFiyat = Double.parseDouble(urun.getUrunBirimFiyat());
            int miktar = Integer.parseInt(urun.getUrunMiktar());
            totalPrice += birimFiyat * miktar;
        }

        return totalPrice;
    }
    
    private boolean silUrun(int id) {
    	

        List<UrunDto> sepetItems = SepetService.getUrunList();

        for (UrunDto uyeDto : sepetItems) {
    	    System.out.println("sepetItems geldi");

            if (uyeDto.getId() == id) {  // sepetteki ID urundekiID mi ait
    		    

                SepetService.delete(sepetItems, uyeDto); 
                
                return true;
            }
        }
	    System.out.println("False çalıştı");
        return false;
    }
    
    
    
    public void tamamlaOdeme(double odemeMiktari) {
        boolean odemeDurumu = SepetService.tamamlaOdeme(odemeMiktari);

        if (odemeDurumu) {
            // Ödeme başarılı, gerekli işlemleri yap
            JOptionPane.showMessageDialog(this, "Ödeme tamamlandı. Ürünler teslim edilecek.");
            // ... diğer işlemler ...
        } else {
            // Bakiye yetersiz, CuzdanClient'a yönlendir
            JOptionPane.showMessageDialog(this, "Bakiyeniz yetersiz. Lütfen bakiye düzenleme işlemleri için cüzdan ekranına yönlendiriliyorsunuz.");
            CuzdanClient cuzdanClient = new CuzdanClient();
            cuzdanClient.setVisible(true);
        }
    }
	
	
		
	
}
