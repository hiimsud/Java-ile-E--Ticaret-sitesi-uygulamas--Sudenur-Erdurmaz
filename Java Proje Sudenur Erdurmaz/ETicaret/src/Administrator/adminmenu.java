package Administrator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbconnet.DatabaseManager;


public class adminmenu extends JFrame{
    private DefaultTableModel tableModel;
    private JTable urunTable;
    private JTextField urunIDField, urunTuruField, urunAdiField, urunMiktarField, urunBirimFiyatField, urunRenkField;

    public adminmenu()  {
        setTitle("Admin Menu");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tablo modeli oluştur
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Urun ID");
        tableModel.addColumn("Urun Turu");
        tableModel.addColumn("Urun Adi");
        tableModel.addColumn("Urun Miktar");
        tableModel.addColumn("Urun Birim Fiyat");
        tableModel.addColumn("Urun Renk");

        urunTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(urunTable);

        // Text alanları
        urunIDField = new JTextField(20);
        urunTuruField = new JTextField(20);
        urunAdiField = new JTextField(20);
        urunMiktarField = new JTextField(20);
        urunBirimFiyatField = new JTextField(20);
        urunRenkField = new JTextField(20);

        // Butonlar
        JButton ekleButton = new JButton("Ekle");
        JButton silButton = new JButton("Sil");
        JButton duzenleButton = new JButton("Düzenle");

        // Butonlara action listener ekle
        ekleButton.addActionListener(e -> urunEkle());
        silButton.addActionListener(e -> urunSil());
        duzenleButton.addActionListener(e -> urunDuzenle());

        // Tablodan bir satır seçildiğinde loadSelectedData metodunu çağır
        urunTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedData();
            }
        });

        // Panel ve Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("   Urun ID:"));
        inputPanel.add(urunIDField);
        inputPanel.add(new JLabel("   Urun Turu:"));
        inputPanel.add(urunTuruField);
        inputPanel.add(new JLabel("   Urun Adi:"));
        inputPanel.add(urunAdiField);
        inputPanel.add(new JLabel("   Urun Miktar:"));
        inputPanel.add(urunMiktarField);
        inputPanel.add(new JLabel("   Urun Birim Fiyat:"));
        inputPanel.add(urunBirimFiyatField);
        inputPanel.add(new JLabel("   Urun Renk:"));
        inputPanel.add(urunRenkField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ekleButton);
        buttonPanel.add(silButton);
        buttonPanel.add(duzenleButton);

        panel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);

        // UrunGoruntule metodunu burada çağır
        urunGoruntule();
    }

    private void urunGoruntule() {
        // Mevcut verileri temizle
        tableModel.setRowCount(0);

        try (Connection connection = dbconnet.DatabaseManager.connect()) {
            String sql = "SELECT * FROM tbl_urunler";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String urunID = resultSet.getString("urunID");
                        String urunTuru = resultSet.getString("urunTuru");
                        String urunAdi = resultSet.getString("urunAdi");
                        Integer urunMiktar = resultSet.getInt("urunMiktar");
                        Double urunBirimFiyat = resultSet.getDouble("urunBirimFiyat");
                        String urunRenk = resultSet.getString("urunRenk");

                        // Yeni bir satır ekle
                        Object[] row = {urunID, urunTuru, urunAdi, urunMiktar, urunBirimFiyat, urunRenk};
                        tableModel.addRow(row);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSelectedData() {
        int selectedRow = urunTable.getSelectedRow();
        if (selectedRow != -1) {
            urunIDField.setText((String) urunTable.getValueAt(selectedRow, 0));
            urunTuruField.setText((String) urunTable.getValueAt(selectedRow, 1));
            urunAdiField.setText((String) urunTable.getValueAt(selectedRow, 2));
            urunMiktarField.setText(urunTable.getValueAt(selectedRow, 3).toString());
            urunBirimFiyatField.setText(urunTable.getValueAt(selectedRow, 4).toString());
            urunRenkField.setText((String) urunTable.getValueAt(selectedRow, 5));
        }
    }

    private void urunEkle() {
        String urunID = urunIDField.getText();
        String urunTuru = urunTuruField.getText();
        String urunAdi = urunAdiField.getText();
        String urunMiktar = urunMiktarField.getText();
        String urunBirimFiyat = urunBirimFiyatField.getText();
        String urunRenk = urunRenkField.getText();

        try (Connection connection = dbconnet.DatabaseManager.connect()) {
            String sql = "INSERT INTO tbl_urunler (urunID, urunTuru, urunAdi, urunMiktar, urunBirimFiyat, urunRenk) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, urunID);
                statement.setString(2, urunTuru);
                statement.setString(3, urunAdi);
                statement.setInt(4, Integer.parseInt(urunMiktar));
                statement.setDouble(5, Double.parseDouble(urunBirimFiyat));
                statement.setString(6, urunRenk);

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Ürün başarıyla eklendi.");
                    urunGoruntule(); // Tabloyu güncelle
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("SQL Hatası: " + e.getMessage());
            }
        } catch (SQLException e) {
            // Connection kapatılırken hata oluştu
            e.printStackTrace();
            System.err.println("Connection kapatılırken hata oluştu: " + e.getMessage());
        }

    }

    private void urunSil() {
        int selectedRow = urunTable.getSelectedRow();
        if (selectedRow != -1) {
            String urunID = (String) urunTable.getValueAt(selectedRow, 0);

            try (Connection connection = dbconnet.DatabaseManager.connect()) {
                String sql = "DELETE FROM tbl_urunler WHERE urunID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, urunID);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Ürün başarıyla silindi.");
                        urunGoruntule(); // Tabloyu güncelle
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ürün silinirken bir hata oluştu.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin.");
        }
    }

    private void urunDuzenle() {
        int selectedRow = urunTable.getSelectedRow();
        if (selectedRow != -1) {
            String urunID = (String) urunTable.getValueAt(selectedRow, 0);

            // Düzenlenecek verileri alıp textfielda yazdırıyor
            String urunTuru = urunTuruField.getText();
            String urunAdi = urunAdiField.getText();
            String urunMiktar = urunMiktarField.getText();
            String urunBirimFiyat = urunBirimFiyatField.getText();
            String urunRenk = urunRenkField.getText();

            try (Connection connection = dbconnet.DatabaseManager.connect()) {
                String sql = "UPDATE tbl_urunler SET urunTuru=?, urunAdi=?, urunMiktar=?, urunBirimFiyat=?, urunRenk=? WHERE urunID=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, urunTuru);
                    statement.setString(2, urunAdi);
                    statement.setDouble(3, Double.parseDouble(urunMiktar));
                    statement.setDouble(4, Double.parseDouble(urunBirimFiyat));
                    statement.setString(5, urunRenk);
                    statement.setString(6, urunID);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Ürün başarıyla güncellendi.");
                        urunGoruntule(); // Tabloyu güncelle
                        clearFields();
                        urunTable.clearSelection(); // Tabloyu temizle
                    } else {
                        JOptionPane.showMessageDialog(this, "Ürün güncellenirken bir hata oluştu.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin.");
        }
    }

    private void clearFields() {
        urunIDField.setText("");
        urunTuruField.setText("");
        urunAdiField.setText("");
        urunMiktarField.setText("");
        urunBirimFiyatField.setText("");
        urunRenkField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new adminmenu());
    }
}
