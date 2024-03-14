package Service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.SepetClient;
import dbconnet.DatabaseManager;
import client.DashBoard;
import client.Kullanici;
import dto.UyeolRequest;

/**
 * @author busrayral
 */

public final class GirisyapServices {

	public void girisyap(String email, String password) throws Exception {

		try (Connection connection = DatabaseManager.connect()) {
			String sql = "SELECT * FROM tbl_kullanici WHERE email = ? AND password = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, email);
				statement.setString(2, password);

				try (ResultSet resultSet = statement.executeQuery()) {

					if (resultSet.next()) {
						SepetClient.userID = Integer.parseInt(resultSet.getString("ID")); //her yerde veritabanı sorgusu yamak yerine ıdyi userID setledik diğer kısımlarda işlemlerimizi kolaylaştırdık.
						JFrame frame = new DashBoard(); /// yeni eklendi
						frame.setVisible(true); /// yeni eklendi
						System.out.println("Giriş başarılı");

					}

					else {
						JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre hatalı", "Hata",
								JOptionPane.ERROR_MESSAGE);

						throw new Exception("Kullanıcı adı veya şifre hatalı");

					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
//kullanılmadı.
	public static List<UyeolRequest> getAllUsersFromDatabase() {
		List<UyeolRequest> userList = new ArrayList<>();

		try (Connection connection = DatabaseManager.connect()) {
			String sql = "SELECT * FROM tbl_kullanici";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						UyeolRequest user = new UyeolRequest.Builder().name(resultSet.getString("name"))
								.surname(resultSet.getString("surname")).email(resultSet.getString("email"))
								.password(resultSet.getString("password")).build();
						userList.add(user);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;

	}
}
