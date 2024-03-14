package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbconnet.DatabaseManager;
import dto.UyeolRequest;




/**
 * @author busrayral
 */


public class UyeolService {
public void uyeol (UyeolRequest request) throws SQLException{
	execute(request);
}
private void execute (UyeolRequest request ) throws SQLException{
	
	try (Connection connection = DatabaseManager.connect()) {
     
        checkEmailUsed(request.getEmail(), connection);

       
        String insertQuery = "INSERT INTO tbl_kullanici (name, surname, email, password, bakiye) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, request.getName());
            preparedStatement.setString(2, request.getSurname());
            preparedStatement.setString(3, request.getEmail());
            preparedStatement.setString(4, request.getPassword());
            preparedStatement.setDouble(5, request.getBakiye());

            preparedStatement.executeUpdate();
        }
    }
}private void checkEmailUsed(String email, Connection connection) throws SQLException {
    
    String query = "SELECT email FROM tbl_kullanici WHERE email = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                throw new IllegalArgumentException("This email is already used");
            }
        }
    }
}
}
