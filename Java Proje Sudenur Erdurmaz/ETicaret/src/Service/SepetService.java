package Service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.SepetClient;
import dbconnet.DatabaseManager;
import dto.UrunDto;
import client.Urun;

/**
 * @author busrayral 
 * 
 */


     public class SepetService {


    	  public static List<UrunDto> getUrunList() {
    	        List<UrunDto> userList = new ArrayList<>();

    	        try (Connection connection = DatabaseManager.connect()) {
    	            
    	            
    	            String sql = "SELECT U.UrunAdi AS UrunAdi, U.urunTuru, U.urunMiktar AS urunMiktar, U.urunID, U.urunAdi AS UrunAdi, SU.Adet,  U.urunBirimFiyat, U.urunBirimFiyat * SU.Adet AS ToplamFiyat\r\n"
    	            		+ "FROM SepetUrunleri SU\r\n"
    	            		+ "JOIN Sepet S ON SU.SepetID = S.SepetID\r\n"
    	            		+ "JOIN tbl_urunler U ON SU.UrunID = U.UrunID\r\n"
    	            		+ "WHERE S.KullaniciID = ?";
    	            		
    	            
    	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    	            	statement.setInt(1, SepetClient.userID);


    	                try (ResultSet resultSet = statement.executeQuery()) {
    	                    while (resultSet.next()) {
    	                    	System.out.println("vmgjöhbkjçvhmsdv");
    	                    	UrunDto user =new UrunDto.Builder()
    	                    			.urunTuru(resultSet.getString("urunTuru"))
    	                                .urunAdi(resultSet.getString("UrunAdi"))
    	                                .urunMiktar(resultSet.getString("urunMiktar"))
    	                                .urunBirimFiyat(resultSet.getString("urunBirimFiyat"))
    	                                .urunID(Integer.parseInt(resultSet.getString("urunID")))
    	                                .build();
    	               
    	                        userList.add(user);
    	                    }
    	                }
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace(); 
    	        }
    	        return userList;
    	  }
    	  
    	  
    	  public static boolean tamamlaOdeme(double odemeMiktari) {
    	        try (Connection connection = DatabaseManager.connect()) {
    	            String bakiyeKontrolQuery = "SELECT bakiye FROM tbl_kullanici WHERE KullaniciID = ?";
    	            String bakiyeDusurQuery = "UPDATE tbl_kullanici SET bakiye = bakiye - ? WHERE KullaniciID = ?";

    	            try (PreparedStatement kontrolStatement = connection.prepareStatement(bakiyeKontrolQuery);
    	                 PreparedStatement dusurStatement = connection.prepareStatement(bakiyeDusurQuery)) {
    	                kontrolStatement.setInt(1, SepetClient.userID);
    	                ResultSet resultSet = kontrolStatement.executeQuery();

    	                if (resultSet.next()) {
    	                    double bakiye = resultSet.getDouble("bakiye");

    	                    if (bakiye >= odemeMiktari) {
    	                        // Ödeme yapılabiliyor, bakiyeden düş
    	                        dusurStatement.setDouble(1, odemeMiktari);
    	                        dusurStatement.setInt(2, SepetClient.userID);
    	                        dusurStatement.executeUpdate();
    	                        return true;
    	                    } else {
    	                        // Bakiye yetersiz, CuzdanClient'a yönlendir
    	                        return false;
    	                    }
    	                }
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        return false;
    	  }
    	        
    	  ///bu kısım kullanılıyor//
    	  public double calculateTotalPrice(List<UrunDto> sepetItems) {
    	        double totalPrice = 0.0;

    	        for (UrunDto urun : sepetItems) {
    	            double birimFiyat = Double.parseDouble(urun.getUrunBirimFiyat());
    	            int miktar = Integer.parseInt(urun.getUrunMiktar());
    	            totalPrice += birimFiyat * miktar;
    	        }

    	        return totalPrice;
    	    }

    	  
    	  public static void sepeteEkle(Urun selectedUrun, int miktar) {
    	        try (Connection connection = DatabaseManager.connect()) {
    	            String sepetEkleQuery = "INSERT INTO Sepet (KullaniciID) VALUES (?)";
    	            try (PreparedStatement sepetEkleStatement = connection.prepareStatement(sepetEkleQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
    	                sepetEkleStatement.setInt(1, SepetClient.userID);
    	                sepetEkleStatement.executeUpdate();

    	                int sepetID;
    	                try (var generatedKeys = sepetEkleStatement.getGeneratedKeys()) {
    	                    if (generatedKeys.next()) {
    	                        sepetID = generatedKeys.getInt(1);
    	                    } else {
    	                        throw new SQLException("Sepet Oluşturulmadı, ID bulunamadı.");
    	                    }
    	                }

    	                String sepetUrunleriEkleQuery = "INSERT INTO SepetUrunleri (SepetID, UrunID, Adet) VALUES (?, ?, ?)";
    	                try (PreparedStatement sepetUrunleriEkleStatement = connection.prepareStatement(sepetUrunleriEkleQuery)) {
    	                    sepetUrunleriEkleStatement.setInt(1, sepetID);
    	                    sepetUrunleriEkleStatement.setInt(2, selectedUrun.getId());
    	                    sepetUrunleriEkleStatement.setInt(3, miktar);
    	                    sepetUrunleriEkleStatement.executeUpdate();
    	                }
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	            JOptionPane.showMessageDialog(null, "Ürün sepete eklenirken bir hata oluştu.");
    	        }
    	    }
    	  
    	  
    	  public static void delete(List<UrunDto> sepetItems) {
    		  delete(sepetItems, null);
    	  }
    	  
    		public static void delete(List<UrunDto> sepetItems, UrunDto uyeDto) {
    			
    			sepetItems.remove(uyeDto);
    		    
         
    		    if(uyeDto == null ) {
    		    	try (Connection connection = DatabaseManager.connect()) {
        		        String sql = "DELETE FROM SepetUrunleri\r\n"
        		        		+ "WHERE SepetID IN (SELECT SepetID FROM Sepet WHERE KullaniciID = ?);";
        		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        		            statement.setInt(1, SepetClient.userID);
        		            statement.executeUpdate();
        		        }
        		        
        		    } catch (SQLException e) {
        		        e.printStackTrace();
        		    }
    		    }else {
    		    	try (Connection connection = DatabaseManager.connect()) {
        		        String sql = "DELETE FROM SepetUrunleri\r\n"
        		        		+ "WHERE SepetID IN (SELECT SepetID FROM Sepet WHERE KullaniciID = ?)\r\n"
        		        		+ "  AND UrunID = ?;" ;
        		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        		            statement.setInt(1, SepetClient.userID);
        		            statement.setInt(2, uyeDto.getId());
        		            statement.executeUpdate();
        		        }
        		        
        		    } catch (SQLException e) {
        		        e.printStackTrace();
        		    }
    		    	
    		    }
    		    
    		    SepetClient sepetClient = new SepetClient();
    		    sepetClient.updateTable(sepetItems);
    		    sepetClient.setVisible(true);
    		    
    		}
    		
   
    	  public void updateTotalPriceTextField(JTextField textField, double totalPrice) {
  	        textField.setText(String.format("%.2f", totalPrice));
  	    }
	
	
	public void fillSepetTable(JTable table, Vector<Vector<Object>> sepetItems) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        for (Vector<Object> row : sepetItems) {
            model.addRow(row);
        }
    }
	
	

	
}
     
     
     
     
